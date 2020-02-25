package com.summer.springboot.disruptor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 创建事件
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class NotifyEvent {
    private String message;

    private long id;

    private Date time;
}