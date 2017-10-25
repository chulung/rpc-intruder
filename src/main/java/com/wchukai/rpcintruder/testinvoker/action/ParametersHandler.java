package com.wchukai.rpcintruder.testinvoker.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author chukai
 */
public class ParametersHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParametersHandler.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enableDefaultTyping();
    }

    public static String createParamTemplate(Class<?>[] types) {
        try {
            return objectMapper.writeValueAsString(createEmptyArgs(types));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object[] readParams(String json) {
        try {
            return objectMapper.readValue(json, Object[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object[] createEmptyArgs(Class<?>[] parameterTypes) {
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            args[i] = createEmptyObject(parameterTypes[i]);
        }
        return args;
    }

    private static Object createEmptyObject(Class clazz) {
        Object obj = null;
        try {
            obj = objectMapper.readValue("0", clazz);
            if (obj instanceof String) {
                return "";
            }
        } catch (Exception e) {
            try {
                obj = objectMapper.readValue("[]", clazz);
            } catch (Exception e1) {
                try {
                    obj = objectMapper.readValue("{}", clazz);
                } catch (Exception e2) {
                    ;
                }
            }
        }
        return obj;
    }

}
