package com.summer.dlock.annotation;

import com.summer.common.exception.BussinessException;
import com.summer.dlock.lock.redisson.RedissonDlock;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class DlockImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        //get the cyustom  @EnableDistributionLock information
        if (annotationMetadata.getAnnotationAttributes(EnableDistributionLock.class.getName()) != null) {
            Map<String, Object> attrs = annotationMetadata.getAnnotationAttributes(EnableDistributionLock.class.getName());
            if (attrs.containsKey("value")) {
                if (attrs.get("value") == DLockType.REDISSON) {
                    return new String[]{RedissonDlock.class.getName()};
                } else {
                    throw new BussinessException("EnableDistributionLock type not defined");
                }
            }

        }
        return new String[]{RedissonDlock.class.getName()};
    }
}
