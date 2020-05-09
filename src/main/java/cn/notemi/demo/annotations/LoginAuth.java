package cn.notemi.demo.annotations;

import java.lang.annotation.*;

/**
 * @desc 已登录权限验证注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginAuth {

}