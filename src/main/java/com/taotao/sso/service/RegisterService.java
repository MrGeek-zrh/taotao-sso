package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.User;

public interface RegisterService {

	TaotaoResult checkData(String param, int type);

	/**
	 * 用户注册
	 * <p>Title: register</p>
	 * <p>Description: </p>
	 * @param user
	 * @return
	 */
	TaotaoResult register(User user);
	
}
