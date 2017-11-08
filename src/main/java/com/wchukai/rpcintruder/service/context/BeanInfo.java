package com.wchukai.rpcintruder.service.context;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author chukai
 */
public class BeanInfo {
    @JsonIgnore
    private Class clazz;
    @JsonIgnore
    private Object bean;
    private String className;
    private String interfaceName;
    private List<MethodInfo> methodInfos;

    public BeanInfo(Class interfaceClz, Object bean) {
        this.interfaceName = interfaceClz.getName();
        this.clazz = bean.getClass();
        this.bean = bean;
        this.className = this.clazz.getName();
        Method[] declaredMethods = this.clazz.getDeclaredMethods();
        methodInfos = new ArrayList<>(declaredMethods.length);
        for (Method method : declaredMethods) {
            // i.E. jacoco coverage analysis
            if(method.isSynthetic()){
                continue;
            }
            methodInfos.add(new MethodInfo(this, method));
        }
        Collections.sort(methodInfos, new Comparator<MethodInfo>() {
            @Override
            public int compare(MethodInfo o1, MethodInfo o2) {
                return o1.getMethodName().compareTo(o2.getMethodName());
            }
        });
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        className = className;
    }

    public List<MethodInfo> getMethodInfos() {
        return methodInfos;
    }

    public void setMethodInfos(List<MethodInfo> methodInfos) {
        this.methodInfos = methodInfos;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
}
