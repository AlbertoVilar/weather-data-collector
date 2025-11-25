package com.gntech.weather_collector.infrastructure.mapper;

import com.gntech.weather_collector.api.dto.WeatherResponseDTO;
import com.gntech.weather_collector.infrastructure.client.response.OpenWeatherResponse;
import com.gntech.weather_collector.infrastructure.entity.WeatherData;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class WeatherDataConverter {

    public WeatherData toWeatherData(OpenWeatherResponse response) {
        return WeatherData.builder()
                .city(response.getName())
                .country(response.getSys().getCountry())
                .temperature(response.getMain().getTemp())
                .feelsLike(response.getMain().getFeelsLike())
                .humidity(response.getMain().getHumidity())
                .description(response.getWeather().get(0).getDescription())
                .windSpeed(response.getWind().getSpeed())

                .build();
    }

    public WeatherResponseDTO weatherResponseDTO(WeatherData weatherData) {

        return WeatherResponseDTO.builder()
                .id(weatherData.getId())
                .city(weatherData.getCity())
                .country(weatherData.getCountry())
                .temperature(weatherData.getTemperature())
                .feelsLike(weatherData.getFeelsLike())
                .humidity(weatherData.getHumidity())
                .description(weatherData.getDescription())
                .windSpeed(weatherData.getWindSpeed())
                .collectedAt(weatherData.getCollectedAt())
                .createdAt(weatherData.getCreatedAt())

                .build();

    }
}
