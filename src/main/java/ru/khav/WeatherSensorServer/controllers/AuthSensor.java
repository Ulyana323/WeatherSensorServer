package ru.khav.WeatherSensorServer.controllers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.khav.WeatherSensorServer.DTO.SensorDTO;
import ru.khav.WeatherSensorServer.models.Sensor;
import ru.khav.WeatherSensorServer.security.JWTUtill;
import ru.khav.WeatherSensorServer.services.RegistrationService;
import ru.khav.WeatherSensorServer.utill.SensorValidation;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/auth")
public class AuthSensor {

    @Autowired
    RegistrationService registrationService;
    @Autowired
    SensorValidation sensorValidation;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    JWTUtill jwtUtill;
    @Autowired
    AuthenticationManager authenticationManager;

    private Sensor convertFromDTO(SensorDTO sensorDTO) {
        return this.modelMapper.map(sensorDTO, Sensor.class);

    }

    @PostMapping("/registr")
    public ResponseEntity<?> registerNewSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                            BindingResult bindingResult) {
        sensorValidation.validate(convertFromDTO(sensorDTO),bindingResult);
        if(bindingResult.hasErrors())
        {
            return new ResponseEntity<>("this sensor is already existing",HttpStatus.BAD_REQUEST);
        }
        registrationService.registr(convertFromDTO(sensorDTO));
        String token = jwtUtill.generateToken(sensorDTO.getName());

        Map<String, String> map = new HashMap<>();
        map.put("jwt-token-registr", token);


        return new ResponseEntity<>(map,HttpStatus.ACCEPTED);
    }

    @PostMapping("/log")
    public Map<String ,String> performLogin(@RequestBody SensorDTO sensorDTO)
    {
        System.out.println("Attempting to login for: " + sensorDTO.getName());
        UsernamePasswordAuthenticationToken authInputToken=new UsernamePasswordAuthenticationToken(
                sensorDTO.getName(),sensorDTO.getPassword());

        try{
            authenticationManager.authenticate(authInputToken);
        }
        catch (BadCredentialsException e)
        {
            Map<String, String> map = new HashMap<>();
            map.put("message","Incorrect credentials!");
            return map;
        }
     catch (Exception e) {
         Map<String, String> map = new HashMap<>();
         map.put("Ошибка аутентификации: " , e.getMessage());
         return map;
    }

        String token=jwtUtill.generateToken(sensorDTO.getName());
        Map<String, String> map = new HashMap<>();
        map.put("jwt-token-logged",token);
        return map;
    }

}
