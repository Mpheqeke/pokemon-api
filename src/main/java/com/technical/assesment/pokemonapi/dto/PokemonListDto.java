package com.technical.assesment.pokemonapi.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PokemonListDto {

    private int Id;
    private String name;
    private String sprite;
}
