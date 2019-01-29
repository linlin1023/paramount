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
 * Created by pll on 2018/11/25 21:04.
 * Email support@paramountmerchandize.co.nz
 */
@Api(tags = "login")
@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private TokenManager tokenManeger;


    @LogAnnotation
    @ApiOperation(value = "web login")
    @PostMapping("/sys/login")
    public void login(String username,String password){
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        SecurityUtils.getSubject().login(usernamePasswordToken);
    }

    @ApiOperation(value = "current login user")
    @GetMapping("/sys/login")
    public User getLoginInfo(){
        return UserUtil.getCurrentUser();
    }

    @LogAnnotation
    @ApiOperation(value = "restful style,the interface between front end and back end")
    @PostMapping("/sys/login/restful")
    public Token restfulLogin(String username,String password){
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password);
        SecurityUtils.getSubject().login(usernamePasswordToken);
        return tokenManeger.saveToken(usernamePasswordToken);
    }

}
