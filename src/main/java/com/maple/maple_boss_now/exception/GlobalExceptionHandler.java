package com.maple.maple_boss_now.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 일반 예외 처리 (HTML 페이지로 리다이렉트)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", "An unexpected error occurred: " + ex.getMessage());
        return mav;
    }
    // 404 Not Found - 길드를 찾을 수 없을 때
    @ExceptionHandler(GuildNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleGuildNotFoundException(GuildNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Guild not found");
        response.put("details", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
