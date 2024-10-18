package com.example.readConnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.readConnect.model.User;
import com.example.readConnect.model.UserRepository;
import com.example.readConnect.model.UserUpdateDto;
import com.example.readConnect.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    // get a single user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // create a new user, using /users
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // update an existing user, using /users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        user.setUsername(userDetails.getUsername());
//        user.setPassword(userDetails.getPassword());
//        user.setEmail(userDetails.getEmail());
        user.setAge(userDetails.getAge());
        user.setCountry(userDetails.getCountry());
        user.setMode(userDetails.getMode());    
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    // login a user
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
        String identifier = credentials.get("email");
        String password = credentials.get("password");

        // find user by email or username
        User user = userRepository.findByEmailOrUsername(identifier, identifier);
       

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("record", user);
            response.put("type", "user");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }
    
    //check for the existence of email
    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmailExists(@RequestParam String email) {
        boolean emailExists = userRepository.existsByEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", emailExists);
        return ResponseEntity.ok(response);
    }
    
//    // update an existing user
//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userDetailsDto) {
//        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id " + id));
//
//        user.setUsername(userDetailsDto.getUsername());
//        user.setEmail(userDetailsDto.getEmail());
//        user.setPassword(userDetailsDto.getPassword());
//        user.setAge(userDetailsDto.getAge());
//        user.setCountry(userDetailsDto.getCountry());
//        user.setMode(userDetailsDto.getMode());        
//
//        final User updatedUser = userRepository.save(user);
//        return ResponseEntity.ok(updatedUser);
//    }
}
