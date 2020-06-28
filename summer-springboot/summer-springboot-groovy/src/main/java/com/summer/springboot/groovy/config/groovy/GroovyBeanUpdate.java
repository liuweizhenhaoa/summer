package com.summer.springboot.groovy.config.groovy;

import com.google.common.collect.Maps;
import com.summer.common.utils.MD5Utils;
import com.summer.springboot.groovy.mapper.GroovyBeanMapper;
import com.summer.springboot.groovy.model.GroovyBean;
import groovy.lang.GroovyClassLoader;
import jline.internal.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
	private Map<String, Class> nameAndClass = Maps.newConcurrentMap();
	private Map<String, String> nameAndMd5 = Maps.newConcurrentMap();
	private Date lastLoadDate;

	/**
	 * 构造方法执行之后，调用此方法
	 */
//	@PostConstruct
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

	/**
	 * 缓存GroovyClassLoader加载的class,防止metaspace内存溢出
	 * @param name
	 * @param script
	 */
	public Class praseAndCache(String name, String script) {
		try {
			Preconditions.checkNotNull(name);
			Preconditions.checkNotNull(script);

			String oldMd5 = nameAndMd5.get(name);
			String newMd5 = MD5Utils.md5String(script);
			if (oldMd5 != null && oldMd5.equals(newMd5)) {
				return nameAndClass.get(name);
			}

			GroovyClassLoader classLoader = new GroovyClassLoader();
			Class aClass = classLoader.parseClass(script);
			log.info("collection-engine load script:{} finish", name);
			nameAndClass.put(name, aClass);
			nameAndMd5.put(name, newMd5);
			return aClass;
		} catch (Exception e){
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将groovy脚本转换成class，并有spring ApplicationContext转载和管理
	 * @param content
	 * @param beanName
	 */
	public void loadClass(String content,String beanName){
		Class clazz = praseAndCache(beanName, content);
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

//	@Scheduled(cron = "0/10 * * * * ?")
	public void update(){
		log.info("GroovyBean update----------------------");
		List<GroovyBean>  groovyBeans = groovyBeanMapper.getUpdateGroovyBeans(lastLoadDate);
		loadClass(groovyBeans);
	}
}
