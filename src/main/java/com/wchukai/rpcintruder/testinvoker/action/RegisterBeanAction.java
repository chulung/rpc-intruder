package com.wchukai.rpcintruder.testinvoker.action;

import com.wchukai.rpcintruder.testinvoker.action.context.InvocationContext;
import com.wchukai.rpcintruder.testinvoker.action.exception.ActionException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chukai
 */
@Component
public class RegisterBeanAction extends Action {
    @Override
    public Object doAction(HttpServletRequest request, HttpServletResponse response) {
        String className = request.getParameter("className");
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ActionException(className + " is not found.");
        }
        Object bean = applicationContext.getBean(clazz);
        InvocationContext.getInstance().resolveBean(clazz,bean);
        return InvocationContext.getInstance().getBeanInfoCache();
    }
}
