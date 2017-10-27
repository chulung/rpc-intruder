package com.wchukai.rpcintruder.service.action;

import com.wchukai.rpcintruder.service.context.InvocationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chukai
 */
public class InvokeAction extends Action {


    @Override
    public Object doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = Integer.valueOf(request.getParameter("id"));
        Object rs = InvocationContext.getInstance().invoke(id, request.getParameter("args"));
        return rs == null ? "null" : rs;
    }

}
