package com.bigboss.useramjobstore.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author: maifuwa
 * @date: 2024/10/7 9:27
 * @description: Json解析工具类
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
    public static <T> String toJson(T obj) throws JsonProcessingException {
        return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
    }


    /**
     * 字符串转换为自定义对象
     *
     * @param str   要转换的字符串
     * @param clazz 自定义对象的class对象
     * @return 自定义对象
     */
    public static <T> T toObject(String str, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(str, clazz);
    }

    /**
     * 字符串转换为List
     *
     * @param str   要转换的字符串
     * @param clazz 自定义对象的class对象
     * @param <T>   自定义对象
     * @return List
     */
    public static <T> List<T> toList(String str, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(str, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    /**
     * 字符串转换为Map
     *
     * @param str        要转换的字符串
     * @param keyClass   key的class对象
     * @param valueClass value的class对象
     * @param <T>        key
     * @param <R>        value
     * @return Map
     */
    public static <T, R> Map<T, R> toMap(String str, Class<T> keyClass, Class<R> valueClass) throws JsonProcessingException {
        return objectMapper.readValue(str, objectMapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass));
    }

    /**
     * 创建 empty JsonNode
     *
     * @return ObjectNode
     */
    public static JsonNode createJsonNode() {
        return objectMapper.createObjectNode();
    }

    /**
     * 将str转为JsonNode
     *
     * @param str str
     * @return JsonNode
     */
    public static JsonNode toJsonNode(String str) throws JsonProcessingException {
        return objectMapper.readTree(str);
    }

    /**
     * 将pojo转为JsonNode
     *
     * @param obj pojo
     * @return JsonNode
     */
    public static JsonNode toJsonNode(Object obj) {
        return objectMapper.valueToTree(obj);
    }

}
