package com.bsa.giphyWebAPI.Exceptions;

public class InvalidRequestException extends InvalidException {
    public InvalidRequestException(String invalidQuery) {
        super("invalid request catched: " + invalidQuery);
    }
}
