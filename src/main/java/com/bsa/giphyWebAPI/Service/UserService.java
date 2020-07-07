package com.bsa.giphyWebAPI.Service;

import com.bsa.giphyWebAPI.Exceptions.InvalidRequestException;
import com.bsa.giphyWebAPI.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

    public String searchGif(Optional<Boolean> force, String query, String userId) {
        if (query.trim().length() != 0) {
            return force.isPresent()
                    ? userRepository.searchGifInUserFolder(query, userId)
                    : userRepository.searchGifInInnerCache(query, userId);
        } else {
            throw new InvalidRequestException(query);
        }
    }

    public ResponseEntity<List<Map<String, String>>> getUserHistory(String userId) {
        return userRepository.getUserHistory(userId);
    }

    public ResponseEntity<List<Map<String, Object>>> getAllUserFiles(String userId) {
        return userRepository.getAllUserFiles(userId);
    }

    public void cleanUserHistory(String userId) {
        userRepository.cleanUserHistory(userId);
    }

    public void resetUserCache(String userId, Optional<String> query) {
        if (query.isPresent()) {
            userRepository.resetUserCache(userId, query.get());
        } else {
            userRepository.resetUserCache(userId);
        }
    }

    public void cleanUser(String userId) {
        userRepository.cleanUser(userId);
    }
}
