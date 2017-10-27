package com.wchukai.rpcintruder.service.context;

import com.wchukai.rpcintruder.service.RpcRequest;
import com.wchukai.rpcintruder.service.ServiceConfiguration;
import com.wchukai.rpcintruder.service.action.ParametersHandler;
import com.wchukai.rpcintruder.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.*;

/**
 * @author chukai
 */
public class InvocationContext implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvocationContext.class);
    private static InvocationContext INSTANCE = new InvocationContext();
    private List<BeanInfo> beanInfoCache = new ArrayList<>();
    private Map<Class, BeanInfo> classInfoMap = new HashMap<>();
    private Map<Integer, MethodInfo> methodIdMap = new HashMap<>();
    private Map<String, MethodInfo> methodNameMap = new HashMap<>();
    private ApplicationContext applicationContext;
    private ServiceConfiguration serviceConfig;

    public InvocationContext() {
        INSTANCE = this;
    }

    public static InvocationContext getInstance() {
        return INSTANCE;
    }

    public void resolveBean(String className) throws ClassNotFoundException {
        Class interfaceClz = Class.forName(className);
        if (!interfaceClz.isInterface()) {
            throw new IllegalArgumentException(className + " is not a interface,please excluded in properties.");
        }
        Object bean = this.applicationContext.getBean(interfaceClz);
        if (bean == null) {
            throw new NoSuchBeanDefinitionException(className);
        }
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
                this.methodIdMap.put(methodInfo.getId(), methodInfo);
                this.methodNameMap.put(handleMethodKey(interfaceClz.getName(), methodInfo.getMethod().getName(), StringUtil.getTypeNames(methodInfo.getMethod().getParameterTypes())), methodInfo);
            }
            LOGGER.info("created baneInfo:{}", interfaceClz);
        }
    }

    public List<BeanInfo> getBeanInfoCache() {
        return beanInfoCache;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (serviceConfig == null) {
            serviceConfig = new ServiceConfiguration();
        }
        try {
            serviceConfig.initProperties();
        } catch (Exception e) {
            LOGGER.error("initialization failure :", e);
            throw new RuntimeException(e);
        }
        if (serviceConfig.getClassNames() != null) {
            for (String className : serviceConfig.getClassNames()) {
                try {
                    this.resolveBean(className);
                } catch (Exception e) {
                    LOGGER.error("class: {} is not load,cause:{}", className, e.getMessage());
                }
            }

        }
    }

    private void resolveBeans(List<String> classeNames) {

    }

    public Object invoke(Integer id, String argsJson) throws Exception {
        MethodInfo methodInfo = this.methodIdMap.get(id);
        if (methodInfo == null) {
            throw new IllegalArgumentException("cannot find mehodInfo,id=" + id);
        }
        Object[] args = ParametersHandler.readParams(argsJson);
        return invoke(new InvokerBody(methodInfo, args, null));
    }


    public Object invoke(RpcRequest rpcRequest) throws Exception {
        Object[] args1 = rpcRequest.getArgs();
        Class[] parameterTypes = new Class[args1.length];
        if (args1 != null) {
            for (int i = 0; i < args1.length; i++) {
                parameterTypes[i] = args1[i].getClass();
            }
        } else {
            parameterTypes = new Class[0];
        }
        String fullMethodKey = handleMethodKey(rpcRequest.getClassName(), rpcRequest.getMethodName(), rpcRequest.getArgsType());
        MethodInfo methodInfo = this.methodNameMap.get(fullMethodKey);
        if (methodInfo == null) {
            this.resolveBean(rpcRequest.getClassName());
        }
        methodInfo = this.methodNameMap.get(fullMethodKey);
        if (methodInfo == null) {
            throw new NoSuchMethodException(fullMethodKey);
        }
        return invoke(new InvokerBody(methodInfo, args1, rpcRequest));
    }

    private Object invoke(InvokerBody invokerBody) throws Exception {
        if (serviceConfig.getInvokeFilter() != null) {
            return serviceConfig.getInvokeFilter().invoke(invokerBody);
        }
        return invokerBody.invoke();
    }

    private String handleMethodKey(String className, String methodName, String[] parameterTypes) {
        StringBuffer sb = new StringBuffer();
        sb.append(className).append(".").append(methodName).append("(");
        for (String argTypeName : parameterTypes) {
            sb.append(argTypeName).append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), "");
        return sb.append(")").toString();
    }

    public boolean isEnabled() {
        return serviceConfig.isEnable();
    }
}
