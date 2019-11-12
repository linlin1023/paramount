package com.paramount.shopping.controller;

import com.paramount.shopping.domian.TbContent;
import com.paramount.shopping.domian.TbUser;
import com.paramount.shopping.domian.response.PageResult;
import com.paramount.shopping.domian.response.Result;
import com.paramount.shopping.service.TbUserService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/shopping/tbuser")
public class TbUserController {

    @Autowired
    TbUserService tbUserService;

    //search?page=undefined&rows=undefined  shopping/tbuser/search
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbUser user, int page, int rows  ){
        log.info("TbUserController============");
        return  tbUserService.findPage(user, page, rows);
    }

    @RequestMapping("/findOne")
    public TbUser findOne(Long id){
        return tbUserService.findOne(id);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody TbUser user){
        try {
            tbUserService.add(user);
            return new Result(true, "successfully add");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "failed");
        }
    }


    @RequestMapping("/update")
    public Result update(@RequestBody TbUser user){
        try {
            tbUserService.update(user);
            return new Result(true, "successfully add");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "failed");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Long [] ids){
        try {
            tbUserService.delete(ids);
            return new Result(true, "delete successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "delete failed");
        }
    }

}
