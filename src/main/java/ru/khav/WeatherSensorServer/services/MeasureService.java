package ru.khav.WeatherSensorServer.services;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.internal.util.ZonedDateTimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.khav.WeatherSensorServer.models.Measurement;
import ru.khav.WeatherSensorServer.models.Sensor;
import ru.khav.WeatherSensorServer.repositories.MeasureRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MeasureService {

    @Autowired
    MeasureRepo measureRepo;

    public void save(Measurement measurement)
    {
        measurement.setDate(LocalDateTime.now());
        measureRepo.save(measurement);
    }

   public List<Measurement> findByCurSensor(Sensor sensor)
   {
       List<Measurement> m=new ArrayList<>();
       m = measureRepo.findAllByOwner(sensor);
       if(m.isEmpty())
       {
           return null;
       }
       return m;
   }

   /* public Optional<List<Measurement>> MeasuresBefore(LocalDateTime date,Sensor sensor)
    {
        return measureRepo.findAllByDateBeforeAndOwner(date,sensor);

    }*/
}

