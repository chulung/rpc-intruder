package com.wchukai.rpcintruder.service.action;

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
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.enableDefaultTyping();
    }

    public static String createParamTemplate(Class<?>[] types) {
        try {
            return OBJECT_MAPPER.writeValueAsString(createEmptyArgs(types));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object[] readParams(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, Object[].class);
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
            obj = OBJECT_MAPPER.readValue("0", clazz);
            if (obj instanceof String) {
                return "";
            }
        } catch (Exception e) {
            try {
                obj = OBJECT_MAPPER.readValue("[]", clazz);
            } catch (Exception e1) {
                try {
                    obj = OBJECT_MAPPER.readValue("{}", clazz);
                } catch (Exception e2) {
                    ;
                }
            }
        }
        return obj;
    }

}
