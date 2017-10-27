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
 *
 * @author wchukai
 */
public class ClientConfiguration implements BeanDefinitionRegistryPostProcessor {
    protected List<String> classNames = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String url;


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

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
            beanDefinition.setBeanClass(InterfaceProxyFactoryBean.class);
            beanDefinition.setLazyInit(true);
            beanDefinition.setPrimary(true);
            beanDefinition.getPropertyValues().addPropertyValue("serviceClass", clazz);
            InterfaceProxy serviceProxy = new InterfaceProxy();
            serviceProxy.setUrl(this.url);
            beanDefinition.getPropertyValues().addPropertyValue("serviceProxy", serviceProxy);
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
