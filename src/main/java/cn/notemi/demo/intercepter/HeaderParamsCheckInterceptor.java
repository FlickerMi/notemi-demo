package cn.notemi.demo.intercepter;

import cn.notemi.demo.constant.HeaderConstants;
import cn.notemi.demo.enums.CallSourceEnum;
import cn.notemi.demo.enums.ResultCode;
import cn.notemi.demo.exceptions.BusinessException;
import cn.notemi.demo.util.StringUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @desc 统一参数校验：HEADER头参数校验
 */
public class HeaderParamsCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			String callSource = request.getHeader(HeaderConstants.CALL_SOURCE);
			String apiVersion = request.getHeader(HeaderConstants.API_VERSION);
			String appVersion = request.getHeader(HeaderConstants.APP_VERSION);

			if (StringUtil.isAnyBlank(callSource, apiVersion)) {
				throw new BusinessException(ResultCode.PARAM_NOT_COMPLETE);
			}

			try {
				Double.valueOf(apiVersion);
			} catch (NumberFormatException e) {
				throw new BusinessException(ResultCode.PARAM_IS_INVALID);
			}

			if ((CallSourceEnum.ANDROID.name().equals(callSource) || CallSourceEnum.IOS.name().equals(callSource)) && StringUtil.isEmpty(appVersion)) {
				throw new BusinessException(ResultCode.PARAM_NOT_COMPLETE);
			}

			if (!CallSourceEnum.isValid(callSource)) {
				throw new BusinessException(ResultCode.PARAM_IS_INVALID);
			}

		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// nothing to do
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// nothing to do
	}

}