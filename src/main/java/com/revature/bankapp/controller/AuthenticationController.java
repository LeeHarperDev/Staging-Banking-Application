package com.revature.bankapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.bankapp.model.Person;
import com.revature.bankapp.model.Role;
import com.revature.bankapp.model.User;
import com.revature.bankapp.repository.UserRepository;
import com.revature.bankapp.service.AuthenticationService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {
    @Autowired
    @Setter
    private UserRepository userRepository;

    @Autowired
    @Setter
    private AuthenticationService authenticationService;

    @PostMapping(path = "login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> attemptUserLogin(@RequestBody User user) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            List<User> users = userRepository.findAll();

            for (User dbUser : users) {
                if (dbUser.comparePassword(user.getPassword())) {
                    String userJson = mapper.writeValueAsString(dbUser);
                    String jwt = authenticationService.generateJWT(userJson);
                    return new ResponseEntity<>("{\"token\": \"" + jwt + "\", \"user\": " + userJson + "}", HttpStatus.OK);
                }
            }

            return new ResponseEntity<>("{\"message\": \"Invalid Credentials\"}", HttpStatus.UNAUTHORIZED);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("{\"message\": \"Annnn server-side error has occurred.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
