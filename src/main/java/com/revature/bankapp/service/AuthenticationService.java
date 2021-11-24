package com.revature.bankapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.bankapp.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthenticationService {
    private final String secret = "oh498htg49533o984g4o38934o87ghf4o8yh34f87g43fio7g";

    private final ObjectMapper mapper = new ObjectMapper();

    public String generateJWT(String userJson) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);

            return JWT.create()
                    .withIssuer("Revature")
                    .withClaim("user", userJson)
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            return null;
        }
    }

    public boolean verifyJWT(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Revature")
                    .build();

            verifier.verify(token);

            return true;
        } catch (JWTVerificationException exception){
            return false;
        }
    }

    public User getUserFromHeaders(Map<String, String> headers) {
        String authHeader = headers.get("authorization");

        if (authHeader == null) {
            return null;
        }

        try {
            DecodedJWT decodedJWT = JWT.decode(authHeader.substring(7));
            String userJson = decodedJWT.getClaim("user").asString();

            return this.mapper.readValue(userJson, User.class);
        } catch (JsonProcessingException e) {
            System.out.println("An error has occurred in processing the User JSON from the token.");
            e.printStackTrace();

            return null;
        }
    }
}
