package com.technical.assesment.pokemonapi.service.impl;

import com.technical.assesment.pokemonapi.configuration.ApplicationConfigurationProperties;
import com.technical.assesment.pokemonapi.dto.PokemonDetailsDto;
import com.technical.assesment.pokemonapi.dto.PokemonListDto;
import com.technical.assesment.pokemonapi.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private ApplicationConfigurationProperties properties;

    @Autowired
    public DashboardServiceImpl(ApplicationConfigurationProperties properties) {
        this.properties = properties;
    }


    @Override
    public List<PokemonListDto> getPokemonList() {
        return null;
    }

    @Override
    public List<PokemonDetailsDto> getPokemonDetails() {
        return null;
    }
}
