package com.bsa.giphyWebAPI.Service;

import com.bsa.giphyWebAPI.Repository.CacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CacheService {

    @Autowired
    private CacheRepository cacheRepository;

    public ResponseEntity<List<Map<String, Object>>> getAllCache(Optional<String> query) {
        return query.isPresent()? cacheRepository.getAllCache(query.get()): cacheRepository.getAllCache();
    }

    public ResponseEntity<List<Map<String, Object>>> generateCache(String query) {
        cacheRepository.generateCache(query, 0);
        return cacheRepository.getAllCache();
    }

    public void clearCache() {
        cacheRepository.clearCache();
    }
}
