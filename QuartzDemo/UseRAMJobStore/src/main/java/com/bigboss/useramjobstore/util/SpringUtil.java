package com.bigboss.useramjobstore.util;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
/**
 * @author: maifuwa
 * @date: 2024/10/11 11:02
 * @description:
 */
@Component
public class SpringUtil {

    private static ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static Class<?> getBeanClass(String name) {
        return applicationContext.getType(name);
    }

    @SneakyThrows
    public static <T> T copyBean(Object source, Class<T> target) {
        Constructor<T> constructor = target.getConstructor();
        T t = constructor.newInstance();
        BeanUtils.copyProperties(source, t);
        return t;
    }

}
