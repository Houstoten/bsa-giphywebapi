package com.bsa.giphyWebAPI.DTO;

import lombok.Getter;
import lombok.Setter;

import java.net.URL;

@Getter
@Setter
public class ImageReceiveDto {
    private URL url;
    private String id;
}
