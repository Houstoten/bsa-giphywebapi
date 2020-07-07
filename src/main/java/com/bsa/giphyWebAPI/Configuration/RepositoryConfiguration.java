package com.bsa.giphyWebAPI.Configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "data")
@Setter
@Getter
public class RepositoryConfiguration {
    private String cacheDirectory;
    private String usersDirectory;
}
