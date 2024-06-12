package com.maple.maple_boss_now.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterDetailsResponse {
    private CharacterBasicInfoResponse characterBasicInfo;
    private CharacterStatInfoResponse characterStatInfo;
    private CharacterEquipInfoResponse characterEquipInfo;

}