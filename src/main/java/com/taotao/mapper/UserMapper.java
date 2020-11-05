package com.taotao.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.taotao.pojo.User;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);
    
    User selectUserByUserName(String username);
    
    User selectUserByPhone(Long phoneNum);
    
    User selectUserByEmail(Long emailNum);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}