package com.gntech.weather_collector.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(length = 10)
    private String country;

    @Column(precision = 5, scale = 2)
    private BigDecimal temperature;

    @Column(name = "feels_like", precision = 5, scale = 2)
    private BigDecimal feelsLike;

    @Column
    private Integer humidity;

    @Column(length = 255)
    private String description;

    @Column(name = "wind_speed", precision = 5, scale = 2)
    private BigDecimal windSpeed;

    @CreatedDate
    @Column(name = "collected_at", updatable = false, nullable = false)
    private LocalDateTime collectedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

}

