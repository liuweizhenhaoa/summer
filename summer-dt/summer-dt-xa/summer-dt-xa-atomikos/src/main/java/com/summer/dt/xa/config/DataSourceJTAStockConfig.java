package com.summer.dt.xa.config;


import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
@EnableAutoConfiguration
@MapperScan(basePackages = "com.summer.dt.xa.dao.stock", sqlSessionTemplateRef = "jtaStockSqlSessionTemplate")
public class DataSourceJTAStockConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.jta-stock")
    public DataSource dataSourceJTAStock(){
        return new AtomikosDataSourceBean();
    }

    @Bean
    public SqlSessionFactory jtaStockSqlSessionFactory(@Qualifier("dataSourceJTAStock") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        bean.setTypeAliasesPackage("com.summer.dt.xa.dao.stock");
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate jtaStockSqlSessionTemplate(
            @Qualifier("jtaStockSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
