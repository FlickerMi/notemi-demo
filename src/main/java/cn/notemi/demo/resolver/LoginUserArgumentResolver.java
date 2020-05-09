package cn.notemi.demo.resolver;

import cn.notemi.demo.annotations.LoginAuth;
import cn.notemi.demo.helper.LoginTokenHelper;
import cn.notemi.demo.model.bo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Method;

/**
 * @desc 登录用户参数解析器
 */
@Slf4j
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

	//根据方法参数判断是否需要对其做转换
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		final Method method = parameter.getMethod();
		final Class<?> clazz = parameter.getMethod().getDeclaringClass();

		boolean isHasLoginAuthAnn = clazz.isAnnotationPresent(LoginAuth.class) || method.isAnnotationPresent(LoginAuth.class);
		boolean isHasLoginUserParameter = parameter.getParameterType().isAssignableFrom(LoginUser.class);

		return isHasLoginAuthAnn && isHasLoginUserParameter;
	}

	//supportsParameter方法返回true后，进入该方法，返回值即为要转化对象的结果
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		return LoginTokenHelper.getLoginUserFromRequest();
	}
}