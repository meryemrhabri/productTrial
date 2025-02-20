package com.example.altenKata.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.altenKata.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
        public String generatToken(User userLogin) {
            String token = JWT.create()
                    .withSubject(userLogin.getEmail())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                    .sign(Algorithm.HMAC256(secret));
            return token;
        }
    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }
}
