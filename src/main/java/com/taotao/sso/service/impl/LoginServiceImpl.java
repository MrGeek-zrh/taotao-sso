package com.taotao.sso.service.impl;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtil;
import com.taotao.mapper.UserMapper;
import com.taotao.pojo.User;
import com.taotao.sso.jedis.JedisClient;
import com.taotao.sso.service.LoginService;

/**
 * 用户登录验证的具体实现
* <p>Title: LoginServiceImpl.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-10-21_14:07:41
* @version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_SESSION_KEY}")
	private String REDIS_SESSION_KEY;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	/**
	 * 用户登录验证功能：
	 * 		主要用于整合其他功能，来验证登录信息是否正确，如果正确，就将用户信息写入redis中
	 * 			redis用于模拟session
	 */
	@Override
	public TaotaoResult login(String username, String password, HttpServletRequest request,
			HttpServletResponse response) {
		//校验用户名密码是否正确
		User user = userMapper.selectUserByUserName(username);
		//取用户信息
		if (user == null) {
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		//校验密码
		if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		//登录成功
		//生成token
		String token = UUID.randomUUID().toString();
		//把用户信息写入redis
		//key:REDIS_SESSION:{TOKEN}
		//value:user转json
		//在将用户信息存入redis之前，先将用户的登录密码置为空，安全
		user.setPassword(null);
		jedisClient.set(REDIS_SESSION_KEY + ":" + token, JsonUtil.ObjectToJSON(user));
		//设置session的过期时间
		jedisClient.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE);
		//写cookie
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		
		return TaotaoResult.ok(token);
	}

}