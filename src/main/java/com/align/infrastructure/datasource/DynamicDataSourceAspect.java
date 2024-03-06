package com.align.infrastructure.datasource;


import com.align.infrastructure.annotation.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class DynamicDataSourceAspect {
    /**
     * Pointcut
     */
    @Pointcut("@annotation(com.align.infrastructure.annotation.DataSource)")
    public void dataSourcePointCut() {}

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        DataSource ds = null;
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        // get Annotation info
        ds = method.getAnnotation(DataSource.class);
        if (ds == null) {
            // if there is no annotation on the method, use master database.
            DynamicDataSource.setDataSourceKey("master");
            log.info("set default datasource is " + "slave");
        } else {
            //if the annotation on the method,use the value of annotation
            DynamicDataSource.setDataSourceKey(ds.value());
            log.info("set datasource is " + ds.value());
        }
        return point.proceed();
    }

    @After(value = "dataSourcePointCut()")
    public void afterSwitchDS(JoinPoint point) {
        DynamicDataSource.clearDataSourceKey();
        log.info("clean datasource");
    }


}