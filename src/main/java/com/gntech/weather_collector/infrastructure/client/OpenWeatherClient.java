package com.gntech.weather_collector.infrastructure.client;

import com.gntech.weather_collector.infrastructure.client.response.OpenWeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openweather", url = "${openweather.api.url}")
public interface OpenWeatherClient {

    @GetMapping
    OpenWeatherResponse getWeatherByCity(
            @RequestParam("q") String city,
            @RequestParam("appid") String apiKey,
            @RequestParam("units") String units,
            @RequestParam("lang") String language);
}

