package com.wchukai;

import com.wchukai.rpcintruder.servlet.IntruderServlet;
import com.wchukai.rpcintruder.testinvoker.action.InvokerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

/**
 * @author chukai
 * @date 2017/10/23
 */
@EnableAutoConfiguration
@EnableWebMvc
@SpringBootApplication
@ImportResource(locations = {"classpath:service.xml"})
public class Application {
    public static final void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean indexServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new IntruderServlet());
        registration.addUrlMappings("/rpc-intruder");
        return registration;
    }

    @Bean
    public InvokerConfig invokerConfig() {
        InvokerConfig invokerConfig = new InvokerConfig();
        invokerConfig.setClassNames(Arrays.asList("com.wchukai.service.HelloService"));
        return invokerConfig;
    }
}
