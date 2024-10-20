package com.bigboss.mybatisflexdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@MapperScan("com.bigboss.mybatisflexdemo.mapper")
@EnableAspectJAutoProxy
@SpringBootApplication
public class MyBatisFlexDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBatisFlexDemoApplication.class, args);
    }

}
