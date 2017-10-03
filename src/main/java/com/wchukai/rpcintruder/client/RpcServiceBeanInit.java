package com.wchukai.rpcintruder.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenchukai on 2017/9/8.
 */
public class RpcServiceBeanInit implements BeanDefinitionRegistryPostProcessor {
    protected List<String> classNames = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private boolean checkEnv = true;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        String enable = System.getProperty("rpc.intruder");
        if (enable == null) {
            enable = System.getenv("rpc.intruder");
        }
        if (isCheckEnv() && !"enabled".equals(enable)) {
            throw new IllegalArgumentException("please configure a environment variable:rpc-inturder=enabled");
        }
        logger.info("RPC Intruder is being starting.");
        //初始化代理service
        for (String className : this.classNames) {
            Class clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            RootBeanDefinition beanDefinition = new RootBeanDefinition();
            beanDefinition.setBeanClass(ServiceFactoryBean.class);
            beanDefinition.setLazyInit(true);
            beanDefinition.getPropertyValues().addPropertyValue("serviceClass", clazz);
            String beanName = subName(clazz.getSimpleName());
            //注册bean
            registry.registerBeanDefinition(beanName + "_intruder", beanDefinition);
            logger.info("registerBeanDefinition :" + beanName);
        }
        logger.info("RPC Intruder completed.");
    }

    private String subName(String simpleName) {
        return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    public void setClassNames(List<String> classNames) {
        this.classNames = classNames;
    }

    public List getClassNames() {
        return classNames;
    }

    public boolean isCheckEnv() {
        return checkEnv;
    }

    public void setCheckEnv(boolean checkEnv) {
        this.checkEnv = checkEnv;
    }
}
