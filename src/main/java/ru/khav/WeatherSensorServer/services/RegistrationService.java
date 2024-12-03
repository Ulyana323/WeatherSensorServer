package ru.khav.WeatherSensorServer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khav.WeatherSensorServer.models.Sensor;

@Service
public class RegistrationService {
    @Autowired
    SensorService sensorService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void registr(Sensor sensor)
    {
       sensor.setName(sensor.getName());
       sensor.setPassword(bCryptPasswordEncoder.encode(sensor.getPassword()));
       sensorService.save(sensor);

    }
}
