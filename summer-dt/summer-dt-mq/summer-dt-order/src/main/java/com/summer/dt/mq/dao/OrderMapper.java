package com.summer.dt.mq.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.summer.dt.mq.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Insert("insert into `orders` (`id`, `price`, `detail`, `createTime`) " +
            "values(#{id},#{price},#{detail},#{createTime})")
    void save(Order order);
}
