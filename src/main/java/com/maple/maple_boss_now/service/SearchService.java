package com.maple.maple_boss_now.service;

import com.maple.maple_boss_now.dto.CharacterBasicInfoResponse;
import com.maple.maple_boss_now.dto.CharacterDetailsResponse;
import com.maple.maple_boss_now.dto.CharacterInfoResponse;
import com.maple.maple_boss_now.dto.CharacterStatInfoResponse;
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
        CharacterDetailsResponse response = new CharacterDetailsResponse();
        response.setCharacterBasicInfo(characterBasicInfo);
        response.setCharacterStatInfo(characterStatInfo);
        return response;
    }
}
