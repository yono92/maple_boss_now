package com.maple.maple_boss_now.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<String> handleCharacterNotFoundException(CharacterNotFoundException ex) {
        // 예외 메시지를 사용하여 HTTP 상태 코드 404와 함께 응답을 반환합니다.
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
