package com.bsa.giphyWebAPI.Interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WrongHeaderInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws IOException {
        if (!"X-BSA-GIPHY".equals(request.getHeader("bsa-header"))) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Wrong or missing bsa-header");
            return false;
        }
        return true;
    }
}