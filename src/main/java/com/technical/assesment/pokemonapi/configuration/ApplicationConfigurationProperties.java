package com.technical.assesment.pokemonapi.configuration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Getter
@Configuration
public class ApplicationConfigurationProperties {

    @Value("${pokemonapi.baseUrl}")
    private String pokemonBaseUrl;

    @Value("${pokemonapi.limit}")
    private String  limit;

    @Value("${pokemonapi.offset}")
    private String offset;
}
