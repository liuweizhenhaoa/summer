package com.summer.dt.mq.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.summer.dt.mq.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liuwei
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Insert("insert into `user` (`id`, `age`, `name`, `sex`, `createTime`) " +
            "values(#{id},#{age},#{name},#{sex},#{createTime})")
    void save(User user);
}
