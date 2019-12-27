package com.summer.dubbo.provider.dao;

import com.summer.dubbo.provider.entity.TransactionLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface TransactionLogMapper {

    @Insert("insert into `transactionlog` ( `primaryKey`, `type`, `msgBody`, `status`, " +
            "`sendTimes`, `createTime`, `updateTime`) " +
            "values(#{primaryKey},#{type},#{msgBody},#{status},#{sendTimes},#{createTime},#{updateTime});")
    void save(TransactionLog transactionLog);


    @Update("update transactionLog set status=#{status},updateTime=#{updateTime} where primaryKey=#{primaryKey} and type=#{type}")
    void updateTransactionStatus(@Param("primaryKey") long primaryKey, @Param("type") String type,
                                 @Param("status")String status, @Param("updateTime") Date updateTime);

    @Select("select * from transactionLog a where a.status='0'")
    List<TransactionLog> findInitTransactionLogs();
}
