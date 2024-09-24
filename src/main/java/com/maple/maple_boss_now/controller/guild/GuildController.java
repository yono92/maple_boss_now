package com.maple.maple_boss_now.controller.guild;

import com.maple.maple_boss_now.dto.guild.GuildBasicInfoResponse;
import com.maple.maple_boss_now.dto.guild.GuildInfoResponse;
import com.maple.maple_boss_now.service.guild.GuildService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class GuildController {
    private final GuildService guildService;

    @GetMapping("/guild/basic-info")
    public GuildBasicInfoResponse getGuildBasicInfo(@RequestParam String guildName, @RequestParam String worldName) {
        return guildService.getGuildInfo(guildName, worldName);
    }
}
