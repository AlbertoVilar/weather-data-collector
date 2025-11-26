package com.gntech.weather_collector.api.exceptions;

public class RateLimitExceededException extends RuntimeException {
    public RateLimitExceededException() {
        super("Limite de requisições da API excedido. Tente novamente mais tarde");
    }

    public RateLimitExceededException(String message) {
        super(message);
    }
}