package com.maple.maple_boss_now.dto.character;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class CharacterStatInfoResponse {
    @JsonProperty("date")
    private String date;

    @JsonProperty("character_class")
    private String characterClass;

    @JsonProperty("final_stat")
    private List<StatDetail> finalStat;

    @JsonProperty("remain_ap")
    private int remainAp;

    @Data
    public static class StatDetail {
        @JsonProperty("stat_name")
        private String statName;

        @JsonProperty("stat_value")
        private String statValue;
    }
}