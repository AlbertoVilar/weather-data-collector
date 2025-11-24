package com.gntech.weather_collector.api.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record WeatherResponseDTO(
        Long id,
        String city,
        String country,
        BigDecimal temperature,
        BigDecimal feelsLike,
        Integer humidity,
        String description,
        BigDecimal windSpeed,
        LocalDateTime collectedAt,
        LocalDateTime createdAt
) {
}
