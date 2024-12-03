package ru.khav.WeatherSensorServer.utill;

import org.hibernate.annotations.Cascade;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.khav.WeatherSensorServer.models.Sensor;
import ru.khav.WeatherSensorServer.services.SensorService;

@Component
public class SensorValidation implements Validator {

    private final SensorService sensorService;

    public SensorValidation(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorValidation.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
       Sensor s=(Sensor) target;

        try {
            sensorService.loadUserByUsername(s.getName());
        }
        catch (UsernameNotFoundException ex)
        {
            return;
        }
        errors.rejectValue("name","","this name already existed");
    }
}
