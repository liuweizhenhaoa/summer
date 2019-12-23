package com.summer.springboot.jaxrs.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(description = "订单实体")
public class Order implements Serializable {

    @ApiModelProperty("id")
    @NotNull(message = "不为空")
    private long id;

    @ApiModelProperty("价格")
    @NotNull(message = "不为空")
    private double price;

    @ApiModelProperty("描述")
    @NotNull(message = "不为空")
    @Length(max = 10, message = "name长度不能超过10")
    private String detail;

    @ApiModelProperty("邮箱")
    @Email(message = "email 不为空")
    private String email;

    @ApiModelProperty("创建时间")
    private Date createTime;

}
