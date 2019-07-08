package com.summer.sharding.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    private Long orderId;
    private String userId;
    private String startTime;
    private String endTime;
    private Long start;
    private Long end;
}
