package com.gntech.weather_collector.api.handlers;

import com.gntech.weather_collector.api.controller.WeatherController;
import com.gntech.weather_collector.api.exceptions.BadRequestException;
import com.gntech.weather_collector.api.exceptions.CityNotFoundException;
import com.gntech.weather_collector.api.exceptions.InvalidApiKeyException;
import com.gntech.weather_collector.api.exceptions.RateLimitExceededException;
import com.gntech.weather_collector.api.exceptions.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice(assignableTypes = WeatherController.class)
public class GlobalExceptionHandler {

    private String statusDescricaoPt(HttpStatus status) {
        return switch (status) {
            case BAD_REQUEST -> "Requisição inválida";
            case UNAUTHORIZED -> "Não autorizado";
            case NOT_FOUND -> "Recurso não encontrado";
            case TOO_MANY_REQUESTS -> "Muitas solicitações";
            case INTERNAL_SERVER_ERROR -> "Erro interno do servidor";
            case SERVICE_UNAVAILABLE -> "Serviço indisponível";
            default -> status.getReasonPhrase();
        };
    }

    private StandardError buildError(HttpStatus status, String message, HttpServletRequest req) {
        StandardError err = new StandardError();
        err.setTimestamp(OffsetDateTime.now().toString());
        err.setStatus(status.value());
        err.setError(statusDescricaoPt(status));
        err.setMessage(message);
        err.setPath(req.getRequestURI());
        return err;
    }

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<StandardError> handleCityNotFound(
            CityNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request));
    }

    @ExceptionHandler(InvalidApiKeyException.class)
    public ResponseEntity<StandardError> handleInvalidApiKey(
            InvalidApiKeyException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(buildError(HttpStatus.UNAUTHORIZED, ex.getMessage(), request));
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<StandardError> handleRateLimit(
            RateLimitExceededException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(buildError(HttpStatus.TOO_MANY_REQUESTS, ex.getMessage(), request));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> handleBadRequest(
            BadRequestException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request));
    }
}
