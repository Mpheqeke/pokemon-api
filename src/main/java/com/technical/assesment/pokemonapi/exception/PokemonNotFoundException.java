package com.technical.assesment.pokemonapi.exception;

public class PokemonNotFoundException extends RuntimeException {

    public PokemonNotFoundException(String message) {
        super(message);
    }

}
