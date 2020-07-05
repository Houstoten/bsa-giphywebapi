package com.bsa.giphyWebAPI.utils;

import com.bsa.giphyWebAPI.DTO.ImageReceiveDto;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Component
public class ImageSaver {
    public Optional<File> saveImage(ImageReceiveDto imageReceiveDto, String query, String userId) throws IOException, UnirestException {
        File file = new File("./cache/" + query + "/" + imageReceiveDto.getId() + ".gif");
        if (file.exists()) {
            return Optional.empty();
        }
        Unirest.setTimeouts(0, 0);
        FileUtils.copyInputStreamToFile(
                Unirest.get(imageReceiveDto.getUrl().toString())
                        .asBinary()
                        .getBody()
                , file);
        FileUtils.copyFileToDirectory(file, new File("./users/" + userId + "/" + query));
        return Optional.of(file);
    }
}
