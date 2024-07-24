package com.technical.assesment.pokemonapi.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@RequiredArgsConstructor
public class PokemonDetailsDto {

    private int Id;
    private String name;
    private int height;
    private int weight;
    private String sprite;
    private ArrayList<String> skills;
}
