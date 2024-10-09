package com.bigboss.usedjdbcjobstore.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: maifuwa
 * @date: 2024/10/8 11:26
 */
@Slf4j
@Component
public class SpringUtil {

    private static ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static Class<?> getBeanClass(String name) {
        return applicationContext.getType(name);
    }
}
