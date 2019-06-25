package com.summer.dt.dao;

import com.summer.dt.entity.Stock;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface StockMapper {

    @Update("update stock  set num = #{num},version = #{version+1},updateTime = #{updateTime} where " +
            "goodId= #{goodId} and " +
            "version= #{version}")
    void update(int num, long goodId, Date updateTime, int version);


    @Select("select * from stock a where a.goodId = #{goodId}")
    Stock findStockByGoodId(@Param("goodId")long goodId);

}
