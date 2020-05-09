package cn.notemi.demo.service;

import cn.notemi.demo.model.qo.LoginQO;
import cn.notemi.demo.model.vo.LoginVO;

/**
 * @desc 登录服务
 */
public interface LoginService {

    LoginVO login(LoginQO loginQO);

    void logout();
}