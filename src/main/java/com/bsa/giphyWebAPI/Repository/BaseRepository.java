package com.bsa.giphyWebAPI.Repository;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BaseRepository {
    private String cacheDirectory;
    private String usersDirectory;
    private String server;

    public String addServerAddress(String path) {
        return server + path;
    }
}
