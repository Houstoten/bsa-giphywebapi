package com.bsa.giphyWebAPI.Service;

import com.bsa.giphyWebAPI.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String generateGif(Optional<Boolean> force, String query, String userId) {
        return force.isPresent()
                ? userRepository.getGifFromExternal(query, userId)
                : userRepository.getGifFromCache(query, userId);
    }
}
