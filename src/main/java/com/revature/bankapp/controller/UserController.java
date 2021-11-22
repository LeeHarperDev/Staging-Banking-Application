package com.revature.bankapp.controller;

import com.revature.bankapp.model.User;
import com.revature.bankapp.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "users")
public class UserController {

    private ArrayList<User> users;

    @Autowired
    @Setter
    private UserRepository userRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        Optional<User> user = this.userRepository.findById(id);

        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>((User) null, HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<User> createNewUser(@RequestBody User user) {
        this.userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Integer> deleteUser(@PathVariable Integer id) {
        this.userRepository.deleteById(id);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
