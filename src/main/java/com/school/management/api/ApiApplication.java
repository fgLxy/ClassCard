package com.school.management.api;

import com.school.management.api.netty.NettyServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class ApiApplication  implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(WebApplicationContext wac)   {
        DispatcherServlet servlet = new DispatcherServlet(wac);
        ServletRegistrationBean srb = new ServletRegistrationBean(servlet, "/api/*");
        srb.setName("ApiServlet");
        return srb;
    }

    @Override
    public void run(String... args) {
        NettyServer.start(9999);
    }
}
