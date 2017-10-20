package com.wchukai.rpcintruder.test.action.dto;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chukai on 2017/10/20.
 */
public class ClassInfo {
    private String className;
    private List<MethodInfo> methodInfos;

    public ClassInfo(Class clazz) {
        this.className=clazz.getName();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        methodInfos=new ArrayList<>(declaredMethods.length);
        for(Method method: declaredMethods){
           methodInfos.add(new MethodInfo(method));
       }
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


}
