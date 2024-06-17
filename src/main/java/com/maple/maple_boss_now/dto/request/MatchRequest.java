package com.maple.maple_boss_now.dto;

import lombok.Data;

import java.util.List;

@Data
public class MatchRequest {
    private Long bossId;
    private String description;
    private String availability; // 매칭 가능 시간 (ISO 8601 형식의 문자열)
    private MapleInfo leader; // 파티장 정보
    private List<MapleInfo> members; // 파티원 리스트
}
