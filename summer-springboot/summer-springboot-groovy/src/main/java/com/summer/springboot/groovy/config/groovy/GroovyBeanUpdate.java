package com.summer.springboot.groovy.config.groovy;

import com.summer.springboot.groovy.mapper.GroovyBeanMapper;
import com.summer.springboot.groovy.model.GroovyBean;
import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * @Auther: liuwei
 * @Date: 2020/4/13 19:06
 * @Description:
 */
@Component
@Slf4j
public class GroovyBeanUpdate {

	@Autowired
	private GroovyBeanMapper groovyBeanMapper;
	@Autowired
	private ApplicationContext applicationContext;

	private Date lastLoadDate;

	/**
	 * 构造方法执行之后，调用此方法
	 */
	@PostConstruct
	public void init(){
		lastLoadDate = new Date();
		log.info("GroovyBean load init, Date:{}", lastLoadDate);
		List<GroovyBean>  groovyBeans = groovyBeanMapper.getAllGroovyBeans();
		loadClass(groovyBeans);
	}

	public void loadClass(List<GroovyBean>  groovyBeans){
		if (groovyBeans != null && groovyBeans.size()>0) {
			groovyBeans.forEach( groovyBean ->{
				if (groovyBean != null && StringUtils.isNotEmpty(groovyBean.getContent())) {
					loadClass(groovyBean.getContent(), groovyBean.getClassName());
				}else {
					log.error("配置错误："+groovyBean.toString());
				}

			});
		}
	}

	public void loadClass(String content,String beanName){
		Class clazz = new GroovyClassLoader().parseClass(content);
		BeanDefinitionBuilder beanDefinitionBuilder
				= BeanDefinitionBuilder.genericBeanDefinition(clazz);
		BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
		beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);

		AutowireCapableBeanFactory autowireCapableBeanFactory
				= applicationContext.getAutowireCapableBeanFactory();
		autowireCapableBeanFactory
				.applyBeanPostProcessorsAfterInitialization(beanDefinition, beanName);

		BeanDefinitionRegistry beanRegistry
				= (BeanDefinitionRegistry) autowireCapableBeanFactory;
		if (beanRegistry.containsBeanDefinition(beanName)) {
			beanRegistry.removeBeanDefinition(beanName);
		}
		beanRegistry.registerBeanDefinition(beanName, beanDefinition);
	}

	@Scheduled(cron = "0/10 * * * * ?")
	public void update(){
		log.info("GroovyBean update----------------------");
		List<GroovyBean>  groovyBeans = groovyBeanMapper.getUpdateGroovyBeans(lastLoadDate);
		loadClass(groovyBeans);
	}
}
