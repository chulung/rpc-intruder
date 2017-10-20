package com.wchukai.rpcintruder.test.action;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chukai on 2017/10/20.
 */
@Component
public class InvokerAction extends Action {


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String ClassName = request.getParameter("class");
        String methodName = request.getParameter("method");
    }
}
