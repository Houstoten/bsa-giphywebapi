package com.bsa.giphyWebAPI.Controller;

import com.bsa.giphyWebAPI.Service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @GetMapping("/cache")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Map<String, Object>>> getAllCache(@RequestParam(required = false) String query){
        return cacheService.getAllCache(query);
    }
}
