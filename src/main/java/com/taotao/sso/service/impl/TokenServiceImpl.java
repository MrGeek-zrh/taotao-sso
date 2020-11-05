package com.taotao.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtil;
import com.taotao.pojo.User;
import com.taotao.sso.jedis.JedisClient;
import com.taotao.sso.service.TokenService;

/**
 * token 相关操作的具体实现类
* <p>Title: TokenServiceImpl.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-10-21_14:19:26
* @version 1.0
 */
@Service
public class TokenServiceImpl implements TokenService{
	
	@Value("${REDIS_SESSION_KEY}")
	private String REDIS_SESSION_KEY;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Autowired
	private JedisClient jedisClient;

	/**
	 * 从redis 中获取用户信息
	 */
	@Override
	public TaotaoResult getUserByToken(String token) {
		//调用jedis客户端，从redis中获取用户信息
		token = REDIS_SESSION_KEY+":"+token;
		String json = jedisClient.get(token);
		//判断是否查到了相关信息
		if (StringUtils.isBlank(json)) {
			return TaotaoResult.build(400, "用户登录信息已经过期");
		}
		//将json转为java对象
		User user = (User) JsonUtil.jsonToObject(json, User.class);
		//更新session 的过期时间
		jedisClient.expire(REDIS_SESSION_KEY+":"+token, SESSION_EXPIRE);
		//将结果返回
		return TaotaoResult.ok(user);
	}
	
	
}
