package com.bsa.giphyWebAPI.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class WrongHeaderInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws IOException {
        if (!"X-BSA-GIPHY".equals(request.getHeader("bsa-header"))) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Wrong or missing bsa-header");
            log.error(request.getRemoteAddr() + " wanted to connect with incorrect header!");
            return false;
        }
        return true;
    }
}