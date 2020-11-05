package com.taotao.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.sso.service.TokenService;

/**
 * token相关服务Controller
* <p>Title: TokenController.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-10-21_14:34:24
* @version 1.0
 */
@Controller
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
	
	/**
	 * 根据token获取用户信息处理器
	* <p>Title: getUserByToken<／p>
	* <p>Description: <／p>
	* @param token 用户的唯一token
	* @param callback 是否使用jsonp服务
	* @return 
	 */
	@RequestMapping("/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token,String callback) {
//		/////////////////////////
		System.out.println(token);
		////////////////////////
		TaotaoResult result = tokenService.getUserByToken(token);
		//判断是否为jsonp调用
		if (StringUtils.isNotBlank(callback)) {
			//是jsonp调用
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return result;
	}
}
