package ru.khav.WeatherSensorServer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SensorDTO {

    private String name;

    private String password;
}
