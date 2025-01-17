package com.transaction.microservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final CustomHttpInterceptor customHttpInterceptor;

    public WebMvcConfig(CustomHttpInterceptor customHttpInterceptor) {
        this.customHttpInterceptor = customHttpInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customHttpInterceptor).addPathPatterns("/api/**");
    }

}
