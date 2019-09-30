package com.summer.springboot.es.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Document(indexName = "ca", type = "t_bike_ca_trip_17_05")
public class Trip {
    private String userId;
    private String userPhone;
    private Integer hireStatusId;
    private String bikeId;
    private String cityCode;
    @Field(type = FieldType.Text)
    private Date hireDate;
    private String hireStationName;
    @Field(type = FieldType.Text)
    private Date restoreDate;
    private String restoreStationName;
    private Integer transDuration;
    private Integer transMoney;
    private String payOrderId;
    private String payOrderStatus;
    private String operId;
    @Id
    private String id;
    private Integer partId;
    private Integer hireBikeposId;
    private Integer hireStationId;
    private Integer restoreStationId;
    private Integer restoreBikeposId;
    private Integer errorhdlBikeposId;
    private Integer errorhdlStationId;
    private String errorhdlStationName;
    @Field(type = FieldType.Text)
    private Date errorhdlDate;
    private String errorhdlType;
    @Field(type = FieldType.Text)
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


//    @Id
//    @Column(name = "ID_")
//    private String id;
//    @Column(name = "USER_ID_")
//    private String USER_ID_;
//    @Column(name = "USER_PHONE_")
//    private String USER_PHONE_;
//    @Column(name = "GEN_DATE_")
//    private Date GEN_DATE_;
//    @Column(name = "BIKE_ID_")
//    private String BIKE_ID_;


//    private Integer HIRE_STATUS_ID_;
//    private String CITY_CODE_;
//    private Date HIRE_DATE_;
//
//    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
//    private String HIRE_STATION_NAME_;
//    private Date RESTORE_DATE_;
//    private String RESTORE_STATION_NAME_;
//    private Integer TRANS_DURATION_;
//    private Integer TRANS_MONEY_;
//    private String PAY_ORDER_ID_;
//    private String PAY_ORDER_STATUS_;
//
//    private String OPER_ID_;
//
//
//    private Integer PART_ID_;
//    private Integer HIRE_BIKEPOS_ID_;
//    private Integer HIRE_STATION_ID_;
//    private Integer RESTORE_STATION_ID_;
//    private Integer RESTORE_BIKEPOS_ID_;
//    private Integer ERRORHDL_BIKEPOS_ID_;
//    private Integer ERRORHDL_STATION_ID_;
//    private String ERRORHDL_STATION_NAME_;
//    private Date ERRORHDL_DATE_;
//    private String ERRORHDL_TYPE_;
//    private String LOGIN_TYPE_;
//    private String APP_ID_;
//    private String SERVICE_ID_;
//    private String H_LOCK_TYPE_;
//    private String R_LOCK_TYPE_;
//    private String COUP_USED_ID_;
//    private Integer BEF_DISCOUNT__MONEY_;
//    private String COUP_TYPE_;
//    private String RELET_ID_;
//    private String CARD_USER_ID_;
//    private String COORD_HIRE_;
//    private String COORD_RESTORE_;
//    private String SUPPLY_STATUS_;
}
