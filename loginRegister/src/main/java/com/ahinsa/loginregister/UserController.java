package com.ahinsa.loginregister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public User Register(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        // Check if the user exists in the repository and credentials match
        User oldUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (oldUser != null) {
            return ResponseEntity.ok(oldUser); // Login successful, return user details
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password"); // Login failed, return error message
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // No users found
        } else {
            return ResponseEntity.ok(users); // Return the list of users
        }
    }




}
