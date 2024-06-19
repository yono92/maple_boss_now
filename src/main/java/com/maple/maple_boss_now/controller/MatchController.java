package com.maple.maple_boss_now.controller;


import com.maple.maple_boss_now.dto.request.MatchRequest;
import com.maple.maple_boss_now.entity.Boss;
import com.maple.maple_boss_now.entity.Match;
import com.maple.maple_boss_now.entity.PartyMember;
import com.maple.maple_boss_now.repository.BossRepository;
import com.maple.maple_boss_now.repository.MatchRepository;
import com.maple.maple_boss_now.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;


    @PostMapping("/matches")
    public ResponseEntity<?> createMatch(@RequestBody MatchRequest matchRequest) {
        try {
            Match match = matchService.createMatch(matchRequest);
            return ResponseEntity.ok(match);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/matches")
    public ResponseEntity<List<Match>> getMatches(@RequestParam Long bossId) {
        List<Match> matches = matchService.getMatchesByBossId(bossId);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/matches/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable Long id) {
        return matchService.getMatchById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
