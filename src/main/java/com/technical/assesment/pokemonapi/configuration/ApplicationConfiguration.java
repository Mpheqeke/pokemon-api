package com.technical.assesment.pokemonapi.configuration;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class ApplicationConfiguration {

    private ApplicationConfigurationProperties properties;

    @Autowired
    public ApplicationConfiguration( ApplicationConfigurationProperties properties ) {
        this.properties = properties;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metericCommonTags() {
        return registry -> registry.config().commonTags("Application", "pokemonapi");
    }

}
