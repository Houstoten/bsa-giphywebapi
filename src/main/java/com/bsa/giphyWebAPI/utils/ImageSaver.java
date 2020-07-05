package com.bsa.giphyWebAPI.utils;

import com.bsa.giphyWebAPI.Entity.GifImage;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class ImageSaver {
    public static boolean saveImage(GifImage gifImage) throws IOException, UnirestException {
        File file = new File("./cache/" + gifImage.getQuery() + "/" + gifImage.getName() + ".gif");
        if (file.exists()) {
            return false;
        }
        Unirest.setTimeouts(0, 0);
        FileUtils.copyInputStreamToFile(
                Unirest
                        .get(gifImage.getUrl().toString())
                        .asBinary()
                        .getBody()
                , file);
        return true;
    }
}
