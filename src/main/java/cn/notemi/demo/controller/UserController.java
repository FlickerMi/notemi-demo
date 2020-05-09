package cn.notemi.demo.controller;

import cn.notemi.demo.annotations.LoginAuth;
import cn.notemi.demo.annotations.ResponseResult;
import cn.notemi.demo.model.bo.LoginUser;
import cn.notemi.demo.model.po.User;
import cn.notemi.demo.model.qo.LoginQO;
import cn.notemi.demo.model.vo.LoginVO;
import cn.notemi.demo.repository.UserRepository;
import cn.notemi.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Title：UserController
 **/
@RestController
@RequestMapping("/api/user")
@ResponseResult
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @RequestBody User user) {
        user.setId(UUID.randomUUID().toString());
        user.setCreateTime(new Date());
        return userRepository.save(user);
    }

    @GetMapping("/all")
    @LoginAuth
    public List<User> users() {
        return userRepository.findAll();
    }

    @GetMapping("/mine")
    @LoginAuth
    public LoginUser mine(LoginUser loginUser) {
        return loginUser;
    }

    @GetMapping("/{id}")
    public User findOneById(@PathVariable("id") String id) {
        return userRepository.findById(id).orElse(null);
    }
}
