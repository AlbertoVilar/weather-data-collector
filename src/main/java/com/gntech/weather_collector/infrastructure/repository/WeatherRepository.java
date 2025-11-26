package com.gntech.weather_collector.infrastructure.repository;

import com.gntech.weather_collector.infrastructure.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherData, Long> {

    List<WeatherData> findByCityIgnoreCaseOrderByCollectedAtDesc(String city);

    @Query(value = "SELECT * FROM weather_data WHERE unaccent(lower(city)) = unaccent(lower(?1)) ORDER BY collected_at DESC", nativeQuery = true)
    List<WeatherData> findByCityAccentInsensitiveOrderByCollectedAtDesc(String city);
}
