package com.summer.dubbo.provider.dao;

import com.summer.dubbo.provider.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    @Insert("insert into `orders` (`id`, `price`, `detail`, `createTime`) " +
            "values(#{id},#{price},#{detail},#{createTime})")
    void save(Order order);
}
