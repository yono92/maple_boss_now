package com.maple.maple_boss_now.controller;

import com.maple.maple_boss_now.entity.Boss;
import com.maple.maple_boss_now.repository.BossRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class BossController {

    private final BossRepository bossRepository;

    @GetMapping("/bosses")
    public ResponseEntity<List<Boss>> getBosses() {
        List<Boss> bosses = bossRepository.findAll()
                .stream()
                .filter(boss -> boss.getCategory().equals("주간보스") || boss.getCategory().equals("월간보스"))
                .toList();
        return ResponseEntity.ok(bosses);
    }

    @GetMapping("/boss/{id}")
    public ResponseEntity<Boss> getBossById(@PathVariable Long id) {
        Optional<Boss> boss = bossRepository.findById(id);
        return boss.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
