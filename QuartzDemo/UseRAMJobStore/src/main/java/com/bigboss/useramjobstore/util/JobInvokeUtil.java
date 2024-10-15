package com.bigboss.useramjobstore.util;

import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * @author: maifuwa
 * @date: 2024/10/14 10:35
 * @description: invokeTarget 解析工具类
 */
public class JobInvokeUtil {

    public static void invokeMethod(String invokeTarget) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String beanName = getBeanName(invokeTarget);
        String methodName = getMethodName(invokeTarget);
        Object[] methodParams = getMethodParams(invokeTarget);

        Object bean = SpringUtil.getBean(beanName);
        Method method = bean.getClass().getMethod(methodName, getMethodParamTypes(methodParams));
        method.invoke(bean, methodParams);
    }

    public static String getBeanName(String invokeTarget) {
        return invokeTarget.substring(0, invokeTarget.indexOf("."));
    }

    public static String getMethodName(String invokeTarget) {
        return invokeTarget.substring(invokeTarget.indexOf(".") + 1, invokeTarget.indexOf("("));
    }

    public static Object[] getMethodParams(String invokeTarget) {
        String params = invokeTarget.substring(invokeTarget.indexOf("(") + 1, invokeTarget.indexOf(")"));
        return Stream.of(params.split(","))
                .filter(StringUtils::hasText)
                .map(String::trim)
                .map(JobInvokeUtil::parseParam)
                .toArray();
    }

    public static Object parseParam(String param) {
        if (param.equals("true") || param.equals("false")) {
            return Boolean.valueOf(param);
        }
        if (param.endsWith("D")) {
            return Double.valueOf(param.substring(0, param.length() - 1));
        }
        if (param.endsWith("L")) {
            Long tmp = Long.valueOf(param.substring(0, param.length() - 1));
            if (tmp <= Integer.MAX_VALUE) {
                return Integer.valueOf(tmp + "");
            }
            return tmp;
        }
        return param;
    }

    public static Class<?>[] getMethodParamTypes(Object[] methodParams) {
        return Stream.of(methodParams).map(Object::getClass).toArray(Class[]::new);
    }
}
