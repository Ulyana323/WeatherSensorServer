package ru.khav.WeatherSensorServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khav.WeatherSensorServer.models.Measurement;
import ru.khav.WeatherSensorServer.models.Sensor;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MeasureRepo extends JpaRepository<Measurement,Integer> {
    List<Measurement> findAll();

    List<Measurement> findAllByOwner(Sensor sensor);

}
