package com.summer.springboot.hbase.service.impl;

import com.summer.hbase.config.api.HbaseTemplate;
import com.summer.hbase.config.api.RowMapper;
import com.summer.springboot.hbase.dao.CityRowMapper;
import com.summer.springboot.hbase.entity.City;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceImpl {

    @Autowired
    private HbaseTemplate hbaseTemplate;

    public List<City> query(String startRow, String stopRow) {
        Scan scan = new Scan(Bytes.toBytes(startRow), Bytes.toBytes(stopRow));
        scan.setCaching(5000);
        List<City> dtos = this.hbaseTemplate.find("people_table", scan, new CityRowMapper());
        return dtos;
    }

    public City query(String row) {
        City dto = this.hbaseTemplate.get("people_table", row, new RowMapper<City>() {
            @Override
            public City mapRow(Result result, int rowNum) throws Exception {
                return null;
            }
        });
        return dto;
    }

    public void saveOrUpdate() {
        List<Mutation> saveOrUpdates = new ArrayList<Mutation>();
        Put put = new Put(Bytes.toBytes("135xxxxxx"));
        put.addColumn(Bytes.toBytes("people"), Bytes.toBytes("name"), Bytes.toBytes("test"));
        saveOrUpdates.add(put);

        this.hbaseTemplate.saveOrUpdates("people_table", saveOrUpdates);
    }
}