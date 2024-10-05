package org.bigboss.springsecuritydemo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: maifuwa
 * @date: 2024/10/7 9:27
 */
@Slf4j
@Component
public class JsonUtil {

    private static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        JsonUtil.objectMapper = objectMapper;
    }

    /**
     * 对象转Json格式字符串
     *
     * @param obj 对象
     * @return Json格式字符串
     */
    public static <T> String toJson(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Parse Object to String error : {}", e.getMessage());
            return null;
        }
    }


    /**
     * 字符串转换为自定义对象
     *
     * @param str   要转换的字符串
     * @param clazz 自定义对象的class对象
     * @return 自定义对象
     */
    public static <T> T toObject(String str, Class<T> clazz) {
        if (str == null || str.isBlank() || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? clazz.cast(str) : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            log.error("Parse String to Object error : {}", e.getMessage());
            return null;
        }
    }

    /**
     * 字符串转换为List
     *
     * @param str   要转换的字符串
     * @param clazz 自定义对象的class对象
     * @param <T>   自定义对象
     * @return List
     */
    public static <T> List<T> toList(String str, Class<T> clazz) {
        if (str == null || str.isBlank() || clazz == null) {
            return null;
        }
        try {
            return objectMapper.readValue(str, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            log.error("Parse String to List error : {}", e.getMessage());
            return null;
        }
    }
}
