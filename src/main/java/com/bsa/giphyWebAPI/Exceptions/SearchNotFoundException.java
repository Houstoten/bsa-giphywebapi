package com.bsa.giphyWebAPI.Exceptions;

public class SearchNotFoundException extends NotFoundException {
    public SearchNotFoundException(String query) {
        super("Nothing found in user folder by query " + query);
    }
}
