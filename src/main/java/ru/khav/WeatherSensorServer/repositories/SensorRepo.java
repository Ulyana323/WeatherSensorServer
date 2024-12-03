package ru.khav.WeatherSensorServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khav.WeatherSensorServer.models.Sensor;

import java.util.Optional;

@Repository
public interface SensorRepo extends JpaRepository<Sensor,Integer> {
Optional<Sensor> findByName(String SensorName);
}
