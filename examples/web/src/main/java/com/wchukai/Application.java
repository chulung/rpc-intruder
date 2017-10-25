package com.wchukai;

import com.wchukai.model.Hello;
import com.wchukai.model.World;
import com.wchukai.service.HelloService;
import com.wchukai.service.HelloService2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author chukai
 * @date 2017/10/25
 */
@EnableAutoConfiguration
@EnableWebMvc
@SpringBootApplication
@ImportResource(locations = {"classpath:client.xml"})
public class Application implements EmbeddedServletContainerCustomizer, ApplicationListener<ContextRefreshedEvent> {
    private Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private HelloService helloService;

    @Autowired
    private HelloService2 helloService2;

    public static final void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        configurableEmbeddedServletContainer.setPort(8081);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // dubbo
        System.out.println("dubbo======" + helloService.invokeHello(1, new Hello(), new World()) + "");

        // interuder
        System.out.println("interuder======" + helloService2.invokeHello(2, new Hello(), new World()) + "");
    }
}
