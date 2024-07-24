package com.technical.assesment.pokemonapi.service.impl;

import com.technical.assesment.pokemonapi.configuration.ApplicationConfigurationProperties;
import com.technical.assesment.pokemonapi.dto.PokemonDetailsDto;
import com.technical.assesment.pokemonapi.dto.PokemonListDto;
import com.technical.assesment.pokemonapi.dto.PokemonStatDto;
import com.technical.assesment.pokemonapi.exception.PokemonNotFoundException;
import com.technical.assesment.pokemonapi.service.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class DashboardServiceImpl implements DashboardService {

    private ApplicationConfigurationProperties properties;

    private RestTemplate restTemplate;

    @Autowired
    public DashboardServiceImpl(ApplicationConfigurationProperties properties, RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }


    @Override
    public List<PokemonListDto> getPokemonList() {
        try {
            String url = properties.getPokemonBaseUrl() + "?" + properties.getOffset() + "&" + properties.getLimit();
            Map response = restTemplate.getForObject(url, Map.class);
            List<Map<String, String>> results = (List<Map<String,String>>) response.get("results");

            List<PokemonListDto> pokemonListDto = new ArrayList<>();
            for(Map<String,String> result : results) {
                PokemonListDto pokemon = new PokemonListDto();
                pokemon.setName(result.get("name"));
                String pokemonDetaisUrl = result.get("url");
                Map pokemonDetails = restTemplate.getForObject(pokemonDetaisUrl, Map.class);
                Map sprites = (Map) pokemonDetails.get("sprites");
                pokemon.setSprite((String) sprites.get("front_default"));
                pokemon.setId(Integer.parseInt(pokemonDetails.get("id").toString()));
                pokemonListDto.add(pokemon);
            }
            return pokemonListDto;
        } catch ( RestClientException exception) {
            throw new PokemonNotFoundException("Error fetching Pokemon list: " + exception.getMessage());
        }
    }

    @Override
    public PokemonDetailsDto getPokemonDetails(String identifier) {
        try {
            String url = properties.getPokemonBaseUrl() + "/" + identifier;
            Map response = restTemplate.getForObject(url, Map.class);
             PokemonDetailsDto pokemonDetailsDto = new PokemonDetailsDto();

             pokemonDetailsDto.setId((Integer) response.get("id"));
             pokemonDetailsDto.setHeight((Integer) response.get("height"));
             pokemonDetailsDto.setWeight((Integer) response.get("weight"));
             pokemonDetailsDto.setBaseExperience((Integer) response.get("base_experience"));

             pokemonDetailsDto.setName((String) response.get("name"));

             List<Map<String, Object>> moves = (List<Map<String, Object>>) response.get("moves");

             if (!moves.isEmpty()){
                 Map<String, Object> primaryMoveInfo = (Map<String, Object>) moves.get(0);
                 Map<String, String> moveDetails = (Map<String, String>) primaryMoveInfo.get("move");
                 pokemonDetailsDto.setMove((String) moveDetails.get("name"));
             }


             List<Map<String,Object>> pokiStat =((List<Map<String, Object>>) response.get("stats"));
             List<PokemonStatDto> pokemonStatDtoList = new ArrayList<>();
             for (Map<String, Object> stat : pokiStat){
                 Map<String, String> statInfo = (Map<String, String>) stat.get("stat");
                 String name = (String) statInfo.get("name");
                 int level = (Integer) stat.get("base_stat");
                 pokemonStatDtoList.add(new PokemonStatDto(name, level));
             }
             pokemonDetailsDto.setStats(pokemonStatDtoList);

             Map pokemonSpecieData = (Map) response.get("species");
             pokemonDetailsDto.setSpecie((String) pokemonSpecieData.get("name"));

             Map sprites = (Map) response.get("sprites");
             pokemonDetailsDto.setSprite((String) sprites.get("front_default"));

             return pokemonDetailsDto;

        } catch (HttpClientErrorException exception) {
            throw new PokemonNotFoundException("Pokemon not found with name: " + identifier + " cause " + exception.getMessage());
        }
    }
}
