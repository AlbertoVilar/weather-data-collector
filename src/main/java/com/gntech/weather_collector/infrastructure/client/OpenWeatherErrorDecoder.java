package com.gntech.weather_collector.infrastructure.client;

import com.gntech.weather_collector.api.exceptions.CityNotFoundException;
import com.gntech.weather_collector.api.exceptions.InvalidApiKeyException;
import com.gntech.weather_collector.api.exceptions.RateLimitExceededException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class OpenWeatherErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();

        if (status == 404) {
            return new CityNotFoundException("Cidade não encontrada na API externa");
        }
        if (status == 401) {
            return new InvalidApiKeyException();
        }
        if (status == 429) {
            return new RateLimitExceededException();
        }

        return new RuntimeException("Erro ao chamar API externa de clima: status=" + status);
    }
}
