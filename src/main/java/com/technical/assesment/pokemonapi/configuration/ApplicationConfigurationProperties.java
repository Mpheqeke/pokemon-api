package com.technical.assesment.pokemonapi.configuration;

import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Slf4j
@Getter
@ConfigurationProperties(prefix = "yaml")
public class ApplicationConfigurationProperties {
//TODO
//    @Value("${pokemonapi.baseUrl}")
//    private String pokemonBaseUrl;


}
