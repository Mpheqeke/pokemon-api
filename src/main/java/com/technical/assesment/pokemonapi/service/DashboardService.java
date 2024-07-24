package com.technical.assesment.pokemonapi.service;

import com.technical.assesment.pokemonapi.dto.PokemonDetailsDto;
import com.technical.assesment.pokemonapi.dto.PokemonListDto;

import java.util.List;

public interface DashboardService {

    List<PokemonListDto> getPokemonList();

    List<PokemonDetailsDto> getPokemonDetails();
}
