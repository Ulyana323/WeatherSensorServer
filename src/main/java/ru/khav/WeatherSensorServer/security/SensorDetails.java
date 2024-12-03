package ru.khav.WeatherSensorServer.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.khav.WeatherSensorServer.models.Measurement;
import ru.khav.WeatherSensorServer.models.Sensor;

import java.util.Collection;

public class SensorDetails implements UserDetails {

private final  Sensor sensor;

    public SensorDetails(Sensor sensor) {
        this.sensor = sensor;
    }

    public void addMeasure(Measurement m)
    {
        sensor.getMeasures().add(m);
    }

    public Sensor getSensor()
    {
        return this.sensor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.sensor.getPassword();
    }

    @Override
    public String getUsername() {
        return this.sensor.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public String toString() {
        return "SensorDetails{" +
                "sensorId=" + sensor.getId() + // Предполагая, что у вашего сенсора есть метод getId()
                ", sensorName='" + sensor.getName();
    }
}
