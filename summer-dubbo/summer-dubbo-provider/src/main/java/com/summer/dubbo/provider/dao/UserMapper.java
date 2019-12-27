package com.summer.dubbo.provider.dao;

import com.summer.dubbo.provider.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liuwei
 */
@Mapper
public interface UserMapper {

    @Insert("insert into `user` (`id`, `age`, `name`, `sex`, `createTime`) " +
            "values(#{id},#{age},#{name},#{sex},#{createTime})")
    void save(User user);
}
