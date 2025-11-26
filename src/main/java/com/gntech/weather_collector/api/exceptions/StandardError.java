package com.gntech.weather_collector.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardError {

    private String timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;


}
