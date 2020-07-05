package com.bsa.giphyWebAPI.Configuration;

import com.bsa.giphyWebAPI.utils.ImagePuller;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@EnableConfigurationProperties(PullerConfiguration.class)
public class ConfigProperties {

    @Bean
    @Scope("singleton")
    public ImagePuller imagePuller(PullerConfiguration pullerConfiguration) {
        return new ImagePuller(pullerConfiguration.getUrl(), pullerConfiguration.getApiKey());
    }
}
