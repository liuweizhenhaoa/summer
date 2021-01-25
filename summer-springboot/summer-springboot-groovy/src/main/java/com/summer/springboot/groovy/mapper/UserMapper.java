package com.summer.springboot.groovy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author liuwei
 * @Date 1$ 1$
 **/
@Mapper
public interface UserMapper {

	@Insert(" INSERT INTO user_info(`name`, password) VALUES(#{name}, #{password})")
	int addUserInfo(String name, String password);



}