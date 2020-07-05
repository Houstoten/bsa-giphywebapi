package com.bsa.giphyWebAPI.Controller;

import com.bsa.giphyWebAPI.Entity.FolderEntity;
import com.bsa.giphyWebAPI.Service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @GetMapping("/cache")
    public FolderEntity getAllCache(@RequestParam(required = false) String query){
        return cacheService.getAllCache(query);
    }
}
