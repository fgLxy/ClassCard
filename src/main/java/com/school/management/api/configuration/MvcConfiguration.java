package com.school.management.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class MvcConfiguration extends DelegatingWebMvcConfiguration {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        try {
            registry.addResourceHandler("/images/**").addResourceLocations("file:///E:/class card/images/");
            registry.addResourceHandler("/videos/**").addResourceLocations("file:///E:/class card/videos/");
            registry.addResourceHandler("/files/**").addResourceLocations("file:///E:/class card/files/");
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.addResourceHandlers(registry);
    }
}
