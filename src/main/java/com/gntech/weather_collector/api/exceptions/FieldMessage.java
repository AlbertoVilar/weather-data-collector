package com.gntech.weather_collector.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FieldMessage {

    private String FieldName;
    private String message;


}
