package com.align.infrastructure.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Autowired
    DataSource master;

    @Autowired
    DataSource slave;

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSourceKey(String dbName) {
        contextHolder.set(dbName);
    }

    public static String getDataSourceKey() {
        return contextHolder.get();
    }

    public static void clearDataSourceKey() {
        contextHolder.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSourceKey();
    }

    @Override
    public void afterPropertiesSet() {
        Map<Object,Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", master);
        targetDataSources.put("slave", slave);
        // set all datasources
        super.setTargetDataSources(targetDataSources);
        // set default datasource
        super.setDefaultTargetDataSource(master);

        super.afterPropertiesSet();
    }
}