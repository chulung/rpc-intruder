package com.wchukai.rpcintruder.client;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Enhancer;

/**
 *
 * @author wchukai
 */
public class InterfaceProxyFactoryBean implements FactoryBean {

    private Class serviceClass;
    private InterfaceProxy serviceProxy ;
    @Override
    public Object getObject() throws Exception {
        return createProxy(this.serviceClass);
    }


    /**
     * 创建代理
     *
     * @param clazz service0 class
     * @param <T> type of service0
     * @return proxy of service0
     */
    public <T> T createProxy(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(serviceProxy);
        enhancer.setInterceptDuringConstruction(false);
        return (T) enhancer.create();
    }

    @Override
    public Class getObjectType() {
        return serviceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Class getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(Class serviceClass) {
        this.serviceClass = serviceClass;
    }

    public void setServiceProxy(InterfaceProxy serviceProxy) {
        this.serviceProxy = serviceProxy;
    }

    public InterfaceProxy getServiceProxy() {
        return serviceProxy;
    }
}
