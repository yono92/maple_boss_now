package com.maple.maple_boss_now.dto.character;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본 생성자
public class CharacterBasicInfoResponse {
    private String date;
    @JsonProperty("character_name")
    private String characterName;
    @JsonProperty("world_name")
    private String worldName;
    @JsonProperty("character_gender")
    private String characterGender;
    @JsonProperty("character_class")
    private String characterClass;
    @JsonProperty("character_class_level")
    private String characterClassLevel;
    @JsonProperty("character_level")
    private int characterLevel;
    @JsonProperty("character_exp")
    private long characterExp;
    @JsonProperty("character_exp_rate")
    private String characterExpRate;
    @JsonProperty("character_guild_name")
    private String characterGuildName;
    @JsonProperty("character_image")
    private String characterImage;
    @JsonProperty("character_date_create")
    private String characterDateCreate;
    @JsonProperty("access_flag")
    private String accessFlag;
    @JsonProperty("liberation_quest_clear_flag")
    private String liberationQuestClearFlag;
}
