package com.example.backend.controllers;

import com.example.backend.models.User;
import com.example.backend.services.UserService;
import com.example.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        userService.register(user);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User found = userService.login(user.getUsername(), user.getPassword());
        if (found != null) {
            String token = jwtUtil.generateToken(found.getUsername());
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "userId", found.getId(),
                    "firstLogin", found.isFirstLogin()
            ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("failed");
    }
    @PutMapping("/set-first-login-false/{id}")
    public ResponseEntity<?> setFirstLoginFalse(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setFirstLogin(false);
            userService.register(u); // Lưu lại
            return ResponseEntity.ok("updated");
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/by-username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


}


