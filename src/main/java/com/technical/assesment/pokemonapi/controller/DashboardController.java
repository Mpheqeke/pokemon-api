package com.technical.assesment.pokemonapi.controller;

import com.technical.assesment.pokemonapi.dto.ErrorDetailsDto;
import com.technical.assesment.pokemonapi.dto.PokemonDetailsDto;
import com.technical.assesment.pokemonapi.dto.PokemonListDto;
import com.technical.assesment.pokemonapi.exception.PokemonNotFoundException;
import com.technical.assesment.pokemonapi.service.impl.DashboardServiceImpl;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/dashboard")
@RestController
public class DashboardController {

    @Autowired
    private DashboardServiceImpl dashboardService;

    @GetMapping("/landing")
    public ResponseEntity<?> getPokemonList() {
        List<PokemonListDto> pokemonList = dashboardService.getPokemonList();

        if (pokemonList.isEmpty()){
            ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto( "Error occurred ","Pokemon api returned nothing",LocalDateTime.now());
            return new ResponseEntity<>(errorDetailsDto, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pokemonList, HttpStatus.OK);
    }

    @GetMapping("details/{identifier}")
    public ResponseEntity<?> getPokemonDetails(@NonNull @PathVariable String identifier){
        try {
            PokemonDetailsDto pokemonDetailsDto = dashboardService.getPokemonDetails(identifier);
            return new ResponseEntity<>(pokemonDetailsDto, HttpStatus.OK);
        } catch (PokemonNotFoundException exception) {
            ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto( exception.getMessage() ,"Pokemon " + identifier + "not found" ,LocalDateTime.now());
            return new ResponseEntity<>(errorDetailsDto, HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetailsDto> handleAllExceptions(Exception ex, WebRequest request){
        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto(ex.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(errorDetailsDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
