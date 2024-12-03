package ru.khav.WeatherSensorServer.models;



import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "sensors")
@Data
public class Sensor implements UserDetails {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int Id;

    @NotNull(message = "sensor name can't be empty!")
    @Column(name="sensor_name")
    private String  name;

@NotNull(message = "sensor pass can't be empty!")
    @Column(name="sensor_pass")
    private String password;


    @OneToMany(mappedBy = "owner",fetch = FetchType.EAGER)
    private List<Measurement> measures;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.name;
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

}
