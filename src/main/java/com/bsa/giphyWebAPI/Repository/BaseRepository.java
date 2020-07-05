package com.bsa.giphyWebAPI.Repository;

import lombok.Getter;

@Getter
public class BaseRepository {
    private String cacheDirectory;
    private String usersDirectory;

    public BaseRepository(String cacheDirectory, String usersDirectory) {
        this.cacheDirectory = cacheDirectory;
        this.usersDirectory = usersDirectory;
    }
}
