package com.wchukai.rpcintruder.testinvoker.action;

import com.wchukai.rpcintruder.testinvoker.action.context.InvocationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chukai
 */
public class BeanInfoAction extends Action {
    @Override
    public Object doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return InvocationContext.getInstance().getBeanInfoCache();
    }
}
