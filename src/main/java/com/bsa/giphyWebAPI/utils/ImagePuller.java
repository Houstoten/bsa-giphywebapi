package com.bsa.giphyWebAPI.utils;

import com.bsa.giphyWebAPI.DTO.ImageReceiveDto;
import com.bsa.giphyWebAPI.Exceptions.GiphyRequestFailed;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class ImagePuller {
    private final String url;
    private final String apiKey;

    public ImagePuller(String url, String apiKey) {
        this.url = url;
        this.apiKey = apiKey;
    }

    @Autowired
    private ObjectMapper mapper;

    public Optional<ImageReceiveDto> getImageReceiveDto(final String query) {
        try {
            JsonNode root = mapper
                    .readTree(new URL(url + "?api_key=" + apiKey + "&tag=" + query))
                    .path("data");
            ImageReceiveDto imageReceiveDto = new ImageReceiveDto();
            imageReceiveDto.setId(root.path("id").asText());
            imageReceiveDto.setUrl(new URL(root.path("images").path("downsized_large").path("url").asText()));
            return Optional.of(imageReceiveDto);
        } catch (IOException e) {
            throw new GiphyRequestFailed(query);
        }
    }
}
