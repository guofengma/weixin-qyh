package com.bingkun.weixin.qyh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by pengjikun on 2017/7/31.
 */
@SpringBootApplication
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
