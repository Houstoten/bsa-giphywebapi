package com.bsa.giphyWebAPI.Controller;

import com.bsa.giphyWebAPI.Service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Map<String, Object>>> getAllCache(@RequestParam(required = false) String query){
        return cacheService.getAllCache(Optional.ofNullable(query));
    }

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Map<String, Object>>> generateCache(@RequestParam String query){
        return cacheService.generateCache(query);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCache(){
        cacheService.clearCache();
    }
}
