package com.maple.maple_boss_now.controller;

import com.maple.maple_boss_now.entity.User;
import com.maple.maple_boss_now.jwt.JwtProvider;
import com.maple.maple_boss_now.model.AuthProvider;
import com.maple.maple_boss_now.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class KakaoAuthController {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @GetMapping("/login/oauth2/code/kakao")
    public void kakaoCallback(@RequestParam String code, HttpServletResponse response) throws IOException {
        log.info("Received code: " + code);

        // 1. 받은 코드로 토큰 요청
        String kakaoToken = getKakaoToken(code);
        log.info("Kakao token: " + kakaoToken);

        // 2. 토큰으로 사용자 정보 요청
        User kakaoUser = getKakaoUser(kakaoToken);
        log.info("Kakao user: " + kakaoUser);

        // 3. 사용자 정보로 회원가입 or 로그인 처리
        Optional<User> userOptional = userRepository.findByKakaoId(kakaoUser.getKakaoId());
        User user;
        if (userOptional.isPresent()) {
            log.info("User already exists: " + userOptional.get());
            user = userOptional.get();
        } else {
            log.info("Creating new user with Kakao ID: " + kakaoUser.getKakaoId());
            user = new User();
            user.setKakaoId(kakaoUser.getKakaoId());
            user.setUsername(kakaoUser.getUsername());
            user.setEmail(kakaoUser.getEmail()); // null일 수 있음
            user.setProvider(AuthProvider.KAKAO);
            user = userRepository.save(user);
            log.info("New user created: " + user);
        }

        // 4. JWT 생성
        String jwtToken = jwtProvider.generateToken(user.getId().toString());
        log.info("JWT token: " + jwtToken);

        // 5. JWT를 클라이언트로 전달 (예: 리다이렉트)
        response.sendRedirect("/login?token=" + jwtToken);
    }

    // 토큰 요청 메서드
    private String getKakaoToken(String code) {
        String tokenEndpoint = "https://kauth.kakao.com/oauth/token";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(tokenEndpoint, HttpMethod.POST, requestEntity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("Access token response: " + response.getBody());
            return response.getBody().get("access_token").toString();
        } else {
            log.error("Failed to retrieve access token. Response: {}", response);
            throw new RuntimeException("Failed to retrieve access token");
        }
    }

    // 사용자 정보 요청 메서드
    private User getKakaoUser(String token) {
        String userEndpoint = "https://kapi.kakao.com/v2/user/me";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(userEndpoint, HttpMethod.GET, requestEntity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("User info response: " + response.getBody());
            Map<String, Object> responseBody = response.getBody();
            Map<String, Object> kakaoAccount = (Map<String, Object>) responseBody.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

            User user = new User();
            user.setKakaoId(responseBody.get("id").toString());
            user.setUsername(profile.get("nickname").toString()); // "nickname" 으로 변경
            user.setProvider(AuthProvider.KAKAO);

            return user;
        } else {
            log.error("Failed to retrieve user info. Response: {}", response);
            throw new RuntimeException("Failed to retrieve user info");
        }
    }
}
