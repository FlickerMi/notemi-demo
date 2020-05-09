package cn.notemi.demo.service.impl;

import cn.notemi.demo.constant.HeaderConstants;
import cn.notemi.demo.enums.CacheKeyEnum;
import cn.notemi.demo.enums.ResultCode;
import cn.notemi.demo.exceptions.BusinessException;
import cn.notemi.demo.helper.LoginTokenHelper;
import cn.notemi.demo.helper.PasswordHelper;
import cn.notemi.demo.model.bo.LoginToken;
import cn.notemi.demo.model.bo.LoginUser;
import cn.notemi.demo.model.po.LoginCredential;
import cn.notemi.demo.model.po.User;
import cn.notemi.demo.model.qo.LoginQO;
import cn.notemi.demo.model.vo.LoginCredentialVO;
import cn.notemi.demo.model.vo.LoginVO;
import cn.notemi.demo.repository.LoginCredentialRepository;
import cn.notemi.demo.repository.UserRepository;
import cn.notemi.demo.service.LoginService;
import cn.notemi.demo.service.LoginTokenService;
import cn.notemi.demo.util.BeanUtil;
import cn.notemi.demo.util.IpUtil;
import cn.notemi.demo.util.RequestContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @desc 登录服务实现
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginTokenService loginTokenService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginCredentialRepository loginCredentialRepository;

	@Override
	public LoginVO login(LoginQO loginQO) {

		List<LoginCredential> loginCredentialList = loginCredentialRepository.findAllByAccountAndTypeIn(loginQO.getAccount(), loginQO.getType());
		if (loginCredentialList.size() == 0) {
			log.info("login account is nonexistent, account:{}", loginQO.getAccount());
			throw new BusinessException(ResultCode.USER_LOGIN_ERROR);
		}

		//验证密码是否正确
		LoginCredential firstLoginCredential = loginCredentialList.get(0);
		if (!firstLoginCredential.getPwd().equals(PasswordHelper.encodeBySalt(loginQO.getPwd(), firstLoginCredential.getRandomSalt()))) {
			log.info("login account' password is error");
			throw new BusinessException(ResultCode.USER_LOGIN_ERROR);
		}

		User user = userRepository.findById(firstLoginCredential.getUserId()).orElse(null);
		if (user == null) {
			log.info("login user is null");
			throw new BusinessException(ResultCode.USER_LOGIN_ERROR);
		}

		LoginToken loginToken = this.saveLoginToken(user, firstLoginCredential);

		LoginUser loginUser = new LoginUser();
		BeanUtil.copyProperties(user, loginUser);

		LoginCredentialVO loginCredential = new LoginCredentialVO();
		BeanUtil.copyProperties(firstLoginCredential, loginCredential);

		return LoginVO.builder()
				.token(loginToken.getId())
				.loginTime(loginToken.getCreateTime())
				.ip(loginToken.getIp())
				.platform(loginToken.getPlatform())
				.ttl(loginToken.getTtl())
				.user(loginUser)
				.loginCredential(loginCredential)
				.build();
	}

	private LoginToken saveLoginToken(User user, LoginCredential loginCredential) {
		Date currentDate = new Date();
		LoginUser loginUser = new LoginUser();
		BeanUtil.copyProperties(user, loginUser);

		HttpServletRequest request = RequestContextUtil.getRequest();

		LoginToken loginToken = LoginToken.builder()
				.createTime(currentDate)
				.ip(IpUtil.getRealIp(request))
				.platform(request.getHeader(HeaderConstants.CALL_SOURCE))
				.ttl(CacheKeyEnum.VALUE_LOGIN_TOKENS.sec().longValue())
				.loginCredential(loginCredential)
				.loginUser(loginUser)
				.build();

		loginToken = loginTokenService.add(loginToken);
		LoginTokenHelper.addLoginTokenIdToCookie(loginToken.getId(), CacheKeyEnum.VALUE_LOGIN_TOKENS.sec());
		return loginToken;
	}

	@Override
	public void logout() {
		LoginToken loginToken = LoginTokenHelper.getLoginTokenFromRequest();
		if (loginToken == null) {
			throw new BusinessException(ResultCode.USER_NOT_LOGGED_IN);
		}

		loginTokenService.deleteById(loginToken.getId());
		LoginTokenHelper.delLoginTokenIdFromCookie();
	}
}