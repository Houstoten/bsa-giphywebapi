package com.bsa.giphyWebAPI.utils;

import com.bsa.giphyWebAPI.DTO.ImageReceiveDto;
import com.bsa.giphyWebAPI.Entity.GifImage;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Component
public class GifImageMapper {

    @Autowired
    private ImageSaver imageSaver;

    public GifImage getGifImageFromImageReceiveDto(ImageReceiveDto imageReceiveDto, String query, Optional<String> userId)
            throws IOException, UnirestException {
        return GifImage.builder()
                .userId(userId)
                .image(imageSaver.saveImage(imageReceiveDto, query, userId))
                .name(imageReceiveDto.getId())
                .query(query)
                .build();
    }
}
