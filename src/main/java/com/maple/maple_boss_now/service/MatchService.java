package com.maple.maple_boss_now.service;

import com.maple.maple_boss_now.dto.request.MatchRequest;
import com.maple.maple_boss_now.entity.Boss;
import com.maple.maple_boss_now.entity.Match;
import com.maple.maple_boss_now.entity.PartyMember;
import com.maple.maple_boss_now.repository.BossRepository;
import com.maple.maple_boss_now.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final BossRepository bossRepository;

    public Match createMatch(MatchRequest matchRequest) {
        Optional<Boss> bossOptional = bossRepository.findById(matchRequest.getBossId());
        if (bossOptional.isEmpty()) {
            throw new IllegalArgumentException("Boss not found");
        }

        Boss boss = bossOptional.get();
        LocalDateTime matchTime;
        try {
            matchTime = LocalDateTime.parse(matchRequest.getMatchTime());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + matchRequest.getMatchTime());
        }

        Match match = Match.builder()
                .boss(boss)
                .matchTime(matchTime)
                .leader(new PartyMember(matchRequest.getLeaderNickname(), matchRequest.getLeaderJob(), matchRequest.getLeaderLevel()))
                .members(matchRequest.getMembers() == null ? List.of() : matchRequest.getMembers())
                .description(matchRequest.getDescription())
                .build();

        return matchRepository.save(match);
    }

    public List<Match> getMatchesByBossId(Long bossId) {
        return matchRepository.findByBossId(bossId);
    }

    public Optional<Match> getMatchById(Long id) {
        return matchRepository.findById(id);
    }

    // 추가적인 서비스 메서드들 (업데이트, 삭제 등)도 여기에 추가할 수 있습니다.
}
