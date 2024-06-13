package com.maple.maple_boss_now.entity;

import com.maple.maple_boss_now.model.AuthProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String kakaoId; // 카카오 로그인 사용자에 대한 식별자

    @Column(unique = false, nullable = false)
    private String username; // 사용자 이름 (닉네임)


    @Column(nullable = false) // nullable 추가
    private String phoneNumber; // 전화번호

    // 메이플 스토리 닉네임
    @Column(unique = true, nullable = true)
    private String mapleStoryNickname; // 컬럼명 수정: mapleStory_Nickname -> mapleStoryNickname

    @Column(nullable = false)
    private String password; // 사용자 비밀번호

    @Column(unique = true, nullable = false)
    private String email; // 이메일

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) // nullable 추가
    private AuthProvider provider; // 인증 제공자 (카카오, 일반 등)

    private String profileImageUrl; // 프로필 이미지 URL (선택 사항)


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; // 생성 일자

    @LastModifiedDate
    private LocalDateTime updatedAt; // 수정 일자
}
