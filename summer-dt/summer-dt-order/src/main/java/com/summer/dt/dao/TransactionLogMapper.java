package com.summer.dt.dao;

import com.summer.dt.entity.TransactionLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionLogMapper {

    @Insert("insert into `area` (`cityId`, `cityName`, `areaName`, `onOffTime`, `standbyTime`, " +
            "`refreshTime`, `order`, `status`, `createTime`, `updateTime`) " +
            "values(#{cityId},#{cityName},#{areaName},#{onOffTime},#{standbyTime},#{refreshTime},#{order},#{status}," +
            "#{createTime},#{updateTime});")
    void save(TransactionLog transactionLog);
}
