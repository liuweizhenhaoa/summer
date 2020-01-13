package com.summer.dubbo.consumer.dao;

import com.summer.dubbo.consumer.entity.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import java.util.Date;

@Mapper
public interface StockMapper {

    @Update("update stock  set num = #{num},version = #{newVersion},updateTime = #{updateTime} where " +
            "goodId= #{goodId} and " +
            "version= #{version}")
    void update(int num, long goodId, Date updateTime, int version,int newVersion);


    @Select("select * from stock a where a.goodId = #{goodId}")
    Stock findStockByGoodId(@Param("goodId")long goodId);

    @Select("select * from stock a where a.id = #{id}")
    Stock findStockById(@Param("id")int id);

}