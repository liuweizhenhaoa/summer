package com.summer.springboot.groovy.shell.service.impl

import com.summer.springboot.groovy.mapper.UserMapper
import com.summer.springboot.groovy.service.BaseGroovySpot
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.sql.DataSource

@Component("groovyBean")
class GroovyGroovySpot implements BaseGroovySpot {

    @Autowired
    private UserMapper db0UserMapper;

    @Autowired
//    @Qualifier("db0")
    private final DataSource dataSource0;

    @Override
    public String test() {
        String result
        int i = db0UserMapper.addUserInfo("lpw", "test")
        if (i > 0) {
            result = "添加成功"
            println("添加成功")
        } else {
            result = "添加失败"
            println("添加失败")
        }
        return result
    }

    @Override
    public String test2() {
        def sql = new Sql(dataSource0)
        sql.eachRow("SELECT id, name, password FROM user_info WHERE id >= 0 ") {
            row -> println("id = " + row[0] +
                    ", name = " + row[1] +
                    ", password = " + row[2])
        }
        return "success"
    }
}
