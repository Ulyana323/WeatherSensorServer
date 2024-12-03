package ru.khav.WeatherSensorServer.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.khav.WeatherSensorServer.models.Sensor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class MeasureDTO {


    @JsonProperty("temp")
    @NotNull(message = "where temperature?")
    private int temperature;

    @JsonProperty("israin")
    @NotNull(message = "what about Rain?")
    private boolean ifRain;
}
