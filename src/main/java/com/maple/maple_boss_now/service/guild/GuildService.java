package com.maple.maple_boss_now.service.guild;

import com.maple.maple_boss_now.dto.guild.GuildBasicInfoResponse;
import com.maple.maple_boss_now.dto.guild.GuildInfoResponse;
import com.maple.maple_boss_now.exception.GuildNotFoundException;
import lombok.RequiredArgsConstructor;
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
public class GuildService {

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

    /**
     * 길드의 정보를 조회합니다.
     * @param guildName
     * @return
     */

    public GuildInfoResponse getGuildInfo(String guildName, String worldName) {
        String url = UriComponentsBuilder.fromHttpUrl(apiDomain + "/maplestory/v1/guild/id")
                .queryParam("guild_name", guildName)
                .queryParam("world_name", worldName)
                .build()
                .toUriString();

        ResponseEntity<GuildInfoResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                createRequestEntity(),
                GuildInfoResponse.class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new GuildNotFoundException("Failed to fetch guild info: " + response.getStatusCode());
        }
    }


    public GuildBasicInfoResponse getGuildBasicInfo(String oguildId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = yesterday.format(formatter);

        String url = UriComponentsBuilder.fromHttpUrl(apiDomain + "/maplestory/v1/guild/basic")
                .queryParam("oguild_id", oguildId)
                .queryParam("date", date)
                .build()
                .toUriString();

        ResponseEntity<GuildBasicInfoResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                createRequestEntity(),
                GuildBasicInfoResponse.class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new GuildNotFoundException("Failed to fetch guild basic info: " + response.getStatusCode());
        }
    }
}
