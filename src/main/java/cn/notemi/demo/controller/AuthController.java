package cn.notemi.demo.controller;

import cn.notemi.demo.annotations.LoginAuth;
import cn.notemi.demo.annotations.ResponseResult;
import cn.notemi.demo.model.po.User;
import cn.notemi.demo.model.qo.LoginQO;
import cn.notemi.demo.model.vo.LoginVO;
import cn.notemi.demo.repository.UserRepository;
import cn.notemi.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Title：AuthController
 **/
@RestController
@RequestMapping("/api/auth")
@ResponseResult
public class AuthController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public LoginVO login(@Valid @RequestBody LoginQO loginQO) {
        return loginService.login(loginQO);
    }

    @GetMapping("/login")
    public LoginVO getLogin() {
        LoginQO loginQO = new LoginQO();
        List<String> type = new ArrayList<>();
        type.add("CUSTOM");
        loginQO.setAccount("superadmin");
        loginQO.setPwd("123123");
        loginQO.setType(type);
        return loginService.login(loginQO);
    }

    @PostMapping("/logout")
    @LoginAuth
    public void logout() {
        loginService.logout();
    }
}
