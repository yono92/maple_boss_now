package com.maple.maple_boss_now.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "boss_id", nullable = false)
    private Boss boss;

    @Column(nullable = false)
    private LocalDateTime matchTime;

    @Column(nullable = false)
    private String leaderId; // 파티장 ID

    // 다른 필요한 필드를 추가할 수 있습니다.
}
