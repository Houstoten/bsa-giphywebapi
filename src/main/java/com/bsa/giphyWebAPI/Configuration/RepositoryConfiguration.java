package com.bsa.giphyWebAPI.Configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "data")
@Setter
@Getter
public class RepositoryConfiguration {
    private String cacheDirectory;
    private String usersDirectory;
    private String server;
}
