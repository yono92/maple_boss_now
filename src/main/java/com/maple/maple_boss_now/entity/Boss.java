package com.maple.maple_boss_now.entity;

import com.maple.maple_boss_now.enums.BossDifficulty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bosses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Boss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // IDENTITY로 ID 자동 증가 설정
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)")
    private BossDifficulty difficulty;

    @Column(nullable = false)
    private String category; // 일일보스, 주간보스, 월간보스 구분

}
