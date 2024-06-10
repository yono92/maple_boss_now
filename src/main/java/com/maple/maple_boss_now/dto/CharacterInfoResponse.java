package com.maple.maple_boss_now.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CharacterInfoResponse {
    @JsonProperty("ocid")
    String ocid;
}
