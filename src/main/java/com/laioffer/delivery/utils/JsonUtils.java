package com.laioffer.delivery.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class JsonUtils {

    // ObjectMapper 用于 JSON 解析
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 将 JSON 字符串转换为 Map
    public static Map<String, Object> convertJsonToMap(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 将 JSON 字符串转换为 Map<String, Double>
    public static Map<String, Double> convertJsonToMapDouble(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Double>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 将对象转换为 JSON 字符串
    public static String convertObjectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
