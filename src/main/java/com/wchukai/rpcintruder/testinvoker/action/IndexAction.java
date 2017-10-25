package com.wchukai.rpcintruder.testinvoker.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chukai
 */
public class IndexAction extends AssetsAction {
    @Override
    public Object doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

        super.doAction(new HttpServletRequestWrapper(request) {
            @Override
            public String getParameter(String name) {
                if ("path".equals(name)) {
                    return "/index.html";
                }
                return super.getParameter(name);
            }
        }, response);
        return null;
    }
}
