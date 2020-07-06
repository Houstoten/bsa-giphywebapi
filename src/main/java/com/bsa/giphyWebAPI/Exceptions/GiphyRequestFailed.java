package com.bsa.giphyWebAPI.Exceptions;

public class GiphyRequestFailed extends RuntimeException {
    public GiphyRequestFailed(String query) {
        super("Cannot found anything on external searching " + query);
    }
}
