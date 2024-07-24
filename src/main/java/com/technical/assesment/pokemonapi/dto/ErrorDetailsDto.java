package com.technical.assesment.pokemonapi.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ErrorDetailsDto {

    public String message;
    public String details;
    public LocalDateTime timestamp;

    public ErrorDetailsDto(String message, String details, LocalDateTime timestamp) {
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
    }
}
