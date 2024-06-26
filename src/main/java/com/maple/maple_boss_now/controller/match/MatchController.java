package com.maple.maple_boss_now.controller.match;


import com.maple.maple_boss_now.dto.request.MatchRequest;
import com.maple.maple_boss_now.entity.Match;
import com.maple.maple_boss_now.entity.PartyMember;
import com.maple.maple_boss_now.service.match.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
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
        log.info("getMatchById: {}", id);
        return matchService.getMatchById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/match-detail/{id}")
    public ResponseEntity<Match> getMatchDetailById(@PathVariable Long id) {
        log.info("getMatchDetailById: {}", id);
        return matchService.getMatchById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/matches/{matchId}/join")
    public ResponseEntity<?> joinMatch(@PathVariable Long matchId, @RequestBody PartyMember member) {

        return matchService.joinMatch(matchId, member)
                .map(match -> ResponseEntity.ok().body(match))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
