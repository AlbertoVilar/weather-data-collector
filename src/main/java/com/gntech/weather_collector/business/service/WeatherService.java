package com.gntech.weather_collector.business.service;

import com.gntech.weather_collector.api.dto.WeatherResponseDTO;
import com.gntech.weather_collector.infrastructure.client.OpenWeatherClient;
import com.gntech.weather_collector.infrastructure.mapper.WeatherDataConverter;
import com.gntech.weather_collector.infrastructure.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final WeatherRepository repository;
    private final OpenWeatherClient client;
    private final WeatherDataConverter dataConverter;

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.units}")
    private String units;

    @Value("${openweather.api.lang}")
    private String language;

    public WeatherService(WeatherRepository repository, OpenWeatherClient client, WeatherDataConverter dataConverter) {
        this.repository = repository;
        this.client = client;
        this.dataConverter = dataConverter;
    }

    public WeatherResponseDTO getWeatherByCity(String city) {

        var weatherResponse = client.getWeatherByCity(city, apiKey, units, language);
        var weatherData = dataConverter.toWeatherData(weatherResponse);
        weatherData = repository.save(weatherData);
        return dataConverter.weatherResponseDTO(weatherData);
    }
}
