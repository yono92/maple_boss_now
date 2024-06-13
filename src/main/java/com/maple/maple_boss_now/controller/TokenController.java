package com.maple.maple_boss_now.controller;

import com.maple.maple_boss_now.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {

    private final JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<?> saveToken(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        if (jwtProvider.validateToken(token)) {
            String userId = jwtProvider.getUserIdFromToken(token);
            jwtProvider.storeToken(userId, token);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        if (token != null && jwtProvider.validateToken(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
        }
    }
}
