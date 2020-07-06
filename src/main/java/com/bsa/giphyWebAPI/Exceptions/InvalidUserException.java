package com.bsa.giphyWebAPI.Exceptions;

public class InvalidUserException extends InvalidException {
    public InvalidUserException(String userName) {
        super("invalid user catched: " + userName);
    }
}
