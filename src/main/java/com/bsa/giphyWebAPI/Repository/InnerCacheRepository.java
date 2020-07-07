package com.bsa.giphyWebAPI.Repository;

import com.bsa.giphyWebAPI.Exceptions.InvalidUserException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InnerCacheRepository {
    public Map<String, Map<String, Map<String, String>>> innerCacheData = new HashMap<>();

    public void addPath(String userId, String query, String fileName, String path) {
        if (innerCacheData.containsKey(userId)) {
            if (innerCacheData.get(userId).containsKey(query)) {
                innerCacheData.get(userId).get(query).putIfAbsent(fileName, path);
            } else {
                innerCacheData.get(userId).put(query, new HashMap<>(Map.of(fileName, path)));
            }
        } else {
            innerCacheData.put(userId, new HashMap<>(Map.of(query, new HashMap<>(Map.of(fileName, path)))));
        }
    }

    public Optional<String> searchPath(String userId, String query) {
        if (innerCacheData.containsKey(userId) && innerCacheData.get(userId).containsKey(query)) {
            var keySetArray = innerCacheData.get(userId)
                    .get(query)
                    .keySet().toArray();
            return Optional.of(innerCacheData
                    .get(userId)
                    .get(query)
                    .get(keySetArray[new Random().nextInt(keySetArray.length)]));
        } else {
            return Optional.empty();
        }
    }

    public void deletePath(String userId, Optional<String> query, Optional<String> fileName) {
        if (innerCacheData.containsKey(userId)) {
            if (query.isPresent()) {

                if (innerCacheData.get(userId).containsKey(query.get())
                        && fileName.isPresent()
                        && innerCacheData.get(userId).get(query.get()).containsKey(fileName.get())) {

                    innerCacheData.get(userId).get(query.get()).remove(fileName.get());
                    innerCacheData.get(userId).get(query.get()).remove(fileName.get());
                    if (innerCacheData.get(userId).get(query.get()).isEmpty()) {
                        innerCacheData.get(userId).remove(query.get());
                    }

                } else {
                    innerCacheData.get(userId).remove(query.get());
                }
            } else {
                innerCacheData.remove(userId);
            }
        }else {
            throw new InvalidUserException(userId);
        }
    }
}
