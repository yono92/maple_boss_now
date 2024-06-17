package com.maple.maple_boss_now.controller;

import com.maple.maple_boss_now.dto.MatchRequest;
import com.maple.maple_boss_now.entity.Boss;
import com.maple.maple_boss_now.entity.Match;
import com.maple.maple_boss_now.repository.BossRepository;
import com.maple.maple_boss_now.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MatchController {
    private final MatchRepository matchRepository;
    private final BossRepository bossRepository;

    @PostMapping("/matches")
    public ResponseEntity<Match> createMatch(@RequestBody MatchRequest matchRequest) {
        Optional<Boss> bossOptional = bossRepository.findById(matchRequest.getBossId());
        if (bossOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Boss boss = bossOptional.get();
        Match match = Match.builder()
                .boss(boss)
                .description(matchRequest.getDescription())
                .leader(matchRequest.getLeader())
                .members(matchRequest.getMembers())
                .availability(LocalDateTime.parse(matchRequest.getAvailability(), DateTimeFormatter.ISO_DATE_TIME))
                .build();

        Match savedMatch = matchRepository.save(match);
        return ResponseEntity.ok(savedMatch);
    }

    @GetMapping("/match/{id}")
    public ResponseEntity<Match> getMatch(@PathVariable Long id) {
        Optional<Match> match = matchRepository.findById(id);
        return match.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/matches")
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }
}
