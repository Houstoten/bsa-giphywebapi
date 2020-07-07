package com.bsa.giphyWebAPI.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class WrongHeaderInterceptorConfig extends WebMvcConfigurerAdapter {
    @Autowired
    WrongHeaderInterceptor wrongHeaderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(wrongHeaderInterceptor);
    }
}