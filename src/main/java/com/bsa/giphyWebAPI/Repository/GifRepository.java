package com.bsa.giphyWebAPI.Repository;

import com.bsa.giphyWebAPI.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class GifRepository {
    @Autowired
    private BaseRepository baseRepository;

    public List<String> getAllGifsUngrouped() {
        try (Stream<Path> paths = Files.walk(Paths.get(baseRepository.getCacheDirectory()))) {
            List<String> output = paths
                    .filter(Files::isRegularFile)
                    .map(Path::toAbsolutePath)
                    .map(Path::normalize)
                    .map(Path::toString)
                    .collect(Collectors.toList());
            return output;
        } catch (IOException e) {
            throw new NotFoundException("Cache folder is clear");
        }
    }
}
