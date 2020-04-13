package com.summer.springboot.groovy.mapper;

import com.summer.springboot.groovy.model.GroovyBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @Auther: liuwei
 * @Date: 2020/4/13 19:11
 * @Description:
 */
@Mapper
public interface GroovyBeanMapper {

	@Select("SELECT content FROM groovy WHERE id = #{id} AND is_delete = '0'")
	String getGroovyContent(@Param("id")int id);

	@Select("SELECT * FROM groovy WHERE is_delete = '0'")
	List<GroovyBean> getAllGroovyBeans();


	@Select("SELECT * FROM groovy WHERE is_delete = '0' and updateTime >#{updateTime}")
	List<GroovyBean> getUpdateGroovyBeans(@Param("updateTime") Date updateTime);
}
