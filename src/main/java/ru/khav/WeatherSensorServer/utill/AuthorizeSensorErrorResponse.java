package ru.khav.WeatherSensorServer.utill;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorizeSensorErrorResponse {

private String message;
private long timestamp;
}
