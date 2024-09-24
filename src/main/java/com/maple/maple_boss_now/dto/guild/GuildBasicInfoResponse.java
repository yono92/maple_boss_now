package com.maple.maple_boss_now.dto.guild;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GuildBasicInfoResponse {

    @JsonProperty("date")
    private String date;

    @JsonProperty("world_name")
    private String worldName;

    @JsonProperty("guild_name")
    private String guildName;

    @JsonProperty("guild_level")
    private int guildLevel;

    @JsonProperty("guild_fame")
    private int guildFame;

    @JsonProperty("guild_point")
    private int guildPoint;

    @JsonProperty("guild_master_name")
    private String guildMasterName;

    @JsonProperty("guild_member_count")
    private int guildMemberCount;

    @JsonProperty("guild_member")
    private List<String> guildMember;

    @JsonProperty("guild_skill")
    private List<GuildSkill> guildSkill;

    @JsonProperty("guild_noblesse_skill")
    private List<GuildSkill> guildNoblesseSkill;

    @JsonProperty("guild_mark")
    private String guildMark;

    @JsonProperty("guild_mark_custom")
    private String guildMarkCustom;

    @Data
    public static class GuildSkill {
        @JsonProperty("skill_name")
        private String skillName;

        @JsonProperty("skill_description")
        private String skillDescription;

        @JsonProperty("skill_level")
        private int skillLevel;

        @JsonProperty("skill_effect")
        private String skillEffect;

        @JsonProperty("skill_icon")
        private String skillIcon;
    }
}
