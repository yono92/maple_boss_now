package com.maple.maple_boss_now.dto.guild;

import lombok.Data;

import java.util.List;

@Data
public class GuildBasicInfoResponse {
    private String date;
    private String worldName;
    private String guildName;
    private int guildLevel;
    private int guildFame;
    private int guildPoint;
    private String guildMasterName;
    private int guildMemberCount;
    private List<String> guildMember;
    private List<GuildSkill> guildSkill;
    private List<GuildSkill> guildNoblesseSkill;
    private String guildMark;
    private String guildMarkCustom;

    @Data
    public static class GuildSkill {
        private String skillName;
        private String skillDescription;
        private int skillLevel;
        private String skillEffect;
        private String skillIcon;
    }
}
