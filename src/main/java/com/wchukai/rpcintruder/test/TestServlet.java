package com.wchukai.rpcintruder.test;

import com.wchukai.rpcintruder.servlet.AbstractServlet;
import com.wchukai.rpcintruder.test.action.Action;
import com.wchukai.rpcintruder.test.action.AssetsAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chulung
 * @date 2017/10/2
 */
public class TestServlet extends AbstractServlet {

    private static final Map<String, Action> actions = new HashMap() {
        {
            put("asserts", new AssetsAction());
        }
    };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String act = request.getParameter("action");
        actions.get(act).execute(request, response);
    }
}
