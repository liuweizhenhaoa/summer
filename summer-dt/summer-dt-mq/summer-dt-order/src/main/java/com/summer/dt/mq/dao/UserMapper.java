package com.summer.dt.mq.dao;

import com.summer.dt.mq.entity.User;
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
