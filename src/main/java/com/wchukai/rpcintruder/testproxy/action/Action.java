package com.wchukai.rpcintruder.testproxy.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chulung on 2017/10/2.
 */
public interface Action {
    public void execute(HttpServletRequest request, HttpServletResponse response);
}
