package com.maple.maple_boss_now.dto;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class MapleInfo {
    private Long id;
    private String nickname;
    private String job;
    private int level;
}
