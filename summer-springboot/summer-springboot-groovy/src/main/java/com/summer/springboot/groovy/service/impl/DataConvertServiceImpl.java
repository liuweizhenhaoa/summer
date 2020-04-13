package com.summer.springboot.groovy.service.impl;

import com.summer.common.utils.FastJsonUtil;
import com.summer.springboot.groovy.model.Article;
import com.summer.springboot.groovy.service.DataConvertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author liuwei
 * @Description //TODO
 * @Date 1$ 1$
 **/
@Service("demo"+DataConvertService.DATACONVERTSERVICE_SUFFIX)
@Slf4j
public class DataConvertServiceImpl implements DataConvertService {
	@Override
	public Article convert(String content) {
		log.info("demo---------------------");

		return FastJsonUtil.json2Bean(content,Article.class);
	}
}
