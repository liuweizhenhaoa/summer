package com.summer.springboot.groovy.controller;

import com.summer.springboot.groovy.service.DataConvertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author liuwei
 * @Description //TODO
 * @Date 1$ 1$
 **/
@RestController
@Slf4j
@RequestMapping("/api/dataconvert/")
public class DataConvertController {


	@Autowired
	@Qualifier("demo"+DataConvertService.DATACONVERTSERVICE_SUFFIX)
	DataConvertService dataConvertService1;

	@Autowired
	@Qualifier("demo1"+DataConvertService.DATACONVERTSERVICE_SUFFIX)
	DataConvertService dataConvertService2;

	@Autowired
	ApplicationContext applicationContext;

	@GetMapping(value = "/test")
	public void groovyTest() {
		log.info(dataConvertService1.convert("{}").toString());

		log.info(dataConvertService2.convert("{}").toString());

		DataConvertService dataConvertService =applicationContext.getBean("demo"+DataConvertService.DATACONVERTSERVICE_SUFFIX, DataConvertService.class);

		log.info(dataConvertService.convert("{}").toString());

	}


}
