package com.summer.dlock.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({DlockImportSelector.class})
public @interface EnableDistributionLock {
    DLockType value() default DLockType.REDISSON;
}
