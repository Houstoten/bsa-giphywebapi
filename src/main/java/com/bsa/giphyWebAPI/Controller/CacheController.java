package com.bsa.giphyWebAPI.Controller;

import com.bsa.giphyWebAPI.Service.CacheService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private VariableValidator variableValidator;

    @Autowired
    private CacheService cacheService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Map<String, Object>>> getAllCache(@RequestParam(required = false) String query) {
        variableValidator.validate(Optional.ofNullable(query).orElse(""));
        log.info("Request to get all cache by : " + Optional.ofNullable(query).orElse(""));
        return cacheService.getAllCache(Optional.ofNullable(query));
    }

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Map<String, Object>>> generateCache(@RequestParam String query) {
        variableValidator.validate(query);
        log.info("Request to generate cache by : " + query);
        return cacheService.generateCache(query);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCache() {
        log.info("Request to clear cache");
        cacheService.clearCache();
    }
}
