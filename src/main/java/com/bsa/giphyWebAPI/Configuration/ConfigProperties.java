package com.bsa.giphyWebAPI.Configuration;

import com.bsa.giphyWebAPI.Repository.BaseRepository;
import com.bsa.giphyWebAPI.utils.ImagePuller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.nio.file.Path;

@Configuration
@EnableConfigurationProperties({PullerConfiguration.class, RepositoryConfiguration.class})
public class ConfigProperties implements WebMvcConfigurer {

    @Autowired
    private Environment environment;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/localDB/**")
                .addResourceLocations("file:///" + Path.of(environment.getProperty("data.cache-directory"))
                        .subpath(0, Path.of(environment.getProperty("data.cache-directory")).getNameCount() - 1)
                        .toAbsolutePath());
    }


    @Bean
    @Scope("singleton")
    public ImagePuller imagePuller(PullerConfiguration pullerConfiguration) {
        return new ImagePuller(pullerConfiguration.getUrl(), pullerConfiguration.getApiKey());
    }

    @Bean
    @Scope("singleton")
    public BaseRepository baseRepository(RepositoryConfiguration repositoryConfiguration) throws UnknownHostException {
        return new BaseRepository(repositoryConfiguration.getCacheDirectory()
                , repositoryConfiguration.getUsersDirectory()
                , InetAddress.getLocalHost().getHostAddress() + ":" + environment.getProperty("server.port") + "\\");
    }

}
