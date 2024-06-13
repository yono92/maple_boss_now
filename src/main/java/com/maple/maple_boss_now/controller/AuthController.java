package com.maple.maple_boss_now.controller;

import com.maple.maple_boss_now.dto.request.SignupRequest;
import com.maple.maple_boss_now.entity.User;
import com.maple.maple_boss_now.model.AuthProvider;
import com.maple.maple_boss_now.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        log.info("Signup request received: {}", signupRequest.getEmail());

        Optional<User> existingUser = userRepository.findByEmail(signupRequest.getEmail());
        if (existingUser.isPresent()) {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }

        // 새로운 유저 생성
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPhoneNumber(signupRequest.getPhoneNumber());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setProvider(AuthProvider.MAPLE_BOSS_NOW); // Ensure this matches the values in AuthProvider

        userRepository.save(user);
        return new ResponseEntity<>("Signup successful", HttpStatus.OK);
    }
}



