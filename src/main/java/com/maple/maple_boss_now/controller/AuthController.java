package com.maple.maple_boss_now.controller;

import com.maple.maple_boss_now.dto.request.LoginRequest;
import com.maple.maple_boss_now.dto.request.SignupRequest;
import com.maple.maple_boss_now.entity.User;
import com.maple.maple_boss_now.jwt.JwtProvider;
import com.maple.maple_boss_now.model.AuthProvider;
import com.maple.maple_boss_now.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

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
        user.setProvider(AuthProvider.MAPLE_BOSS_NOW);

        userRepository.save(user);
        return new ResponseEntity<>("Signup successful", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login request received: {}", loginRequest.getEmail());

        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                String token = jwtProvider.generateToken(user.getId().toString(), user.getUsername()); // username 추가
                return ResponseEntity.ok().body(Map.of("token", token)); // JWT 토큰을 JSON 형식으로 반환
            } else {
                return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
        }
    }

    public Map<String, Object> validateToken(@RequestHeader("Authorization") String tokenHeader) {
        Map<String, Object> response = new HashMap<>();
        String token = tokenHeader.replace("Bearer ", "");
        boolean valid = jwtProvider.validateToken(token);

        response.put("valid", valid);

        if (valid) {
            Claims claims = jwtProvider.getClaimsFromToken(token);
            String username = claims.get("username", String.class); // 사용자 이름 추출
            response.put("username", username);
        }

        return response;
    }
}
