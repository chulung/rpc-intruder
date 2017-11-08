package com.wchukai.rpcintruder.service.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wchukai.rpcintruder.service.action.ParametersHandler;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chukai
 */
public class MethodInfo {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.enableDefaultTyping();
    }

    private static final AtomicInteger ATOMIC_ID = new AtomicInteger(1);
    @JsonIgnore
    private BeanInfo beanInfo;
    private Integer id;
    @JsonIgnore
    private Method method;
    private String methodName;
    private String argsInfo;

    public MethodInfo(BeanInfo beanInfo, Method method) {
        this.id = ATOMIC_ID.getAndIncrement();
        this.beanInfo = beanInfo;
        this.method=method;
        Class<?>[] parameterTypes = method.getParameterTypes();
        this.methodName = method.getName()+resolveParamString(parameterTypes);
        argsInfo = ParametersHandler.createParamTemplate(parameterTypes);
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

    public BeanInfo getBeanInfo() {
        return beanInfo;
    }

    public void setBeanInfo(BeanInfo beanInfo) {
        this.beanInfo = beanInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    private String resolveParamString(Class<?>[] parameterTypes) {
        StringBuffer sb=new StringBuffer("(");
        for(int i=0;i<parameterTypes.length;i++){
            sb.append(parameterTypes[i].getSimpleName());
            if(i<parameterTypes.length-1){
                sb.append(",");
            }
        }
        return sb.append(")").toString();
    }
}