package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转Controller:
 * 		主要用于跳转页面到注册页面和登陆页面
* <p>Title: PageController.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-10-21_19:35:44
* @version 1.0
 */
@Controller
public class PageController {

	/***
	 * 跳转到登陆页面
	* <p>Title: showLogin<／p>
	* <p>Description: <／p>
	* @return
	 */
	@RequestMapping("/page/login")
	public String showLogin(String redirectURL,Model model) {
		model.addAttribute("redirect", redirectURL);
		return "login";
	}
	
	/**
	 * 展示注册页面
	 */
	@RequestMapping("/page/register")
	public String showRegister() {
		return "register";
	}
}
