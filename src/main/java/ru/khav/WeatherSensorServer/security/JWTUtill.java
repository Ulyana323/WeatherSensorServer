package ru.khav.WeatherSensorServer.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtill {
    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(String username)
    {
        Date expirationDate= Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
                .withSubject("user details")
                .withClaim("name",username)
                .withIssuedAt(new Date())
                .withIssuer("Ulyashka")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetriveClaim(String Token) throws JWTVerificationException
    {
        JWTVerifier verifier=JWT.require(Algorithm.HMAC256(secret))
                .withSubject("user details")
                .withIssuer("Ulyashka")
                .build();

        DecodedJWT jwt= verifier.verify(Token);
        return jwt.getClaim("name").asString();

    }
}
