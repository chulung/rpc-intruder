package com.wchukai.rpcintruder.service.action;

import com.wchukai.rpcintruder.service.context.InvocationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chukai
 */
public class RegisterBeanAction extends AbstractAction {
    @Override
    public Object doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String className = request.getParameter("className");
        InvocationContext.getInstance().resolveBean(className);
        return InvocationContext.getInstance().getBeanInfoCache();
    }
}
