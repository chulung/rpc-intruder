package com.wchukai.rpcintruder.service.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chukai
 */
public class IndexAction extends AssetsAction {

    public static final String PATH = "path";

    @Override
    public Object doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

        super.doAction(new HttpServletRequestWrapper(request) {
            @Override
            public String getParameter(String name) {
                if (PATH.equals(name)) {
                    return "index.html";
                }
                return super.getParameter(name);
            }
        }, response);
        return null;
    }
}
