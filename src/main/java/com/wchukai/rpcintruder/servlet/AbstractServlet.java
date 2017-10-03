package com.wchukai.rpcintruder.servlet;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpServlet;

/**
 * Created by chulung on 2017/10/2.
 */
public class AbstractServlet extends HttpServlet implements ApplicationContextAware {
    protected static ApplicationContext applicationContext; // Spring应用上下文环境

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
