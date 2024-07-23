package com.technical.assesment.pokemonapi;

import com.technical.assesment.pokemonapi.configuration.ApplicationConfigurationProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PokemonapiApplicationTests {

	@Autowired
	private ApplicationConfigurationProperties properties;

	@Test
	void contextLoads() {
	}

}
