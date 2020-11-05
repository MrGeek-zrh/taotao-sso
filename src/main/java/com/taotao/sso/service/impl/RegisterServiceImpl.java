package com.taotao.sso.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.UserMapper;
import com.taotao.pojo.User;
import com.taotao.sso.service.RegisterService;

/**
 * 登录Service
* <p>Title: RegisterServiceImpl.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-10-20_22:30:54
* @version 1.0
 */
@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * 校验数据在数据库中是否存在
	 */
	@Override
	public TaotaoResult checkData(String param, int type) {
		//根据数据类型检查数据
		User user = null;
		//1、2、3分别代表username、phone、email
		if (1 == type) {
			user = userMapper.selectUserByUserName(param);
		} else if ( 2 == type) {
			user = userMapper.selectUserByPhone(Long.parseLong(param));
		} else if ( 3 == type ) {
			user = userMapper.selectUserByEmail(Long.parseLong(param));
		}
		//判断查询结果是否为空
		if (user == null) {
			return TaotaoResult.ok(true);
		}
		//查询到了结果，返回false
		return TaotaoResult.ok(false);
	}
	
	/**
	 * 用户注册：
	 * 		对传来用户的信息进行验证，符合条件的，就写入数据库中
	 * <p>Title: register</p>
	 * <p>Description: </p>
	 * @param user
	 * @return
	 */
	@Override
	public TaotaoResult register(User user) {
		// 校验数据
		//校验用户名、密码不能为空
		if (StringUtils.isBlank(user.getUsername())
				|| StringUtils.isBlank(user.getPassword())) {
			return TaotaoResult.build(400, "用户名或密码不能为空");
		}
		//校验数据是否重复
		//校验用户名
		TaotaoResult result = checkData(user.getUsername(), 1);
		if (!(boolean) result.getData()) {
			return TaotaoResult.build(400, "用户名重复");
		}
		//校验手机号
		if (user.getPhone() != null) {
			result = checkData(user.getPhone(), 2);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "手机号重复");
			}
		}
		//校验手机号
		if (user.getEmail() != null) {
			result = checkData(user.getEmail(), 3);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "邮箱重复");
			}
		}
		//插入数据
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//在插入数据之前，需要先对密码进行加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);
		
		return TaotaoResult.ok();
	}
	
	
}
