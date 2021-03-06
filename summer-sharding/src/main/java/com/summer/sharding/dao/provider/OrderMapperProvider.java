package com.summer.sharding.dao.provider;


import com.summer.common.utils.DateUtil;
import com.summer.sharding.entity.OrderRequest;
import org.apache.ibatis.jdbc.SQL;

public class OrderMapperProvider {
    private static final String TBL_ORDER = "order";

    public String getOrder(OrderRequest orderRequest) {
        SQL sql = new SQL().SELECT("*").FROM(TBL_ORDER);
        String userId = orderRequest.getUserId();
        Long orderId = orderRequest.getOrderId();
        if (userId != null) {
            sql.WHERE("user_id = #{userId}");
        }
        if (orderId != null) {
            sql.WHERE("order_id = #{orderId}");
        }
        String startTime = orderRequest.getStartTime();
        String endTime = orderRequest.getEndTime();
        if (startTime != null && endTime != null) {
            orderRequest.setStart(DateUtil.dateToShardingJdbcKey(DateUtil.parseDate(startTime)));
            orderRequest.setEnd(DateUtil.dateToShardingJdbcKey(DateUtil.addDay(DateUtil.parseDate(endTime), 1)));
        }
        if (orderRequest.getStart() != null && orderRequest.getEnd() != null) {
            sql.WHERE("order_id between #{start} and #{end}");
        }
        return sql.toString();
    }



    public String getOrderInfo(OrderRequest orderRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append("select o.order_id, o.user_id, o.description as order_description, o.create_time, oi.order_item_id, oi.description as order_item_description ");
        sb.append("from order o ");
        sb.append("inner join order_item oi ");
        sb.append("on o.order_id = oi.order_id ");
        sb.append("where true ");
        String userId = orderRequest.getUserId();
        if (userId != null) {
            sb.append("and o.user_id = #{userId} ");
        }
        String startTime = orderRequest.getStartTime();
        String endTime = orderRequest.getEndTime();
        if (startTime != null && endTime != null) {
            orderRequest.setStart(DateUtil.dateToShardingJdbcKey(DateUtil.parseDate(startTime)));
            orderRequest.setEnd(DateUtil.dateToShardingJdbcKey(DateUtil.addDay(DateUtil.parseDate(endTime), 1)));
        }
        if (orderRequest.getStart() != null && orderRequest.getEnd() != null) {
            sb.append("and o.order_id between #{start} and #{end}");
        }
        return sb.toString();
    }
}
