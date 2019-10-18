package com.summer.sharding.dao;

import com.summer.sharding.dao.provider.OrderItemMapperProvider;
import com.summer.sharding.entity.OrderItem;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;


import java.util.List;

@Mapper
public interface OrderItemMapper {

    @Results({
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "orderItemId", column = "order_item_id"),
            @Result(property = "description", column = "description")
        })
    @Select("select * from order_item order by order_id")
    List<OrderItem> getOrderItems();

    @InsertProvider(type = OrderItemMapperProvider.class, method = "insertAll")
    int addOrderItems(@Param("orderItems") List<OrderItem> orderItems);
}
