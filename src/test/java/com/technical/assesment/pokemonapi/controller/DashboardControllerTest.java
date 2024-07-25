package com.technical.assesment.pokemonapi.controller;

import com.technical.assesment.pokemonapi.dto.PokemonDetailsDto;
import com.technical.assesment.pokemonapi.dto.PokemonListDto;
import com.technical.assesment.pokemonapi.exception.PokemonNotFoundException;
import com.technical.assesment.pokemonapi.service.impl.DashboardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DashboardControllerTest {

    @MockBean
    private DashboardServiceImpl dashboardService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetPokemonList() throws Exception {
        List<PokemonListDto> pokemonList = new ArrayList<>();
        PokemonListDto pokemon = new PokemonListDto();
        pokemon.setName("pikachu");
        pokemon.setSprite("https://example.com/sprite.png");
        pokemon.setId(25);
        pokemonList.add(pokemon);

        when(dashboardService.getPokemonList()).thenReturn(pokemonList);

        mockMvc.perform(get("/dashboard/landing")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("pikachu")))
                .andExpect(jsonPath("$[0].sprite", is("https://example.com/sprite.png")))
                .andExpect(jsonPath("$[0].id", is(25)));
    }

    @Test
    public void testGetPokemonList_NoContent() throws Exception {
        when(dashboardService.getPokemonList()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/dashboard/landing")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.message", is("Error occurred ")))
                .andExpect(jsonPath("$.details", is("Pokemon api returned nothing")));
    }

    @Test
    public void testGetPokemonDetails() throws Exception {
        PokemonDetailsDto pokemonDetails = new PokemonDetailsDto();
        pokemonDetails.setId(25);
        pokemonDetails.setName("pikachu");
        pokemonDetails.setHeight(4);
        pokemonDetails.setWeight(60);
        pokemonDetails.setBaseExperience(112);
        pokemonDetails.setMove("thunder-shock");
        pokemonDetails.setSpecies("pikachu");
        pokemonDetails.setSprite("https://example.com/sprite.png");

        when(dashboardService.getPokemonDetails(anyString())).thenReturn(pokemonDetails);

        mockMvc.perform(get("/dashboard/details/{identifier}", "pikachu")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(25)))
                .andExpect(jsonPath("$.name", is("pikachu")))
                .andExpect(jsonPath("$.height", is(4)))
                .andExpect(jsonPath("$.weight", is(60)))
                .andExpect(jsonPath("$.baseExperience", is(112)))
                .andExpect(jsonPath("$.move", is("thunder-shock")))
                .andExpect(jsonPath("$.species", is("pikachu")))
                .andExpect(jsonPath("$.sprite", is("https://example.com/sprite.png")));
    }

    @Test
    public void testGetPokemonDetails_NotFound() throws Exception {
        when(dashboardService.getPokemonDetails(anyString())).thenThrow(new PokemonNotFoundException("Pokemon not found with name: lilo cause404 NOT_FOUND"));

        mockMvc.perform(get("/dashboard/details/{identifier}", "lilo ")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Pokemon not found with name: lilo cause404 NOT_FOUND")))
                .andExpect(jsonPath("$.details", is("Pokemon lilo not found")));
    }

}