package com.summer.springboot.disruptor.BeanPostProcessor;

import com.summer.springboot.disruptor.annotations.Boy;
import com.summer.springboot.disruptor.service.HelloBoyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component //注意：Bean后置处理器本身也是一个Bean
@Slf4j
public class BoyAnnotationBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        /**
         * 利用Java反射机制注入属性
         */
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Boy annotation = declaredField.getAnnotation(Boy.class);
            if (null == annotation) {
                continue;
            }
            declaredField.setAccessible(true);
            try {
                log.info("----------postProcessBeforeInitialization method-----------");

                declaredField.set(bean, annotation.value());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {

        if (o instanceof HelloBoyService) {
            log.info("----------postProcessAfterInitialization method-----------");

        }

        return o; //这里要返回o，不然启动时会报错
    }
}

