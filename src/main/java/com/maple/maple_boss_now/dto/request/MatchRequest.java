package com.maple.maple_boss_now.dto.request;

import com.maple.maple_boss_now.entity.PartyMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchRequest {
    private Long bossId;
    private String matchTime; // ISO 8601 형식
    private String leaderNickname;
    private String leaderJob;
    private int leaderLevel;
    private List<PartyMember> members;
    private String description;
}
