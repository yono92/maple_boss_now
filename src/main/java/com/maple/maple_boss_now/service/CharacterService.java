package com.maple.maple_boss_now.service;

import com.maple.maple_boss_now.dto.CharacterBasicInfoResponse;
import com.maple.maple_boss_now.dto.CharacterInfoResponse;
import com.maple.maple_boss_now.dto.CharacterStatInfoResponse;
import com.maple.maple_boss_now.exception.CharacterNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private static final Logger log = LoggerFactory.getLogger(CharacterService.class);
    private final RestTemplate restTemplate;

    @Value("${api.domain}")
    private String apiDomain;

    @Value("${api.test-token}")
    private String apiToken;

    private HttpEntity<String> createRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", apiToken);
        return new HttpEntity<>(headers);
    }

    private String createUrl(String path, String ocid, String date) {
        return UriComponentsBuilder.fromHttpUrl(apiDomain + path)
                .queryParam("ocid", ocid)
                .queryParam("date", date)
                .build()
                .toUriString();
    }
    /**
     * 캐릭터의 OCID 정보를 조회합니다.
     * @param characterName
     * @return
     */
    public CharacterInfoResponse getCharacterOcidInfo(String characterName) {
        String url = UriComponentsBuilder.fromHttpUrl(apiDomain + "/maplestory/v1/id")
                .queryParam("character_name", characterName)
                .build()
                .toUriString();
        ResponseEntity<CharacterInfoResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                createRequestEntity(),
                CharacterInfoResponse.class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new CharacterNotFoundException("Failed to fetch character info: " + response.getStatusCode());
        }
    }

    /**
     * 캐릭터의 기본 정보를 조회합니다.
     * @param ocid
     * @return
     * @throws CharacterNotFoundException
     */
    public CharacterBasicInfoResponse getCharacterBasicInfo(String ocid) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = yesterday.format(formatter);

        String url = createUrl("/maplestory/v1/character/basic", ocid, date);
        ResponseEntity<CharacterBasicInfoResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                createRequestEntity(),
                CharacterBasicInfoResponse.class
        );
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new CharacterNotFoundException("Failed to fetch character basic info: " + response.getStatusCode());
        }
    }

    public CharacterStatInfoResponse getCharacterStatInfo(String ocid) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = yesterday.format(formatter);

        String url = createUrl("/maplestory/v1/character/stat", ocid, date);
        ResponseEntity<CharacterStatInfoResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                createRequestEntity(),
                CharacterStatInfoResponse.class
        );
        log.info("CharacterStatInfoResponse: {}", response);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new CharacterNotFoundException("Failed to fetch character stat info: " + response.getStatusCode());
        }
    }

}
