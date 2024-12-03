package ru.khav.WeatherSensorServer.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khav.WeatherSensorServer.models.Sensor;
import ru.khav.WeatherSensorServer.repositories.SensorRepo;
import ru.khav.WeatherSensorServer.security.SensorDetails;

import java.util.Optional;

@Service
public class SensorService implements UserDetailsService {
    @Autowired
    SensorRepo sensorRepo;


    @Override
    public UserDetails loadUserByUsername(String SensorName) throws UsernameNotFoundException {
        Optional<Sensor> sensor=sensorRepo.findByName(SensorName);

        if(!sensor.isPresent())
        {
            throw new UsernameNotFoundException("Sensor not found!");
        }
        else
        {
            return new SensorDetails(sensor.get());
        }
    }

    @Transactional
    public void save(Sensor sensor)
    {
        if(!sensorRepo.findByName(sensor.getName()).isPresent()) {
            sensorRepo.save(sensor);
        }

    }

}
