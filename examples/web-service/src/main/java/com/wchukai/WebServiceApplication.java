package com.wchukai;

import com.wchukai.rpcintruder.service.ServiceServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author chukai
 * @date 2017/10/23
 */
@EnableAutoConfiguration
@EnableWebMvc
@SpringBootApplication
@ImportResource(locations = {"classpath:service.xml"})
public class WebServiceApplication {
    public static final void main(String[] args) {
        SpringApplication.run(WebServiceApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean indexServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServiceServlet());
        registration.addUrlMappings("/rpc-intruder");
        return registration;
    }

}
