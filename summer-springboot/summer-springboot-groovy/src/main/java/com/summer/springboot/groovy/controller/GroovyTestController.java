package com.summer.springboot.groovy.controller;

import com.summer.springboot.groovy.mapper.UserMapper;
import com.summer.springboot.groovy.service.BaseGroovySpot;
import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author liuwei
 * @Description //TODO
 * @Date 1$ 1$
 **/
@Slf4j
@RestController
public class GroovyTestController {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ApplicationContext applicationContext;

//	@Autowired
//	public GroovyTestController(UserMapper userMapper,
//	                            ApplicationContext applicationContext) {
//		this.userMapper = userMapper;
//		this.applicationContext = applicationContext;
//	}

	@GetMapping(value = "/groovy/test")
	public void groovyTest() {
		BaseGroovySpot baseGroovySpot
				= applicationContext.getBean("groovyBean", BaseGroovySpot.class);
		log.info("content = {}", baseGroovySpot.test());
		log.info("content = {}", baseGroovySpot.test2());
	}

	@GetMapping(value = "/groovy/add/bean")
	public void groovyAddBean() {
		String content = userMapper.getGroovyContent(1);

		Class clazz = new GroovyClassLoader().parseClass(content);
		BeanDefinitionBuilder beanDefinitionBuilder
				= BeanDefinitionBuilder.genericBeanDefinition(clazz);
		BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
		beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);

		AutowireCapableBeanFactory autowireCapableBeanFactory
				= applicationContext.getAutowireCapableBeanFactory();
		autowireCapableBeanFactory
				.applyBeanPostProcessorsAfterInitialization(beanDefinition, "groovyBean");

		BeanDefinitionRegistry beanRegistry
				= (BeanDefinitionRegistry) autowireCapableBeanFactory;
		if (beanRegistry.containsBeanDefinition("groovyBean")) {
			beanRegistry.removeBeanDefinition("groovyBean");
		}
		beanRegistry.registerBeanDefinition("groovyBean", beanDefinition);

		BaseGroovySpot baseGroovySpot
				= applicationContext.getBean("groovyBean", BaseGroovySpot.class);
		log.info("test content = {}", baseGroovySpot.test());
		log.info("test content = {}", baseGroovySpot.test2());
	}
}
