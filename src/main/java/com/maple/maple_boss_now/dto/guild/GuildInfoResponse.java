package com.maple.maple_boss_now.dto.guild;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GuildInfoResponse {
    @JsonProperty("oguild_id")
    private String oguildId;
}
