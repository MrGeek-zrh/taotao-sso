package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * 根据token从redis中获取用户信息
* <p>Title: TokenService.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-10-21_14:16:39
* @version 1.0
 */
public interface TokenService {

	/**
	 * 根据token，从redis中获取用户信息
	* <p>Title: getUserByToken<／p>
	* <p>Description: <／p>
	* @param token
	* @return
	 */
	public TaotaoResult getUserByToken(String token);
}
