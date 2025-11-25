package com.gntech.weather_collector.api.controller;

import com.gntech.weather_collector.api.dto.WeatherResponseDTO;
import com.gntech.weather_collector.business.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    public ResponseEntity<WeatherResponseDTO> getWeather(@PathVariable String city) {

        if (city == null || city.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        city = city.trim().toLowerCase();

        var weatherResponse = weatherService.getWeatherByCity(city);

        return ResponseEntity.ok(weatherResponse);
    }

}

