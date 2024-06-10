package com.maple.maple_boss_now.service;

import com.maple.maple_boss_now.dto.CharacterBasicInfoResponse;
import com.maple.maple_boss_now.dto.CharacterInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final CharacterService characterService;

    public CharacterBasicInfoResponse getCharacterBasicInfoByName(String characterName) {
        CharacterInfoResponse characterInfo = characterService.getCharacterOcidInfo(characterName);
        String ocid = characterInfo.getOcid();
        CharacterBasicInfoResponse characterBasicInfo = characterService.getCharacterBasicInfo(ocid);
        return characterBasicInfo;
    }
}
