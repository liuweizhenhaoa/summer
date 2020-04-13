package com.summer.springboot.groovy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @Author liuwei
 * @Description //TODO
 * @Date 1$ 1$
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
	private String is_delete;
	private Timestamp createTime;
	private Timestamp updateTime;


//	public static void main(String[] args) {
//		Article article = new Article();
//		article.getAuthor();
//
//	}
}
