package com.summer.springboot.groovy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @Auther: liuwei
 * @Date: 2020/4/13 19:08
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GroovyBean {
	private int id;
	private String content;
	private String className;
	private Timestamp createTime;
	private Timestamp updateTime;
	private String is_delete;
}
