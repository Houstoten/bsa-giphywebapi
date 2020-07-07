package com.bsa.giphyWebAPI.Entity;

import lombok.*;

import java.io.File;
import java.util.Date;
import java.util.Optional;

@Getter
@Setter
@ToString
@Builder
public class GifImage {
    private String userId;
    private String query;
    private Optional<File> image;
    private String name;
}
