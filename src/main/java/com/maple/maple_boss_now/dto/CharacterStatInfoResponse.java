package com.maple.maple_boss_now.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CharacterStatInfoResponse {
    private LocalDateTime date;
    private String characterClass;
    private List<Stat> finalStat;
    private int remainAp;

    @Data
    public static class Stat {
        private String statName;
        private String statValue;
    }
}
