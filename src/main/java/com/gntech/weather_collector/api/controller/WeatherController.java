package com.gntech.weather_collector.api.controller;

import com.gntech.weather_collector.api.dto.WeatherResponseDTO;
import com.gntech.weather_collector.business.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
@Tag(name = "Weather API", description = "API para coleta e consulta de dados climáticos")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    @Operation(summary = "Obter clima atual por cidade",
            description = "Busca dados climáticos atuais na API OpenWeather e salva no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clima obtido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada"),
            @ApiResponse(responseCode = "401", description = "Chave de API inválida"),
            @ApiResponse(responseCode = "429", description = "Limite de requisições excedido")
    })
    public ResponseEntity<WeatherResponseDTO> getWeather(
            @Parameter(description = "Nome da cidade", example = "rio de janeiro")
            @PathVariable String city
    ) {
        // Normaliza entrada
        city = city.trim().toLowerCase();

        // Service valida e lança exceções
        var weatherResponse = weatherService.getWeatherByCity(city);

        return ResponseEntity.ok(weatherResponse);
    }

    @GetMapping("/history")
    @Operation(summary = "Obter histórico de clima por cidade",
            description = "Retorna o histórico de consultas climáticas de uma cidade (busca apenas no banco local)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico obtido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetro city inválido")
    })
    public ResponseEntity<List<WeatherResponseDTO>> getHistoryByCity(
            @Parameter(description = "Nome da cidade", example = "rio de janeiro")
            @RequestParam String city
    ) {
        // Normaliza entrada
        city = city.trim().toLowerCase();

        // Service valida e lança exceções
        List<WeatherResponseDTO> history = weatherService.getHistoryByCity(city);

        return ResponseEntity.ok(history);
    }
}