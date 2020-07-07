package com.bsa.giphyWebAPI.Exceptions;

public class GiphyRequestFailed extends NotFoundException {
    public GiphyRequestFailed(String query) {
        super("Cannot found anything on external searching " + query);
    }
}
