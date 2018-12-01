package com.paramount.shopping.controller;

import java.util.HashMap;
import java.util.Map;

import com.paramount.shopping.domian.TbUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 运营商登录的控制层的类
 * @author jt
 *
 */
@RestController(value = "shoppingLoginController")
@RequestMapping("/shopping/login")
public class LoginController {

	@RequestMapping("/showName")
	public Map showName(){
		Map map = new HashMap();
		// 获得用户名信息:
		TbUser user = (TbUser) SecurityUtils.getSubject().getPrincipal();

		map.put("username", user.getName());
		
		return map;
	}
	
}
