package com.wchukai.rpcintruder.testinvoker.action.context;

import com.wchukai.rpcintruder.testinvoker.action.InvokerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;
/**
 * @author chukai
 */
@Component
public class InvocationContext implements InitializingBean, ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvocationContext.class);
    private static InvocationContext INSTANCE = new InvocationContext();
    private List<BeanInfo> beanInfoCache = new ArrayList<>();
    private Map<Class, BeanInfo> classInfoMap = new HashMap<>();
    private Map<Integer, MethodInfo> methodInfoMap = new HashMap<>();
    private ApplicationContext applicationContext;

    @Autowired(required = false)
    private InvokerConfig invokerConfig;

    public static InvocationContext getInstance() {
        return INSTANCE;
    }

    public void resolveBean(Class interfaceClz, Object bean) {
        if (!classInfoMap.containsKey(bean.getClass())) {
            BeanInfo beanInfo = new BeanInfo(interfaceClz, bean);
            classInfoMap.put(bean.getClass(), beanInfo);
            beanInfoCache.add(beanInfo);
            Collections.sort(beanInfoCache, new Comparator<BeanInfo>() {
                @Override
                public int compare(BeanInfo o1, BeanInfo o2) {
                    return o1.getClassName().compareTo(o2.getClassName());
                }
            });
            for (MethodInfo methodInfo : beanInfo.getMethodInfos()) {
                this.methodInfoMap.put(methodInfo.getId(), methodInfo);
            }
            LOGGER.info("created baneInfo:{}", interfaceClz);
        }
    }

    public MethodInfo getMethodById(Integer id) {
        return this.methodInfoMap.get(id);
    }

    public List<BeanInfo> getBeanInfoCache() {
        return beanInfoCache;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        INSTANCE = this;
        if (invokerConfig != null && invokerConfig.getClassNames() != null) {
            for (String name : invokerConfig.getClassNames()) {
                Class<?> clazz = Class.forName(name);
                this.resolveBean(clazz, this.applicationContext.getBean(clazz));
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
