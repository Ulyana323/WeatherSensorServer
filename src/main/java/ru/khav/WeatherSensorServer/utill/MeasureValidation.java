package ru.khav.WeatherSensorServer.utill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.khav.WeatherSensorServer.models.Measurement;
import ru.khav.WeatherSensorServer.services.MeasureService;

@Component
public class MeasureValidation implements Validator {

    @Autowired
    MeasureService measureService;

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasureValidation.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Measurement measurement=(Measurement) target;

    }
}
