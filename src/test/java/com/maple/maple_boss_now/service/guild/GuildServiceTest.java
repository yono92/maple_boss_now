package com.maple.maple_boss_now.service.guild;

import com.maple.maple_boss_now.dto.guild.GuildBasicInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class GuildServiceTest {

    @Autowired
    GuildService guildService;

    @Value("${api.test-token}")
    private String apiKey;

    @Value("${api.domain}")
    private String apiDomain;

    @Test
    void getGuildInfo() {
        // Given
        String guildName = "부우";
        String worldName = "크로아";

        // When
        GuildBasicInfoResponse guildInfoResponse = guildService.getGuildInfo(guildName, worldName);

        // Then
        assertThat(guildInfoResponse).isNotNull();
        assertThat(guildInfoResponse.getGuildName()).isEqualTo(guildName);
        assertThat(guildInfoResponse.getWorldName()).isEqualTo(worldName);
    }
}