package com.wchukai.rpcintruder.test.action;

import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chulung on 2017/10/2.
 */
@Component
public class AssetsAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getParameter("path");
        InputStream in = this.getClass().getResourceAsStream(path);
        if (in != null) {

            try (ServletOutputStream out = response.getOutputStream()) {
                byte[] e = new byte[1024];
                boolean len = true;
                int len1;
                while ((len1 = in.read(e)) != -1) {
                    out.write(e, 0, len1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
