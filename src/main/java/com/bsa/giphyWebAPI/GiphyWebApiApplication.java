package com.bsa.giphyWebAPI;

import com.bsa.giphyWebAPI.DTO.ImageReceiveDto;
import com.bsa.giphyWebAPI.Entity.GifImage;
import com.bsa.giphyWebAPI.utils.GifImageMapper;
import com.bsa.giphyWebAPI.utils.ImagePuller;
import com.bsa.giphyWebAPI.utils.ImageSaver;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.io.IOException;
import java.util.Optional;

@SpringBootApplication
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:dev.properties")
})
public class GiphyWebApiApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(GiphyWebApiApplication.class, args);
        ImagePuller imagePuller = context.getBean(ImagePuller.class);
        Optional<ImageReceiveDto> imageReceiveDto = imagePuller.getImageReceiveDto("mad");
        System.out.println(imageReceiveDto.map(dto -> {
            GifImage gif = null;
            try {
                gif = GifImageMapper.getGifImageFromImageReceiveDto(dto, "mad");
                if (ImageSaver.saveImage(gif)) {
                    System.out.println(gif.getName() + ".gif created!");
                } else {
                    System.out.println(gif.getName() + ".gif already exists!");
                }
            } catch (IOException | UnirestException ex) {
                System.out.println("IO exception occured");
            }
            return gif;
        }));
    }

}
