package com.bsa.giphyWebAPI.Service;

import com.bsa.giphyWebAPI.Repository.GifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GifService {

    @Autowired
    private GifRepository gifRepository;

    public List<String> getAllGifsUngrouped() {
        return gifRepository.getAllGifsUngrouped();
    }
}
