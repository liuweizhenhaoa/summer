package com.summer.dt.dao;

import com.summer.dt.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    @Insert("insert into `area` (`cityId`, `cityName`, `areaName`, `onOffTime`, `standbyTime`, " +
            "`refreshTime`, `order`, `status`, `createTime`, `updateTime`) " +
            "values(#{cityId},#{cityName},#{areaName},#{onOffTime},#{standbyTime},#{refreshTime},#{order},#{status}," +
            "#{createTime},#{updateTime});")
    void save(Order order);
}
