package com.summer.dlock.annotation;

import com.summer.common.exception.BussinessException;
import com.summer.dlock.lock.redisson.RedissonDLock;
import com.summer.dlock.lock.zk.ZookeeperDLock;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class DLockImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        //获取自定的@EnableLogInfo 信息 如果包含 onlySale 则只注入Sales class 否则 注入 Sales 和 Market 两个类
        if (annotationMetadata.getAnnotationAttributes(EnableDistributionLock.class.getName()) != null) {
            Map<String, Object> attrs = annotationMetadata.getAnnotationAttributes(EnableDistributionLock.class.getName());
            if(attrs.containsKey("value")){
                if (attrs.get("value") == DLockType.REDISSON) {
                    return new String[] {RedissonDLock.class.getName() };
                }else if(attrs.get("value") == DLockType.ZOOKEEPER){
                    return new String[] {ZookeeperDLock.class.getName() };
                }else {
                    throw new BussinessException("EnableDistributionLock type not defined");
                }
            }

            return new String[] {RedissonDLock.class.getName() };
        }

        // 将指定的类
        return new String[] { RedissonDLock.class.getName(), ZookeeperDLock.class.getName() };

    }
}
