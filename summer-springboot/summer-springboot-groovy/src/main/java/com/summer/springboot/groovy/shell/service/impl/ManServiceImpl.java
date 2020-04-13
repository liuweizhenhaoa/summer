package com.summer.springboot.groovy.shell.service.impl;

import com.summer.springboot.groovy.shell.model.Man;
import org.springframework.stereotype.Service;

@Service("manService")
class ManServiceImpl implements ManService {
    @Override
    public Man getInfoByName(String name) {
        return new Man((long) 1,name,"1233131312312");
    }

}
