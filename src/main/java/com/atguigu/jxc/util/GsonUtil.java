package com.atguigu.jxc.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GsonUtil {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GsonUtil.class);

    /**
     * 对象转换为json
     *
     * @param value
     * @return
     */
    public static String createJsonString(Object value) {
        Gson gson = new Gson();
        String str = gson.toJson(value);
//      LOGGER.info("转换JSON成功!!{}", str);
        return str;
    }

    /**
     * json 转换为对象
     *
     * @param jsonString
     * @param
     * @return
     */
    public static <T> T getPerson(String jsonString, Class<T> clazz) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, clazz);
        } catch (Exception e) {
            LOGGER.error("JSON转换成对象失败!!{}{}", jsonString, e);
        }
        return t;
    }

    /**
     * json转换为List<Object>
     * @param jsonString
     * @param
     * @return
     */
    public static <T> List<T> getPersons(String jsonString, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {}.getType());
        } catch (Exception e) {
            LOGGER.error("JSON转换成对象失败!!{}{}", jsonString, e);
        }
        return list;
    }

    /**
     * json转换为List<Map<String, Object>>
     * @param jsonString
     * @return
     */
    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {
            }.getType());
        } catch (Exception e) {
            LOGGER.error("JSON转换成对象失败!!{}{}", jsonString, e);
        }
        return list;
    }

    /**
     * 将Map转化为Json
     *
     * @param map
     * @return String
     */
    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }
}
