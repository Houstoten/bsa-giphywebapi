package com.bsa.giphyWebAPI.Entity;

import lombok.*;

import java.awt.*;
import java.net.URL;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
public class GifImage {
    private String query;
    private URL url;
    private String name;
    private Date date;
}
