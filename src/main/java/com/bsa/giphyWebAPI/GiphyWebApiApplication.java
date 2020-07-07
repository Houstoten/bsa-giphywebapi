package com.bsa.giphyWebAPI;

import com.bsa.giphyWebAPI.DTO.ImageReceiveDto;
import com.bsa.giphyWebAPI.Entity.GifImage;
import com.bsa.giphyWebAPI.Repository.BaseRepository;
import com.bsa.giphyWebAPI.utils.GifImageMapper;
import com.bsa.giphyWebAPI.utils.ImagePuller;
import com.bsa.giphyWebAPI.utils.ImageSaver;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@SpringBootApplication
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:dev.properties")
})
public class GiphyWebApiApplication {

    @Autowired
    private BaseRepository baseRepository;

    public static void main(String[] args) {
        var context = SpringApplication.run(GiphyWebApiApplication.class, args);
    }

    @PreDestroy
    public void onExit() throws IOException {
        FileUtils.deleteDirectory(new File(baseRepository.getUsersDirectory()));
        FileUtils.deleteDirectory(new File(baseRepository.getCacheDirectory()));
    }

}
