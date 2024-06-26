package com.maple.maple_boss_now.service;

import com.maple.maple_boss_now.dto.character.*;
import com.maple.maple_boss_now.service.character.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final CharacterService characterService;

    public CharacterDetailsResponse getCharacterDetailsByName(String characterName) {
        CharacterInfoResponse characterInfo = characterService.getCharacterOcidInfo(characterName);
        String ocid = characterInfo.getOcid();
        CharacterBasicInfoResponse characterBasicInfo = characterService.getCharacterBasicInfo(ocid);
        CharacterStatInfoResponse characterStatInfo = characterService.getCharacterStatInfo(ocid);
        CharacterEquipInfoResponse characterEquipInfo = characterService.getCharacterEquipInfo(ocid);
        CharacterDetailsResponse response = new CharacterDetailsResponse();
        response.setCharacterBasicInfo(characterBasicInfo);
        response.setCharacterStatInfo(characterStatInfo);
        response.setCharacterEquipInfo(characterEquipInfo);
        return response;
    }
}
