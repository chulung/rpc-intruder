package com.wchukai.rpcintruder.testinvoker.action;

import com.wchukai.rpcintruder.testinvoker.action.context.InvocationContext;
import com.wchukai.rpcintruder.testinvoker.action.context.MethodInfo;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chukai
 */
@Component
public class InvokeAction extends Action {


    @Override
    public Object doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = Integer.valueOf(request.getParameter("id"));
        MethodInfo methodInfo = InvocationContext.getInstance().getMethodById(id);
        if (methodInfo == null) {
            throw new IllegalArgumentException("cannot find mehodInfo,id=" + id);
        }
        Object[] args = ParametersHandler.readParams(request.getParameter("args"));
        Object rs = methodInfo.getMethod().invoke(methodInfo.getBeanInfo().getBean(), args);
        return rs == null ? "null" : rs;
    }

    private void resolveMethod(Object bean, String methodName, Object[] params) {

    }

    private Object resolveBean(String className) throws ClassNotFoundException {
        return applicationContext.getBeansOfType(Class.forName(className));
    }
}
