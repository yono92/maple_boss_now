package com.maple.maple_boss_now.dto;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String provider;
    private String createdAt;
}
