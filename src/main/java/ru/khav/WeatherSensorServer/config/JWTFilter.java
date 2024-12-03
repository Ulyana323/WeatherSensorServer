package ru.khav.WeatherSensorServer.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.khav.WeatherSensorServer.security.JWTUtill;
import ru.khav.WeatherSensorServer.services.SensorService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtill jwtUtil;

    @Autowired
    private SensorService sensorService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && !authHeader.isEmpty() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            if (jwt.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer header");
                return; // Завершаем выполнение метода
            }

            try {
                String username = jwtUtil.validateTokenAndRetriveClaim(jwt);
                UserDetails userDetails = sensorService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (JWTVerificationException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                return; // Завершаем выполнение метода
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred during authentication");
                return; // Завершаем выполнение метода
            }
        }

        filterChain.doFilter(request, response);
    }
}