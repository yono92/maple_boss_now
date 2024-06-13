package com.maple.maple_boss_now.repository;

import com.maple.maple_boss_now.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByBossId(Long bossId);
}
