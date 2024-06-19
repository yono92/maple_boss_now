package com.maple.maple_boss_now.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PartyMember {
    private String nickname;
    private String job;
    private int level;

}
