package com.wchukai.rpcintruder.servlet;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpServlet;

/**
 * @author wchukai
 */
public class AbstractServlet extends HttpServlet implements ApplicationContextAware {
    protected static ApplicationContext applicationContext; // Spring应用上下文环境

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AbstractServlet.applicationContext = applicationContext;
    }

}
