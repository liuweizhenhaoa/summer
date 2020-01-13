package com.summer.springboot.influxdb.service;

import com.summer.springboot.influxdb.config.infuxdb.InfluxConfig;
import com.summer.springboot.influxdb.entity.Cpu;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BoundParameterQuery;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class InfluxService {

    @Autowired
    private InfluxDB influxDB;

    @Autowired
    InfluxConfig influxConfig;

    public List<Cpu> querySql(String sql) {
        Query query = new Query(sql, influxConfig.getInfluxDatabase());
        QueryResult result = influxDB.query(query);

        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper(); // thread-safe - can be reused
        List<Cpu> cpuList = resultMapper.toPOJO(result, Cpu.class);
        log.info(cpuList.toString());
        return cpuList;
    }

    public List<Cpu> queryByParam(String sql) {
        Query query = BoundParameterQuery.QueryBuilder.newQuery("SELECT * FROM cpu WHERE idle > $idle")
                .forDatabase(influxConfig.getInfluxDatabase())
                .bind("idle", 90)
//                .bind("system", 5)
                .create();
        QueryResult result = influxDB.query(query);

        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper(); // thread-safe - can be reused
        List<Cpu> cpuList = resultMapper.toPOJO(result, Cpu.class);
        log.info(cpuList.toString());
        return cpuList;
    }

    public void querySqlUseCallBack(String sql) {
        Query query = new Query(sql, influxConfig.getInfluxDatabase());
        influxDB.query(query, queryResult -> {
            log.info(queryResult.toString());

        }, throwable -> log.error("error:{}", throwable));

    }

    /**
     * 创建influx数据库 --
     * influx 会对没有创建的数据库进行创建，如果已经创建则influx会自动忽略；
     * 也可以采用IF NOT EXISTS语法
     * 1. 可能在页面添加数据时创建新数据库，这时在对应service层加入创建influx数据库指令
     * 2. 在项目启动时，flyway初始化之后创建数据库(postConstruct)
     */
    public void createInfluxDatabase(String databaseName) {
        Query query = new Query("CREATE DATABASE " + databaseName, databaseName);
        influxDB.query(query);
    }

    public void createInfluxRetentionPolicy(String retentionPolicy) {
        influxDB.query(new Query("CREATE RETENTION POLICY " + retentionPolicy + " ON " + influxConfig.getInfluxDatabase() + " DURATION 30h REPLICATION 2 SHARD DURATION 30m DEFAULT"));
        influxDB.setRetentionPolicy(retentionPolicy);
    }

    /**
     * 插入数据:
     * tag的使用
     */
    public void writeRecord(String measurement, String tag, String record3) {
        influxDB.write(Point.measurement(measurement)
                .tag("point_name", tag)
                .addField("point_value", record3)
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .build());
    }

    /**
     * 插入数据:
     * tag的使用
     */
    public void insertCpu(Cpu cpu) {
        String rpName = "aRetentionPolicy";

        Point point = Point.measurementByPOJO(cpu.getClass()).addFieldsFromPOJO(cpu).build();

        influxDB.write(influxConfig.getInfluxDatabase(), rpName, point);
    }
}
