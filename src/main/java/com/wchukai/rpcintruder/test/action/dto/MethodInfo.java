package com.wchukai.rpcintruder.test.action.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author chukai
 * @date 2017/10/20
 */


public class MethodInfo {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String methodName;
    private String argsInfo;

    public MethodInfo(Method method) {
        this.methodName = method.getName();
        argsInfo = objectMapper.writeValueAsString(createEmptyArgs(method.getParameterTypes()));
    }



    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getArgsInfo() {
        return argsInfo;
    }

    public void setArgsInfo(String argsInfo) {
        this.argsInfo = argsInfo;
    }

    private Object[] createEmptyArgs(Class<?>[] parameterTypes) {
        Map<String,Object> args=new LinkedHashMap<>();
        for(int i=0;i<parameterTypes.length;i++){
            objects[i]=createEmptyObject(parameterTypes.getClass());
            args.put()
        }
    }

    private Object createEmptyObject(Class clazz) {
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