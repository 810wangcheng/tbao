package com.jt.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ObjectMapperUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJSON(Object target){
        String result = null;
        try {
            result = mapper.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    public static <T>T toObject(String json,Class<T> targetClass){
        T t = null;
        try {
            t = mapper.readValue(json,targetClass);
        } catch (JsonParseException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return t;
    }
}
