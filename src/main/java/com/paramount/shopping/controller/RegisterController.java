package com.paramount.shopping.controller;

import com.paramount.shopping.dao.TbUserMapper;
import com.paramount.shopping.domian.TbUser;
import com.paramount.shopping.domian.TbUserExample;
import com.paramount.shopping.domian.response.Result;
import com.paramount.shopping.message.EmailSender;
import com.paramount.shopping.message.SmsSender;
import com.paramount.shopping.utils.RandomString;
import com.paramount.shopping.utils.VerificationCodeUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/register")
@Log4j
public class RegisterController {

    @Autowired
    TbUserMapper tbUserMapper;

    @RequestMapping("/login")
    public TbUser login(@RequestBody TbUser tbUser){
                TbUserExample example = new TbUserExample();
                TbUserExample.Criteria criteria = example.createCriteria();
                if(tbUser.getUsername() != null)
                    criteria.andUsernameEqualTo(tbUser.getUsername());
                if(tbUser.getEmail() != null)
                    criteria.andEmailEqualTo(tbUser.getEmail());
                criteria.andPasswordEqualTo(tbUser.getPassword());
                TbUser userSelected  = tbUserMapper.selectByExample(example).get(0);
                return userSelected;
    }

    @RequestMapping("/saveUser") //post
    public Result saveUser(@RequestBody TbUser tbUser){
        String id = "";
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        tbUser.setIsMobileCheck("1");
        tbUser.setIsEmailCheck("1");
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria1 = example.createCriteria();
        criteria1.andUsernameEqualTo(tbUser.getUsername());
        TbUserExample.Criteria criteria2 = example.createCriteria();
        criteria2.andEmailEqualTo(tbUser.getEmail());
        example.or(criteria2);

        List<TbUser> userCheck = tbUserMapper.selectByExample(example);
        if(userCheck.size()>0)
            return new Result(false, "the username or email is already registed");

        int result = tbUserMapper.insert(tbUser);
        System.out.println("debug");
        return  new Result(true, "create user successfilly");
    }

    @Autowired
    SmsSender smsSender;

    @Autowired
    EmailSender emailSender;

    @RequestMapping("/verificationCodePhone")
    public Integer verificationCodePhone(@RequestBody String phone){
        String code = VerificationCodeUtil.codeGenerate();
        log.info("User phone number is "+ phone);
        System.out.println(phone);
        String mid = smsSender.sendMessage( phone, code );
        log.info("message id = " + mid);
        return Integer.parseInt(code);
    }

    @RequestMapping("/verificationCodeEmail")
    public Integer verificationCodeEmail(@RequestParam String email){
        String code = VerificationCodeUtil.codeGenerate();
        log.info("User email address is "+ email);
        System.out.println(email);
        try {
            emailSender.sendMail(email, code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(code) ;
    }
}
