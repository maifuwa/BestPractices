package com.bigboss.useramjobstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class UseRamJobStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(UseRamJobStoreApplication.class, args);
    }

}
