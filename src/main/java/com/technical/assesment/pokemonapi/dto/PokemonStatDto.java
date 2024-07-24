package com.technical.assesment.pokemonapi.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PokemonStatDto {
    private String name;
    private int level;

    public PokemonStatDto(String name, int level) {
        this.name = name;
        this.level = level;
    }
}
