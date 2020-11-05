package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.sso.service.LoginService;

/**
 * 为登录页面提供登录验证服务
* <p>Title: LoginController.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-10-21_14:06:50
* @version 1.0
 */
@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	/**
	 * 登录功能处理器
	* <p>Title: login<／p>
	* <p>Description: <／p>
	* @param username
	* @param password
	* @param request
	* @param response
	* @return
	 */
	@RequestMapping(value = "/user/login",method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(String username,String password,HttpServletRequest request,HttpServletResponse response) {
		try {
			TaotaoResult result = loginService.login(username, password, request, response);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
