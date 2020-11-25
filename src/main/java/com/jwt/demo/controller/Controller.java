package com.jwt.demo.controller;

import com.jwt.demo.bean.User;
import com.jwt.demo.service.UserService;
import com.jwt.demo.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jhye4
 * @date 2020/11/23 16:54
 */
@RestController
@RequestMapping("/user")
public class Controller {

    @Autowired
    UserService userService;
    /**
     * 登录
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public CommonResult<User> login(@RequestBody User user){
        User finalUser = userService.auth(user.getEmail(), user.getPasswd());
        return CommonResult.success(finalUser);
    }

    /**
     * 鉴权
     * @param token
     * @return
     */
    @RequestMapping("/get")
    public CommonResult<User> getUser(String token){
        User finalUser = userService.getLoginedUserByToken(token);
        return CommonResult.success(finalUser);
    }

    /**
     * logout
     * @param token
     * @return
     */
    @RequestMapping("/logout")
    public CommonResult<Object> logout(String token){
        userService.invalidate(token);
        return CommonResult.success(null);
    }
}
