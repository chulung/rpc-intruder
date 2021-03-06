package com.wchukai.rpcintruder.service.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wchukai.rpcintruder.service.exception.ActionException;
import com.wchukai.rpcintruder.service.exception.InvokeTerminatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public abstract class AbstractAction {
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";
    public static final int ERROR_CODE = -1;
    public static final int SUCCESS_CODE = 0;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ObjectMapper responseWriter = new ObjectMapper();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object result = null;
        try {
            result = doAction(request, response);
            if (result != null) {
                Map<String, Object> jsonObj = new HashMap<>(2);
                jsonObj.put("code", SUCCESS_CODE);
                jsonObj.put("result", result);
                response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
                responseWriter.writeValue(response.getOutputStream(), jsonObj);
            }
        } catch (ActionException e) {
            Map<String, Object> jsonObj = new HashMap<>(2);
            jsonObj.put("code", ERROR_CODE);
            jsonObj.put("error", e.getMessage());
            response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
            responseWriter.writeValue(response.getOutputStream(), jsonObj);
        } catch (Exception ex) {
            if(!(ex instanceof InvokeTerminatedException)){
                logger.error("", ex);
            }else if (ex.getCause()!=null){
                ex= (Exception) ex.getCause();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ex.printStackTrace(new PrintStream(baos));
            String exception = baos.toString();
            Map<String, Object> jsonObj = new HashMap<>(2);
            jsonObj.put("code", ERROR_CODE);
            jsonObj.put("error", exception);
            response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
            responseWriter.writeValue(response.getOutputStream(), jsonObj);
        }
    }

    /**
     * 处理action
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return result
     * @throws Exception do action error
     */
    public abstract Object doAction(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
