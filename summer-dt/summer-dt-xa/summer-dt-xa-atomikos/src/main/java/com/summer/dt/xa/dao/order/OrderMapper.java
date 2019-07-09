package com.summer.dt.xa.dao.order;

import com.summer.dt.xa.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    @Insert("insert into `orders` (`id`, `price`, `detail`, `createTime`) " +
            "values(#{id},#{price},#{detail},#{createTime})")
    void save(Order order);
}
