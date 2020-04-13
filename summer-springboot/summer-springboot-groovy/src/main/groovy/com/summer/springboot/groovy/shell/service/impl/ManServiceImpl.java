package com.summer.springboot.groovy.shell.service.impl;

import org.springframework.stereotype.Service;
import com.summer.springboot.groovy.shell.model.Man;

@Service("manService")
class ManServiceImpl implements ManService {
    @Override
    public Man getInfoByName(String name) {
        return new Man((long) 1,name,"1233131312312");
    }

}
