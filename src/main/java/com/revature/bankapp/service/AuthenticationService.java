package com.revature.bankapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final String secret = "oh498htg49533o984g4o38934o87ghf4o8yh34f87g43fio7g";

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
}
