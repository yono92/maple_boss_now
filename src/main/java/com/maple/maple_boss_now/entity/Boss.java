package com.maple.maple_boss_now.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bosses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Boss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String difficulty;

    @Column(nullable = false)
    private String category; // 일일보스, 주간보스, 월간보스 구분
}
