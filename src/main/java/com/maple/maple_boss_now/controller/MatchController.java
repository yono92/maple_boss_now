package com.maple.maple_boss_now.controller;

import com.maple.maple_boss_now.dto.MatchRequest;
import com.maple.maple_boss_now.entity.Boss;
import com.maple.maple_boss_now.entity.Match;
import com.maple.maple_boss_now.repository.BossRepository;
import com.maple.maple_boss_now.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class MatchController {

    private final MatchRepository matchRepository;
    private final BossRepository bossRepository;

    @PostMapping("/matches")
    public ResponseEntity<Match> createMatch(@RequestBody MatchRequest matchRequest) {
        Optional<Boss> bossOptional = bossRepository.findById(matchRequest.getBossId());
        if (bossOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Boss boss = bossOptional.get();
        Match match = Match.builder()
                .boss(boss)
                .description(matchRequest.getDescription())
                .availability(LocalDateTime.parse(matchRequest.getAvailability(), DateTimeFormatter.ISO_DATE_TIME))
                .leader(matchRequest.getLeader())
                .members(matchRequest.getMembers())
                .build();

        Match savedMatch = matchRepository.save(match);
        return ResponseEntity.ok(savedMatch);
    }

    @GetMapping("/matches")
    public ResponseEntity<List<Match>> getMatches(@RequestParam Long bossId) {
        List<Match> matches = matchRepository.findByBossId(bossId);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/matches/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable Long id) {
        return matchRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
