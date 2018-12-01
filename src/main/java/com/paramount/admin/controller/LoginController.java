package com.paramount.admin.controller;

import com.paramount.admin.common.utils.LogAnnotation;
import com.paramount.admin.common.utils.UserUtil;
import com.paramount.admin.domain.User;
import com.paramount.admin.service.TokenManager;
import com.paramount.admin.vo.Token;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by szz on 2018/3/25 21:04.
 * Email szhz186@gmail.com
 */
@Api(tags = "登陆")
@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private TokenManager tokenManeger;


    @LogAnnotation
    @ApiOperation(value = "web端登陆")
    @PostMapping("/sys/login")
    public void login(String username,String password){
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        SecurityUtils.getSubject().login(usernamePasswordToken);
    }

    @ApiOperation(value = "当前登录用户")
    @GetMapping("/sys/login")
    public User getLoginInfo(){
        return UserUtil.getCurrentUser();
    }

    @LogAnnotation
    @ApiOperation(value = "restful登录方式,前后端分离时的接口")
    @PostMapping("/sys/login/restful")
    public Token restfulLogin(String username,String password){
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password);
        SecurityUtils.getSubject().login(usernamePasswordToken);
        return tokenManeger.saveToken(usernamePasswordToken);
    }

}
