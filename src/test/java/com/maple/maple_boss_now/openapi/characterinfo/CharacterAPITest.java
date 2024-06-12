package com.maple.maple_boss_now.openapi.characterinfo;

import com.maple.maple_boss_now.dto.CharacterBasicInfoResponse;
import com.maple.maple_boss_now.dto.CharacterInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class CharacterAPITest {
    //  RestTemplate 주입
    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.test-token}")
    private String apiKey;

    @Value("${api.domain}")
    private String apiDomain;

    @Test
    void getCharacterInfo() throws Exception {
        // Given
        String characterName = "붓면";

        String url = UriComponentsBuilder.fromHttpUrl(apiDomain + "/maplestory/v1/id")
                .queryParam("character_name", characterName)
                .build()
                .toUriString();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", apiKey);

        // 요청 엔티티 생성
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // When - API 호출
        ResponseEntity<CharacterInfoResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                CharacterInfoResponse.class
        );

        // Then - 응답 검증
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getOcid()).isEqualTo("a1130838a07cb1565cad1e9bf86293b6");

        log.debug("Response OCID: {}", response.getBody().getOcid());
    }

    @Test
    void getCharacterBasicInfo() throws Exception {
        // Given
        String ocid = "a1130838a07cb1565cad1e9bf86293b6"; // 이전 테스트에서 얻은 ocid
        String date = "2024-06-09"; // 조회 기준일

        String url = UriComponentsBuilder.fromHttpUrl(apiDomain + "/maplestory/v1/character/basic")
                .queryParam("ocid", ocid)
                .queryParam("date", date)
                .build()
                .toUriString();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", apiKey);

        // 요청 엔티티 생성
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // When - API 호출
        ResponseEntity<CharacterBasicInfoResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                CharacterBasicInfoResponse.class
        );

        // Then - 응답 검증
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCharacterName()).isEqualTo("붓면");
        log.info("response.getBody() :  {} ", response.getBody());
        log.debug("Response Character Name: {}", response.getBody().getCharacterName());
    }
}
