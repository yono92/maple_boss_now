package com.maple.maple_boss_now.entity;

import com.maple.maple_boss_now.dto.MapleInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private Boss boss; // 매칭되는 보스

    @Column(nullable = false)
    private LocalDateTime availability; // 매칭 가능 시간

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "leader_id")),
            @AttributeOverride(name = "nickname", column = @Column(name = "leader_nickname")),
            @AttributeOverride(name = "job", column = @Column(name = "leader_job")),
            @AttributeOverride(name = "level", column = @Column(name = "leader_level"))
    })
    private MapleInfo leader; // 파티장 정보

    @Column(nullable = false)
    private String description; // 설명

    @ElementCollection
    @CollectionTable(name = "match_members", joinColumns = @JoinColumn(name = "match_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "member_id")),
            @AttributeOverride(name = "nickname", column = @Column(name = "member_nickname")),
            @AttributeOverride(name = "job", column = @Column(name = "member_job")),
            @AttributeOverride(name = "level", column = @Column(name = "member_level"))
    })
    private List<MapleInfo> members; // 파티원 리스트
}
