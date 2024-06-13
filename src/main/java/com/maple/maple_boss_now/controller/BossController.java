package com.maple.maple_boss_now.controller;

import com.maple.maple_boss_now.entity.Boss;
import com.maple.maple_boss_now.repository.BossRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BossController {

    private final BossRepository bossRepository;

    @GetMapping("/bosses")
    public List<Boss> getBosses() {
        return bossRepository.findAll();
    }

}
