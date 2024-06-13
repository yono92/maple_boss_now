package com.maple.maple_boss_now.controller;

import com.maple.maple_boss_now.dto.CharacterBasicInfoResponse;
import com.maple.maple_boss_now.dto.CharacterDetailsResponse;
import com.maple.maple_boss_now.service.CharacterService;
import com.maple.maple_boss_now.service.SearchService;
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

    @GetMapping("/search/character")
    public ResponseEntity<CharacterDetailsResponse> getCharacterInfo(@RequestParam String characterName) {
        return ResponseEntity.ok(searchService.getCharacterDetailsByName(characterName));
    }

}
