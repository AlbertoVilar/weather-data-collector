package com.gntech.weather_collector.infrastructure.repository;

import com.gntech.weather_collector.infrastructure.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherData, Long> {

    List<WeatherData> findByCityOrderByCollectedAtDesc(String city);
}

