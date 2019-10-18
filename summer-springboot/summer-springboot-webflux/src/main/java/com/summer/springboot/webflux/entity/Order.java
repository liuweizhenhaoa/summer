package com.summer.springboot.webflux.entity;


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
public class Order implements Serializable {


    @NotNull(message = "不为空")
    private long id;
    @NotNull(message = "不为空")
    private double price;

    @NotNull(message = "不为空")
    @Length(max = 10, message = "name长度不能超过10")
    private String detail;

    @Email(message = "email 不为空")
    private String email;

    private Date createTime;

}
