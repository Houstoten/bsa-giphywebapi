package com.bsa.giphyWebAPI.Service;

import com.bsa.giphyWebAPI.Exceptions.InvalidRequestException;
import com.bsa.giphyWebAPI.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String generateGif(Optional<Boolean> force, String query, String userId) {
        if (query.trim().length() != 0) {
            return force.isPresent()
                    ? userRepository.getGifFromExternal(query, userId, 0)
                    : userRepository.getGifFromCache(query, userId);
        } else {
            throw new InvalidRequestException(query);
        }
    }
}
