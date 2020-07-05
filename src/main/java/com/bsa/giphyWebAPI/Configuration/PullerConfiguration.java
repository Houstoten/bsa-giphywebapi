package com.bsa.giphyWebAPI.Configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api")
@Setter
@Getter
public class PullerConfiguration {
    private String url;
    private String apiKey;
}
