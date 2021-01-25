package com.summer.springboot.groovy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @Author liuwei
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {

	private Long id;
	private String title;
	private String author;
	private String content;
	private String isDelete;
	private Timestamp createTime;
	private Timestamp updateTime;

}
