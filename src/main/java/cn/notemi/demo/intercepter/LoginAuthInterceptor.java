package cn.notemi.demo.intercepter;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.notemi.demo.annotations.LoginAuth;
import cn.notemi.demo.enums.ResultCode;
import cn.notemi.demo.exceptions.BusinessException;
import cn.notemi.demo.helper.LoginTokenHelper;
import cn.notemi.demo.model.bo.LoginToken;
import cn.notemi.demo.model.bo.LoginUser;
import cn.notemi.demo.service.LoginTokenService;
import cn.notemi.demo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @desc 已登录权限验证拦截器 备注：通过{@link LoginAuth}配合使用
 */
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginTokenService loginTokenCacheService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> clazz = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();

            // 判断LoginAuth注解是标记在所调用的方法上还是在其类上
            if (clazz.isAnnotationPresent(LoginAuth.class) || method.isAnnotationPresent(LoginAuth.class)) {
                // 登录鉴权的主要业务逻辑解释是当发现用户没有做登陆的时候，立即抛出一个自定义的业务异常BusinessException，如果登录则return true继续执行后续代码。
                // 直接获取登录用户（防止请求转发时，第二次查询）
                LoginUser loginUser = LoginTokenHelper.getLoginUserFromRequest();
                if (loginUser != null) {
                    return true;
                }

                //获取登录TOKEN ID
                String loginTokenId = LoginTokenHelper.getLoginTokenId();
                if (StringUtil.isEmpty(loginTokenId)) {
                    throw new BusinessException(ResultCode.USER_NOT_LOGGED_IN);
                }

                //获取登录TOKEN信息
                LoginToken loginToken = loginTokenCacheService.getById(loginTokenId);
                if (loginToken == null) {
                    throw new BusinessException(ResultCode.USER_NOT_LOGGED_IN);
                }

                //登录TOKEN信息放入请求对象，方便后续controller中获取
                LoginTokenHelper.addLoginTokenToRequest(loginToken);
                return true;
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
