package com.technical.assesment.pokemonapi.service.impl;

import com.technical.assesment.pokemonapi.configuration.ApplicationConfigurationProperties;
import com.technical.assesment.pokemonapi.dto.PokemonDetailsDto;
import com.technical.assesment.pokemonapi.dto.PokemonListDto;
import com.technical.assesment.pokemonapi.exception.PokemonNotFoundException;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@SpringBootTest
class DashboardServiceImplTest {

    @Mock
    private ApplicationConfigurationProperties properties;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    @BeforeEach
    public void setUp() {
        when(properties.getPokemonBaseUrl()).thenReturn(Instancio.create(String.class));
        when(properties.getOffset()).thenReturn(Instancio.create(String.class));
        when(properties.getLimit()).thenReturn(Instancio.create(String.class));
    }

    @Test
    public void testGetPokemonList() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> results = new ArrayList<>();
        Map<String, String> pokemon = new HashMap<>();
        pokemon.put("name", "pikachu");
        pokemon.put("url", "https://pokeapi.co/api/v2/pokemon/25/");
        results.add(pokemon);
        response.put("results", results);

        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(response);

        Map<String, Object> pokemonDetails = new HashMap<>();
        pokemonDetails.put("id", 25);
        Map<String, String> sprites = new HashMap<>();
        sprites.put("front_default", "https://example.com/sprite.png");
        pokemonDetails.put("sprites", sprites);

        when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/25/", Map.class)).thenReturn(pokemonDetails);

        List<PokemonListDto> pokemonList = dashboardService.getPokemonList();
        assertNotNull(pokemonList);
        assertFalse(pokemonList.isEmpty());
        assertEquals("pikachu", pokemonList.get(0).getName());
        assertEquals(25, pokemonList.get(0).getId());
        assertEquals("https://example.com/sprite.png", pokemonList.get(0).getSprite());
    }

    @Test
    public void testGetPokemonDetails() {
        Map<String, Object> response = new HashMap<>();
        response.put("id", 25);
        response.put("name", "pikachu");
        response.put("height", 4);
        response.put("weight", 60);
        response.put("base_experience", 112);

        List<Map<String, Object>> moves = new ArrayList<>();
        Map<String, Object> move = new HashMap<>();
        Map<String, String> moveDetails = new HashMap<>();
        moveDetails.put("name", "thunder-shock");
        move.put("move", moveDetails);
        moves.add(move);
        response.put("moves", moves);

        List<Map<String, Object>> stats = new ArrayList<>();
        Map<String, Object> stat = new HashMap<>();
        Map<String, String> statInfo = new HashMap<>();
        statInfo.put("name", "speed");
        stat.put("stat", statInfo);
        stat.put("base_stat", 90);
        stats.add(stat);
        response.put("stats", stats);

        Map<String, String> species = new HashMap<>();
        species.put("name", "pikachu");
        response.put("species", species);

        Map<String, String> sprites = new HashMap<>();
        sprites.put("front_default", "https://example.com/sprite.png");
        response.put("sprites", sprites);

        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(response);

        PokemonDetailsDto pokemonDetails = dashboardService.getPokemonDetails("pikachu");
        assertNotNull(pokemonDetails);
        assertEquals(25, pokemonDetails.getId());
        assertEquals("pikachu", pokemonDetails.getName());
        assertEquals(4, pokemonDetails.getHeight());
        assertEquals(60, pokemonDetails.getWeight());
        assertEquals(112, pokemonDetails.getBaseExperience());
        assertEquals("thunder-shock", pokemonDetails.getMove());
        assertEquals("speed", pokemonDetails.getStats().get(0).getName());
        assertEquals(90, pokemonDetails.getStats().get(0).getLevel());
        assertEquals("pikachu", pokemonDetails.getSpecie());
        assertEquals("https://example.com/sprite.png", pokemonDetails.getSprite());
    }

    @Test
    public void testGetPokemonDetails_PokemonNotFound() {
        when(restTemplate.getForObject(anyString(), eq(Map.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        PokemonNotFoundException exception = assertThrows(PokemonNotFoundException.class, () -> {
            dashboardService.getPokemonDetails("invalid");
        });

        assertEquals("Pokemon not found with name: invalid cause404 NOT_FOUND", exception.getMessage());
    }

    @Test
    public void testGetPokemonList_RestClientException() {
        when(restTemplate.getForObject(anyString(), eq(Map.class)))
                .thenThrow(new RestClientException("Service unavailable"));

        PokemonNotFoundException exception = assertThrows(PokemonNotFoundException.class, () -> {
            dashboardService.getPokemonList();
        });

        assertEquals("Error fetching Pokemon list: Service unavailable", exception.getMessage());
    }
}