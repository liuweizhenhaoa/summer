package com.summer.dt.mq.entity;

import com.summer.mybatis.config.BaseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class User {
    private long id;
    private int age;
    private String name;
    private Sex sex;
    private Date createTime;


    @Getter
    @AllArgsConstructor
    public enum Sex implements BaseCodeEnum {
        // 男
        MAN(1, "男"),
        // 女
        WOMAN(0, "女");

        private int code;
        private String msg;

        @Override
        public int getCode() { return this.code; }

    }
}
