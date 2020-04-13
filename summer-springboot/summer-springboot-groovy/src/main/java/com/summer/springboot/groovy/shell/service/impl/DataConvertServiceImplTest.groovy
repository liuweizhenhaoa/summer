package com.summer.springboot.groovy.service.impl

import com.summer.common.utils.FastJsonUtil
import com.summer.springboot.groovy.model.Article
import com.summer.springboot.groovy.service.DataConvertService
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

/**
 * @Author liuwei
 * @Description //TODO
 * @Date 1$ 1$
 **/
@Service("demo1DataConvertService")
@Slf4j
class DataConvertServiceImplTest implements DataConvertService {
    @Override
    Article convert(String content) {
        log.info("demo111111111111111");
        return FastJsonUtil.json2Bean(content,Article.class);
    }
}
