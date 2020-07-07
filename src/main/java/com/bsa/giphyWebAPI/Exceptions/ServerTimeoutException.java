package com.bsa.giphyWebAPI.Exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerTimeoutException extends RuntimeException {
    public ServerTimeoutException(String query) {
        super("Cannot access and find " + query + ". Please, check another query or try again later.");
        log.error("Cannot access and find " + query + ". Please, check another query or try again later.");
    }
}
