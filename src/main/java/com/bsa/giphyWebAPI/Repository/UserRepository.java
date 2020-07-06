package com.bsa.giphyWebAPI.Repository;

import com.bsa.giphyWebAPI.utils.GifImageMapper;
import com.bsa.giphyWebAPI.utils.ImagePuller;
import com.bsa.giphyWebAPI.utils.ImageSaver;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class UserRepository {

    @Autowired
    private ImageSaver imageSaver;

    @Autowired
    private BaseRepository baseRepository;

    @Autowired
    private ImagePuller imagePuller;

    @Autowired
    private GifImageMapper imageMapper;

    public String getGifFromCache(String query, String userId) {
        try (Stream<Path> paths = Files.walk(Paths.get(baseRepository.getCacheDirectory() + "/" + query))) {
            Optional<Path> file = paths.filter(Files::isRegularFile).findAny();
            if (file.isPresent()) {
                imageSaver.copyImageToUserFolder(file.get(), userId);
            }
            return file.map(path -> path.normalize().toString())
                    .orElseGet(() -> getGifFromExternal(query, userId, 0));
        } catch (IOException e) {
            return getGifFromExternal(query, userId, 0);
        }
    }

    public String getGifFromExternal(String query, String userId, int counter) {
        if (counter <= 10) {//to avoid infinite loop
            return imagePuller.getImageReceiveDto(query)
                    .map(imageReceiveDto -> {
                        try {
                            return imageMapper.getGifImageFromImageReceiveDto(imageReceiveDto, query, userId);
                        } catch (IOException | UnirestException e) {
                            return null;
                        }
                    })
                    .map(gifImage -> gifImage.getImage()
                            .map(File::getPath)
                            .orElseThrow())
                    .orElseGet(() -> getGifFromExternal(query, userId, counter + 1));
        } else {
            return "errors with internet connection occurred, choose of existing or try later";
        }
    }
}
