package com.gntech.weather_collector.api.exceptions;

public class InvalidApiKeyException extends RuntimeException {
    public InvalidApiKeyException() {
        super("Chave de API inválida ou expirada");
    }

    public InvalidApiKeyException(String message) {
        super(message);
    }
}