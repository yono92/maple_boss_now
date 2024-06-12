package com.maple.maple_boss_now.entity;

import com.maple.maple_boss_now.model.AuthProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kakaoId")
    private String kakaoId;

    private String username;
    private String email;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
}
