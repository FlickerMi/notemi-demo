package cn.notemi.demo.service.impl;

import cn.notemi.demo.enums.CacheKeyEnum;
import cn.notemi.demo.helper.LoginTokenHelper;
import cn.notemi.demo.model.bo.LoginToken;
import cn.notemi.demo.service.LoginTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LoginTokenCacheServiceImpl implements LoginTokenService {

	private ValueOperations<String, LoginToken> loginTokenValueOps;

	private RedisTemplate<String, LoginToken> loginTokenTemplate;

	private String loginTokenCacheKeyPrefix;

	public LoginTokenCacheServiceImpl(RedisTemplate<String, LoginToken> loginTokenTemplate, String loginTokenCacheKeyPrefix) {
		Assert.notNull(loginTokenTemplate, "loginTokenTemplate is not null.");
		Assert.notNull(loginTokenCacheKeyPrefix, "loginTokenCacheKeyPrefix is not null.");

		this.loginTokenTemplate = loginTokenTemplate;
		this.loginTokenCacheKeyPrefix = loginTokenCacheKeyPrefix;
		this.loginTokenValueOps = loginTokenTemplate.opsForValue();
	}

	private String getLoginTokenCacheKey(String token) {
		return loginTokenCacheKeyPrefix + token;
	}

	@Override
	public LoginToken add(LoginToken loginToken) {
		Assert.notNull(loginToken, "loginToken is not null");
		Assert.notNull(loginToken.getLoginUser(), "loginToken.getLoginUser() is not null");
		Assert.notNull(loginToken.getLoginCredential(), "loginToken.getLoginCredential() is not null");

		String token = LoginTokenHelper.generateId(loginToken.getLoginCredential().getAccount(), loginToken.getLoginCredential().getType(), loginToken.getIp(), loginToken.getPlatform(), loginToken.getCreateTime(), loginToken.getTtl());
		loginToken.setId(token);
		loginTokenValueOps.set(this.getLoginTokenCacheKey(loginToken.getId()), loginToken, CacheKeyEnum.VALUE_LOGIN_TOKENS.sec(), TimeUnit.SECONDS);
		return loginToken;
	}

	@Override
	public void deleteById(String id) {
		Assert.notNull(id, "id is not null");

		loginTokenTemplate.delete(this.getLoginTokenCacheKey(id));
	}

	@Override
	public LoginToken getById(String id) {
		Assert.notNull(id, "id is not null");

		return loginTokenValueOps.get(this.getLoginTokenCacheKey(id));
	}

	@Override
	public long ttl(String id) {
		Assert.notNull(id, "id is not null");

		return loginTokenTemplate.getExpire(this.getLoginTokenCacheKey(id), TimeUnit.SECONDS);
	}

}