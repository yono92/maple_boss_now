package com.maple.maple_boss_now.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
