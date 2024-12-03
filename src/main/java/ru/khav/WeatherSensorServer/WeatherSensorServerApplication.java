package ru.khav.WeatherSensorServer;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.khav.WeatherSensorServer.models.Sensor;

@SpringBootApplication
public class WeatherSensorServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherSensorServerApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Bean
	public Sensor sensor()
	{
		return new Sensor();
	}
}
