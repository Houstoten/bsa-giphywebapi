package com.bsa.giphyWebAPI.Controller;

import com.bsa.giphyWebAPI.Service.GifService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gifs")
@Slf4j
public class GifController {

    @Autowired
    public GifService gifService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllGifUngrouped() {
        log.info("Request to get all cached gifs");
        return gifService.getAllGifsUngrouped();
    }

}
