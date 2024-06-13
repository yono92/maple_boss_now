package com.maple.maple_boss_now.dto.request;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String phoneNumber;
    private String email;
    private String password;
}
