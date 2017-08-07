package com.bingkun.weixin.qyh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by pengjikun on 2017/7/31.
 */
@SpringBootApplication
@EnableScheduling //可配置定时任务
@MapperScan(basePackages="com.bingkun.weixin.qyh.dao") //扫描Mapper接口
public class Start extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Start.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Start.class);
    }
}
