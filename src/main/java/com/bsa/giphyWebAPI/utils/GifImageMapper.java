package com.bsa.giphyWebAPI.utils;

import com.bsa.giphyWebAPI.DTO.ImageReceiveDto;
import com.bsa.giphyWebAPI.Entity.GifImage;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Component
public class GifImageMapper {
    public static GifImage getGifImageFromImageReceiveDto(ImageReceiveDto imageReceiveDto, String query) throws IOException {
        return GifImage.builder()
                .url(imageReceiveDto.getUrl())
                .name(imageReceiveDto.getId())
                .date(new Date())
                .query(query)
                .build();
    }
}
