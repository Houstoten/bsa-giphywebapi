package com.bsa.giphyWebAPI.utils;

import com.bsa.giphyWebAPI.DTO.ImageReceiveDto;
import com.bsa.giphyWebAPI.Repository.BaseRepository;
import com.bsa.giphyWebAPI.Repository.InnerCacheRepository;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.Optional;

@Component
public class ImageSaver {

    @Autowired
    private CsvReaderWriter csvReaderWriter;

    @Autowired
    private InnerCacheRepository innerCacheRepository;

    @Autowired
    private BaseRepository baseRepository;

    public Optional<File> saveImage(ImageReceiveDto imageReceiveDto, String query, String userId)
            throws IOException, UnirestException {
        File file = new File(baseRepository.getCacheDirectory() + "/" + query + "/" + imageReceiveDto.getId() + ".gif");
        if (file.exists()) {
            return Optional.empty();
        }
        Unirest.setTimeouts(0, 0);
        FileUtils.copyInputStreamToFile(
                Unirest.get(imageReceiveDto.getUrl().toString())
                        .asBinary()
                        .getBody()
                , file);
        copyImageToUserFolder(file, userId, query);
        return Optional.of(file);
    }

    public Optional<File> copyImageToUserFolder(File image, String userId, String query) throws IOException {
        File newFile = new File(baseRepository.getUsersDirectory() + "/" + userId + "/" + query + "/" + image.getName());
        if (newFile.exists()) {
            return Optional.empty();
        }
        FileUtils.copyFile(image, newFile);
        innerCacheRepository.addPath(userId, query, newFile.getName(), newFile.getPath());
        System.out.println(innerCacheRepository.innerCacheData.toString());
        csvReaderWriter.writeCsv(new Date(), userId, query, newFile.getPath());
        return Optional.of(newFile);
    }

    public Optional<File> copyImageToUserFolder(Path path, String userId) throws IOException {
        File oldFile = path.toFile();
        File newFile = new File(baseRepository.getUsersDirectory() + "/" + userId + "/"
                + path.subpath(path.getNameCount() - 2, path.getNameCount()));

        if (newFile.exists()) {
            return Optional.empty();
        }
        FileUtils.copyFile(oldFile, newFile);
        innerCacheRepository.addPath(userId
                , path.subpath(path.getNameCount() - 2, path.getNameCount() - 1).toString()
                , newFile.getName(), newFile.getPath());
        System.out.println(innerCacheRepository.innerCacheData.toString());
        csvReaderWriter.writeCsv(new Date()
                , userId
                , path.subpath(path.getNameCount() - 2, path.getNameCount() - 1).toString()
                , newFile.getPath());
        return Optional.of(newFile);
    }
}
