package com.technical.assesment.pokemonapi.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@RequiredArgsConstructor
public class PokemonDetailsDto {

    private int Id;
    private int height;
    private int weight;
    private int baseExperience;

    private String name;
    private String sprite;
    private String species;
    private String move;
    private List<PokemonStatDto> stats;

}
