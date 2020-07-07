package com.bsa.giphyWebAPI.Exceptions;

public class ServerTimeoutException extends RuntimeException {
    public ServerTimeoutException(String query) {
        super("Cannot access and find " + query + ". Please, check another query or try again later.");
    }
}
