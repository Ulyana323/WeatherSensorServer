package ru.khav.WeatherSensorServer.models;

import lombok.Data;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Table(name = "measures")
@Data
public class Measurement {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="temperature")
    private int temperature;

    @Column(name="is_rainy")
    private boolean ifRain;

    @Column(name="time")
    private LocalDateTime date;


    @ManyToOne
    @JoinColumn(name = "sensor_id",referencedColumnName = "id")
    private Sensor owner;

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", ifRain=" + ifRain +
                ", date=" + date +
                ", owner=" + owner.getName() +
                '}';
    }
}
