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
    public ResponseEntity<String> saveToken(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        if (jwtProvider.validateToken(token)) {
            // 토큰이 유효하면 Redis에 저장하는 방식은 이미 jwtProvider에서 처리됨.
            return ResponseEntity.ok("Token saved");
        } else {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }

    public ResponseEntity<Void> validateToken(HttpServletRequest request) {
        String token = jwtProvider.getJwtFromRequest(request);
        if (token != null && jwtProvider.validateToken(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
