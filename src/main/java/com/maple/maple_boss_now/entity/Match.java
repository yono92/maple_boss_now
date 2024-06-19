package com.maple.maple_boss_now.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Boss boss;

    @Column(nullable = false)
    private LocalDateTime matchTime;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nickname", column = @Column(name = "leader_nickname")),
            @AttributeOverride(name = "job", column = @Column(name = "leader_job")),
            @AttributeOverride(name = "level", column = @Column(name = "leader_level"))
    })
    private PartyMember leader; // 파티장 정보

    @Column(nullable = false)
    private String description; // 설명

    @ElementCollection
    @CollectionTable(name = "match_members", joinColumns = @JoinColumn(name = "match_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "nickname", column = @Column(name = "member_nickname")),
            @AttributeOverride(name = "job", column = @Column(name = "member_job")),
            @AttributeOverride(name = "level", column = @Column(name = "member_level"))
    })
    private List<PartyMember> members; // 파티원 리스트
}
