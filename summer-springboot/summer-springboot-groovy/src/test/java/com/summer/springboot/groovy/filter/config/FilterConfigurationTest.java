package com.summer.springboot.groovy.filter.config;

import com.summer.springboot.groovy.GroovyApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liuwei08
 * @version 2020-04-15
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GroovyApplication.class)
@Slf4j
public class FilterConfigurationTest {

    @Autowired
    FilterConfiguration filterConfiguration;

    @Test
    public void testPrint() {
        filterConfiguration.doFilter("test11111111111111111111");

        filterConfiguration.doFilter(2,"2222222222222222");
    }
}