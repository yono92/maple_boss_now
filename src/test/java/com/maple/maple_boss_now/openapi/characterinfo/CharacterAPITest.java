package com.maple.maple_boss_now.openapi.characterinfo;

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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
                .build()  // 인코딩은 여기서 수행됩니다.
                .toUriString();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", apiKey);

        // 요청 엔티티 생성
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // When - API 호출
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // Then - 응답 검증
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("ocid");

        System.out.println("Response: " + response.getBody());
    }
}
