package com.wchukai.rpcintruder.testinvoker.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wchukai.rpcintruder.testinvoker.action.exception.ActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chukai
 */
public abstract class Action implements ApplicationContextAware {
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";
    public static final int ERROR_CODE = -1;
    public static final int SUCCESS_CODE = 0;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    protected static ApplicationContext applicationContext;
    private ObjectMapper responseWriter = new ObjectMapper();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        Action.applicationContext = applicationContext;
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object result = null;
        try {
            result = doAction(request, response);
            if (result != null) {
                Map<String, Object> jsonObj = new HashMap<>();
                jsonObj.put("code", SUCCESS_CODE);
                jsonObj.put("result", result);
                response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
                responseWriter.writeValue(response.getOutputStream(), jsonObj);
            }
        } catch (ActionException e) {
            logger.info("", e);
            Map<String, Object> jsonObj = new HashMap<>();
            jsonObj.put("code", ERROR_CODE);
            jsonObj.put("error", e.getMessage());
            response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
            responseWriter.writeValue(response.getOutputStream(), jsonObj);
        } catch (Exception e) {
            logger.error("", e);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            String exception = baos.toString();
            Map<String, Object> jsonObj = new HashMap<>();
            jsonObj.put("code", ERROR_CODE);
            jsonObj.put("error", exception);
            response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
            responseWriter.writeValue(response.getOutputStream(), jsonObj);
        }
    }

    public abstract Object doAction(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
