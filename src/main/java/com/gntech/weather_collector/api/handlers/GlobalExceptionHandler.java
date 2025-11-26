package com.gntech.weather_collector.api.handlers;

import com.gntech.weather_collector.api.exceptions.CityNotFoundException;
import com.gntech.weather_collector.api.exceptions.InvalidApiKeyException;
import com.gntech.weather_collector.api.exceptions.RateLimitExceededException;
import com.gntech.weather_collector.api.exceptions.StandardError;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<StandardError> handleCityNotFound(
            CityNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError();
        err.setTimestamp(OffsetDateTime.now().toString());
        err.setStatus(status.value());
        err.setError("Recurso não encontrado");
        err.setMessage(ex.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidApiKeyException.class)
    public ResponseEntity<StandardError> handleInvalidApiKey(
            InvalidApiKeyException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = new StandardError();
        err.setTimestamp(OffsetDateTime.now().toString());
        err.setStatus(status.value());
        err.setError("Não autorizado");
        err.setMessage(ex.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<StandardError> handleRateLimit(
            RateLimitExceededException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.TOO_MANY_REQUESTS;
        StandardError err = new StandardError();
        err.setTimestamp(OffsetDateTime.now().toString());
        err.setStatus(status.value());
        err.setError("Limite de requisições excedido");
        err.setMessage(ex.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> handleIllegalArgument(
            IllegalArgumentException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError();
        err.setTimestamp(OffsetDateTime.now().toString());
        err.setStatus(status.value());
        err.setError("Requisição inválida");
        err.setMessage(ex.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<StandardError> handleIllegalState(
            IllegalStateException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError err = new StandardError();
        err.setTimestamp(OffsetDateTime.now().toString());
        err.setStatus(status.value());
        err.setError("Erro de configuração");
        err.setMessage(ex.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<StandardError> handleFeignException(
            FeignException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        String message = "Falha ao buscar dados da API externa de clima";

        if (ex.status() == 404) {
            status = HttpStatus.NOT_FOUND;
            message = "Cidade não encontrada na API externa";
        } else if (ex.status() == 401) {
            status = HttpStatus.UNAUTHORIZED;
            message = "Chave de API inválida ou expirada";
        } else if (ex.status() == 429) {
            status = HttpStatus.TOO_MANY_REQUESTS;
            message = "Limite de requisições excedido";
        }

        StandardError err = new StandardError();
        err.setTimestamp(OffsetDateTime.now().toString());
        err.setStatus(status.value());
        err.setError(status.getReasonPhrase());
        err.setMessage(message);
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleGenericException(
            Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError err = new StandardError();
        err.setTimestamp(OffsetDateTime.now().toString());
        err.setStatus(status.value());
        err.setError("Erro interno do servidor");
        err.setMessage("Ocorreu um erro inesperado");
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
