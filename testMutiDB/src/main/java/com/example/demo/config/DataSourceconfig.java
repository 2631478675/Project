package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceconfig {
    private final static Logger logger = LoggerFactory.getLogger(DataSourceconfig.class);

    @Bean(name = "primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource primaryDataSource() {

        logger.info("init begin ...");
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "secondDataSource")
    @ConfigurationProperties(prefix = "spring.cental")
    public DataSource secondDataSource() {
        return DataSourceBuilder.create().build();
    }
}
