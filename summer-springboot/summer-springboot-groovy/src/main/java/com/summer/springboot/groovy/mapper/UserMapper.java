package com.summer.springboot.groovy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author liuwei
 * @Description //TODO
 * @Date 1$ 1$
 **/
@Mapper
public interface UserMapper {

	@Insert(" INSERT INTO user_info(`name`, password) VALUES(#{name}, #{password})")
	int addUserInfo(String name, String password);

//	@Select("SELECT content FROM groovy WHERE id = #{id} AND is_delete = '0'")
//	String getGroovyContent(@Param("id")int id);


}