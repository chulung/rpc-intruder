package com.wchukai.rpcintruder.test.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chulung
 * @date 2017/10/2
 */
public abstract class Action implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private ObjectMapper objectMapper=new ObjectMapper();
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public abstract void execute(HttpServletRequest request, HttpServletResponse response);
}
