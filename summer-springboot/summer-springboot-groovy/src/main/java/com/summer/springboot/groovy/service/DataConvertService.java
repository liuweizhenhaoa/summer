package com.summer.springboot.groovy.service;

import com.summer.springboot.groovy.model.Article;

public interface DataConvertService {
	public static final String DATACONVERTSERVICE_SUFFIX = "DataConvertService";
	Article convert(String content);
}
