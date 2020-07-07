package com.bsa.giphyWebAPI.Repository;

import com.bsa.giphyWebAPI.Exceptions.*;
import com.bsa.giphyWebAPI.utils.CsvReaderWriter;
import com.bsa.giphyWebAPI.utils.GifImageMapper;
import com.bsa.giphyWebAPI.utils.ImagePuller;
import com.bsa.giphyWebAPI.utils.ImageSaver;
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
public class UserRepository {

    @Autowired
    private CsvReaderWriter csvReaderWriter;

    @Autowired
    private InnerCacheRepository innerCacheRepository;

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
            throw new ServerTimeoutException(query);
        }
    }

    public String searchGifInUserFolder(String query, String userId) {
        try (Stream<Path> paths = Files.walk(Paths.get(baseRepository.getUsersDirectory() + "/" + userId + "/" + query))) {
            Optional<Path> file = paths.filter(Files::isRegularFile).findAny();
            return file.map(path -> path.normalize().toString()).orElseThrow(() -> new SearchNotFoundException(query));
        } catch (IOException ex) {
            throw new SearchNotFoundException(query);
        }
    }

    public String searchGifInInnerCache(String query, String userId) {
        Optional<String> path = innerCacheRepository.searchPath(userId, query);
        return path.orElseGet(() -> searchGifInUserFolder(query, userId));
    }

    public ResponseEntity<List<Map<String, String>>> getUserHistory(String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(csvReaderWriter.readCsv(userId));
    }

    public ResponseEntity<List<Map<String, Object>>> getAllUserFiles(String userId) {

        try (Stream<String> paths = Optional
                .of(new File(baseRepository.getUsersDirectory() + "/" + userId))
                .map(file -> file.listFiles(File::isDirectory)).map(Arrays::asList)
                .orElseThrow(() -> new InvalidUserException(userId)).stream().map(File::getName)) {

            List<Map<String, Object>> list = new ArrayList<>();
            paths.forEach(path -> list.add(
                    new HashMap<>(Map.of("query", path))));

            for (Map<String, Object> map : list) {
                map.put("gifs", Files
                        .walk(Paths.get(baseRepository.getUsersDirectory() + "/" + map.get("query")))
                        .filter(Files::isRegularFile)
                        .map(Path::toAbsolutePath)
                        .collect(Collectors.toList()));
            }
            return ResponseEntity.status(HttpStatus.OK).body(list);

        } catch (IOException e) {
            throw new InvalidUserException(userId);
        }
    }

    public void cleanUserHistory(String userId) {
        try {
            Files.deleteIfExists(Paths.get(baseRepository.getUsersDirectory() + "/" + userId + "/history.csv"));
        } catch (IOException e) {
            throw new InvalidException("Something went wrong while deleting " + userId + "/history.csv");
        }
    }

    public void resetUserCache(String userId, String query) {
        innerCacheRepository.deletePath(userId, Optional.of(query), Optional.empty());
    }

    public void resetUserCache(String userId){
        innerCacheRepository.deletePath(userId, Optional.empty(), Optional.empty());
    }

    public void cleanUser(String userId) {
        try {
            FileUtils.deleteDirectory(new File(baseRepository.getUsersDirectory()+"/"+userId));
            innerCacheRepository.deletePath(userId, Optional.empty(), Optional.empty());
        } catch (IOException e) {
            throw new InvalidException("Something went wrong while deleting " + userId + " data");
        }

    }
}
