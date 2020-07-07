package com.bsa.giphyWebAPI.Repository;

import com.bsa.giphyWebAPI.Exceptions.NotFoundException;
import com.bsa.giphyWebAPI.Exceptions.ServerTimeoutException;
import com.bsa.giphyWebAPI.utils.GifImageMapper;
import com.bsa.giphyWebAPI.utils.ImagePuller;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class CacheRepository {

    @Autowired
    private BaseRepository baseRepository;

    @Autowired
    private ImagePuller imagePuller;

    @Autowired
    private GifImageMapper imageMapper;

    public ResponseEntity<List<Map<String, Object>>> getAllCache(String query) {
        try (Stream<String> paths = Optional
                .of(new File(baseRepository.getCacheDirectory() + "/" + query))
                .map(File::listFiles).map(Arrays::asList)
                .orElseThrow(() -> new NotFoundException("Can`t find in cache folder : " + query))
                .stream().filter(File::isFile).map(File::getName)) {

            List<Map<String, Object>> list = new ArrayList<>();
            list.add(new HashMap<>(Map.of("query", query)));

            list.get(0).put("gifs", Files
                    .walk(Paths.get(baseRepository.getCacheDirectory() + "/" + query))
                    .filter(Files::isRegularFile)
                    .map(Path::toAbsolutePath)
                    .map(Path::normalize)
                    .collect(Collectors.toList()));

            return ResponseEntity.status(HttpStatus.OK).body(list);

        } catch (IOException e) {
            throw new NotFoundException("Can`t find cache folder");
        }
    }

    public ResponseEntity<List<Map<String, Object>>> getAllCache() {
        try (Stream<String> paths = Optional
                .of(new File(baseRepository.getCacheDirectory()))
                .map(file -> file.listFiles(File::isDirectory)).map(Arrays::asList)
                .orElseThrow(() -> new NotFoundException("Cache folder is clear")).stream().map(File::getName)) {

            List<Map<String, Object>> list = new ArrayList<>();
            paths.forEach(path -> list.add(
                    new HashMap<>(Map.of("query", path))));

            for (Map<String, Object> map : list) {
                map.put("gifs", Files
                        .walk(Paths.get(baseRepository.getCacheDirectory() + "/" + map.get("query")))
                        .filter(Files::isRegularFile)
                        .map(Path::toAbsolutePath)
                        .map(Path::normalize)
                        .collect(Collectors.toList()));
            }
            return ResponseEntity.status(HttpStatus.OK).body(list);

        } catch (IOException e) {
            throw new NotFoundException("Can`t find cache folder");
        }
    }

    public String generateCache(String query, int counter) {
        if (counter <= 10) {//to avoid infinite loop
            return imagePuller.getImageReceiveDto(query)
                    .map(imageReceiveDto -> {
                        try {
                            return imageMapper
                                    .getGifImageFromImageReceiveDto(imageReceiveDto, query, Optional.empty());
                        } catch (IOException | UnirestException e) {
                            return null;
                        }
                    })
                    .map(gifImage -> gifImage.getImage()
                            .map(File::getAbsolutePath)
                            .orElseThrow())
                    .orElseGet(() -> generateCache(query, counter + 1));
        } else {
            throw new ServerTimeoutException(query);
        }
    }

    public void clearCache() {
        try {
            FileUtils.deleteDirectory(new File(baseRepository.getCacheDirectory()));
        } catch (IOException e) {
            throw new NotFoundException("Cache folder doesn't exist");
        }
    }
}
