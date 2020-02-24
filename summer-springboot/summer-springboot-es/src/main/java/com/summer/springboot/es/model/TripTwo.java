package com.summer.springboot.es.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Data
@Document(indexName = "t1", type = "t_bike_ca_trip_17_05")
public class TripTwo {
    private String userId;
    private String userPhone;
    private Integer hireStatusId;
    private String bikeId;
    private String cityCode;
    private Date hireDate;
    private String hireStationName;
    private Date restoreDate;
    private String restoreStationName;
    private Integer transDuration;
    private Integer transMoney;
    private String payOrderId;
    private String payOrderStatus;
    private String operId;
    private String id;
    private Integer partId;
    private Integer hireBikeposId;
    private Integer hireStationId;
    private Integer restoreStationId;
    private Integer restoreBikeposId;
    private Integer errorhdlBikeposId;
    private Integer errorhdlStationId;
    private String errorhdlStationName;
    private Date errorhdlDate;
    private String errorhdlType;
    private Date genDate;
    private String loginType;
    private String appId;
    private String serviceId;
    private String hLockType;
    private String rLockType;
    private String coupUsedId;
    private Integer befDiscountMoney;
    private String coupType;
    private String reletId;
    private String cardUserId;
    private String coordHire;
    private String coordRestore;
    private String supplyStatus;
}

