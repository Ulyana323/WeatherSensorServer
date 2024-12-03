package ru.khav.WeatherSensorServer.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.khav.WeatherSensorServer.DTO.MeasureDTO;
import ru.khav.WeatherSensorServer.DTO.SensorDTO;
import ru.khav.WeatherSensorServer.models.Measurement;
import ru.khav.WeatherSensorServer.models.Sensor;
import ru.khav.WeatherSensorServer.security.JWTUtill;
import ru.khav.WeatherSensorServer.security.SensorDetails;
import ru.khav.WeatherSensorServer.services.MeasureService;
import ru.khav.WeatherSensorServer.services.RegistrationService;
import ru.khav.WeatherSensorServer.services.SensorService;
import ru.khav.WeatherSensorServer.utill.AuthorizeSensorErrorResponse;
import ru.khav.WeatherSensorServer.utill.UnauthorizedSensorException;

import javax.validation.Valid;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/measures")
public class SensorMeasures {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    MeasureService measureService;

    @Autowired
    JWTUtill jwtUtill;

    @Autowired
    SensorService sensorService;


    private Measurement convertFromDTO(MeasureDTO measureDTO) {
        return this.modelMapper.map(measureDTO, Measurement.class);

    }

    @PostMapping("/add")
    public ResponseEntity<?> newMeasure(@RequestBody @Valid MeasureDTO measureDTO,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        SensorDetails sensor = GetSensorNameContext();

        Measurement m = convertFromDTO(measureDTO);
        m.setOwner(sensor.getSensor());
        measureService.save(m);


        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


    private SensorDetails GetSensorNameContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SensorDetails sensor = (SensorDetails) authentication.getPrincipal();


        return sensor;
    }

    @GetMapping("/MPS")
    public ResponseEntity<List<Measurement>> findMeasuresPerSensor() {
        SensorDetails sensordetais = GetSensorNameContext();
        List<Measurement> lst;
        lst = measureService.findByCurSensor(sensordetais.getSensor());
        if(lst.isEmpty())
        {
            return new ResponseEntity<>(lst, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(lst, HttpStatus.OK);
    }


    @ExceptionHandler(UnauthorizedSensorException.class)
    public ResponseEntity<AuthorizeSensorErrorResponse> handleInvalidTokenException(UnauthorizedSensorException ex) {
        AuthorizeSensorErrorResponse response=new AuthorizeSensorErrorResponse(
"this sensor can't send measures, as it isn't authorized",
System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
