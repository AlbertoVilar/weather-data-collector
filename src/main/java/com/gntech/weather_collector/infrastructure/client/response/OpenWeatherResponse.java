package com.gntech.weather_collector.infrastructure.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OpenWeatherResponse {

    private String name;
    private Main main;
    private List<Weather> weather;
    private Wind wind;
    private Sys sys;

    @Data
    public static class Main {
        private BigDecimal temp;
        @JsonProperty("feels_like")
        private BigDecimal feelsLike;
        private Integer humidity;
    }

    @Data
    public static class Weather {
        private String description;
    }

    @Data
    public static class Wind {
        private BigDecimal speed;
    }

    @Data
    public static class Sys {
        private String country;
    }
}