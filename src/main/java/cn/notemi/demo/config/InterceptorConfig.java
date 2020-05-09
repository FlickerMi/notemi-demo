package cn.notemi.demo.config;

import cn.notemi.demo.intercepter.HeaderParamsCheckInterceptor;
import cn.notemi.demo.intercepter.LoginAuthInterceptor;
import cn.notemi.demo.intercepter.ResponseResultInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	@Bean
	public LoginAuthInterceptor loginAuthInterceptor() {
		return new LoginAuthInterceptor();
	}

	@Bean
	public ResponseResultInterceptor responseResultInterceptor() {
		return new ResponseResultInterceptor();
	}

	@Bean
	public HeaderParamsCheckInterceptor headerParamsCheckInterceptor() {
		return new HeaderParamsCheckInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//响应结果控制拦截
		registry.addInterceptor(responseResultInterceptor());
		//请求头参数拦截
//		registry.addInterceptor(headerParamsCheckInterceptor());
		//登录拦截
		registry.addInterceptor(loginAuthInterceptor());
	}

}