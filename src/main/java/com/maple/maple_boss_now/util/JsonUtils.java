package com.maple.maple_boss_now.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maple.maple_boss_now.config.serializer.GsonZonedDateTimeAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;


public class JsonUtils {
    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    //    private static ObjectMapper objectMapper = MvcConfig.getObjectMapper();
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }


    public static <T> T getJSONStringToObject(String jsonStr, Class<T> clz) {
        T object = null;
        try {
            object = objectMapper.readValue(jsonStr, clz);
        } catch (Exception e) {
            logger.error("e", e);
        }

        return object;
    }

    public static String getObjectToString(Object obj) {
        return getObjectToString(obj, false);
    }

    /**
     * POJO객체를 문자열로 변환
     * @param obj
     * @param isGson    Google GSON으로 동작
     *                   - JsonProperty.Access.WRITE_ONLY 옵션 동작 안하므로 테스트 케이스 같은데서 사용
     * @return
     */
    public static String getObjectToString(Object obj, boolean isGson) {
        try {
            if (isGson) {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(ZonedDateTime.class, new GsonZonedDateTimeAdapter())
                        .setPrettyPrinting()
                        .create();
                return gson.toJson(obj);
            } else {
                return objectMapper.writeValueAsString(obj);
            }
        } catch (Exception e) {
            logger.error("e", e);
            return null;
        }
    }
}
