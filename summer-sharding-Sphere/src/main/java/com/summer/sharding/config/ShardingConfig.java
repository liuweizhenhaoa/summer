//package com.summer.sharding.config;
//
//import com.summer.sharding.shardingAlgorithm.OrderDataBaseShardingAlgorithm;
//import com.summer.sharding.shardingAlgorithm.OrderRangeShardingAlgorithm;
//import com.summer.sharding.shardingAlgorithm.OrderShardingAlgorithm;
//import com.zaxxer.hikari.HikariDataSource;
//import io.shardingsphere.core.api.ShardingDataSourceFactory;
//import io.shardingsphere.core.api.config.ShardingRuleConfiguration;
//import io.shardingsphere.core.api.config.TableRuleConfiguration;
//import io.shardingsphere.core.api.config.strategy.ShardingStrategyConfiguration;
//import io.shardingsphere.core.api.config.strategy.StandardShardingStrategyConfiguration;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//@Configuration
//@Slf4j
//public class ShardingConfig {
//
//    @ConfigurationProperties(prefix = "spring.datasource.db0.hikari")
//    @Bean(name = "db0")
//    public DataSource dataSource0() {
//        return new HikariDataSource();
//    }
//
//    @ConfigurationProperties(prefix = "spring.datasource.db1.hikari")
//    @Bean(name = "db1")
//    public DataSource dataSource1() {
//        return new HikariDataSource();
//    }
//
//    @Primary
//    @Bean(name = "shardingDataSource")
//    public DataSource getDataSource(@Qualifier("db0") DataSource db0, @Qualifier("db1") DataSource db1) throws SQLException {
//        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
//        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
////        shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
//        shardingRuleConfig.getBindingTableGroups().add("order");
//        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id", new OrderDataBaseShardingAlgorithm()));
//        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", new OrderShardingAlgorithm(), new OrderRangeShardingAlgorithm()));
//        shardingRuleConfig.setDefaultDataSourceName("db1");
//        Map<String, DataSource> dataSourceMap = new HashMap<>();
//        dataSourceMap.put("db0", db0);
//        dataSourceMap.put("db1", db1);
//        Properties properties = new Properties();
//        properties.setProperty("sql.show", Boolean.TRUE.toString());
//        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new HashMap<String, Object>(), properties);
//    }
//
//    private static TableRuleConfiguration getOrderTableRuleConfiguration() {
//        TableRuleConfiguration result = new TableRuleConfiguration();
//        result.setLogicTable("order");
//        result.setActualDataNodes("db$->{0..1}.order$->{0..1}");
//        result.setKeyGeneratorColumnName("order_id");
//        return result;
//    }
//
//    private static TableRuleConfiguration getOrderItemTableRuleConfiguration() {
//        TableRuleConfiguration result = new TableRuleConfiguration();
//        result.setLogicTable("t_order_item");
//        result.setActualDataNodes("ds_${0..1}.t_order_item_${[0, 1]}");
//        return result;
//    }
//}
