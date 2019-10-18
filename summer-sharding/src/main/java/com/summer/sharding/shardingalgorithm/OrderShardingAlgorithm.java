package com.summer.sharding.shardingalgorithm;

import com.alibaba.fastjson.JSON;
import io.shardingsphere.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * 精确分片算法
 *
 * @author cluo
 * @date 2018/08/03
 * @copyright(c) gome inc Gome Co.,LTD
 */
@Slf4j
public class OrderShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {

        Long value = preciseShardingValue.getValue();
        log.info("collection:" + JSON.toJSONString(collection) + ",preciseShardingValue:" + JSON.toJSONString(preciseShardingValue));
        for (String name : collection) {
            // =与IN中分片键对应的值
            // 分库的后缀
//            int i = 0; // 求分库后缀名的递归算法
            if (name.endsWith(countDatabaseNum(value))) {
                return name;
            }
        }
        throw new UnsupportedOperationException();

    }

    /**
     * 订单、优惠券相关的表，按用户数量分库，64w用户数据为一个库
     * (0,64w]
     */
    public static final int DATABASEAMOUNT = 640000;
    /**
     * 一个订单表里存10000的用户订单
     * (0,1w]
     */
    public static final int TABLEAMOUNT = 10000;


    /**
     * 计算该量级的数据在哪个数据库
     *
     * @return
     */
    private String countDatabaseNum(Long columnValue) {
        // ShardingSphereConstants每个库中定义的数据量
        return String.valueOf(columnValue % 2);
    }

}

