package com.maple.maple_boss_now.controller;

import com.maple.maple_boss_now.dto.character.CharacterDetailsResponse;
import com.maple.maple_boss_now.dto.guild.GuildBasicInfoResponse;
import com.maple.maple_boss_now.dto.guild.GuildInfoResponse;
import com.maple.maple_boss_now.service.SearchService;
import com.maple.maple_boss_now.service.guild.GuildService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SearchController {

    private final SearchService searchService;

    private final GuildService guildService;

    @GetMapping("/search/character")
    public ResponseEntity<CharacterDetailsResponse> getCharacterInfo(@RequestParam String characterName) {
        return ResponseEntity.ok(searchService.getCharacterDetailsByName(characterName));
    }




    @GetMapping("/guild")
    public ResponseEntity<GuildBasicInfoResponse> searchGuild(@RequestParam String guildName, @RequestParam String worldName) {
        GuildBasicInfoResponse guildInfoResponse = guildService.getGuildInfo(guildName, worldName);
        return ResponseEntity.ok(guildInfoResponse);
    }

}
