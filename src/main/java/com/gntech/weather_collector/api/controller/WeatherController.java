package com.gntech.weather_collector.api.controller;

import com.gntech.weather_collector.api.dto.WeatherResponseDTO;
import com.gntech.weather_collector.business.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    public ResponseEntity<WeatherResponseDTO> getWeather(@PathVariable String city) {
        // Normaliza entrada
        city = city.trim().toLowerCase();

        // Service valida e lança exceções
        var weatherResponse = weatherService.getWeatherByCity(city);

        return ResponseEntity.ok(weatherResponse);
    }

    @GetMapping("/history")
    public ResponseEntity<List<WeatherResponseDTO>> getHistoryByCity(@RequestParam String city) {
        // Normaliza entrada
        city = city.trim().toLowerCase();

        // Service valida e lança exceções
        List<WeatherResponseDTO> history = weatherService.getHistoryByCity(city);

        return ResponseEntity.ok(history);
    }
}