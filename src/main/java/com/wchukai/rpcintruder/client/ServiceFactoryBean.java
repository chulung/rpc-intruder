package com.wchukai.rpcintruder.client;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Enhancer;

/**
 * Created by wenchukai on 2017/9/8.
 */
public class ServiceFactoryBean implements FactoryBean {

    private Class serviceClass;
    private ServiceProxy serviceProxy ;
    @Override
    public Object getObject() throws Exception {
        return createProxy(this.serviceClass);
    }


    /**
     * 创建代理
     *
     * @param clazz
     * @param <T>
     * @return
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

    public void setServiceProxy(ServiceProxy serviceProxy) {
        this.serviceProxy = serviceProxy;
    }

    public ServiceProxy getServiceProxy() {
        return serviceProxy;
    }
}
