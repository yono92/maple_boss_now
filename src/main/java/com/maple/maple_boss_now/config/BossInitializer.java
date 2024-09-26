package com.maple.maple_boss_now.config;

import com.maple.maple_boss_now.entity.Boss;
import com.maple.maple_boss_now.repository.BossRepository;
import com.maple.maple_boss_now.enums.BossDifficulty;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BossInitializer implements CommandLineRunner {

    private final BossRepository bossRepository;

    @Override
    public void run(String... args) {
        // 보스 데이터가 존재하지 않는 경우에만 초기화
        if (bossRepository.count() == 0) {
            List<Boss> bosses = List.of(
                    // 일일보스
                    new Boss(null, "자쿰", BossDifficulty.EASY, "일일보스"),
                    new Boss(null, "자쿰", BossDifficulty.NORMAL, "일일보스"),
                    new Boss(null, "파풀라투스", BossDifficulty.EASY, "일일보스"),
                    new Boss(null, "매그너스", BossDifficulty.EASY, "일일보스"),
                    new Boss(null, "힐라", BossDifficulty.NORMAL, "일일보스"),
                    new Boss(null, "혼테일", BossDifficulty.EASY, "일일보스"),
                    new Boss(null, "블러디퀸", BossDifficulty.NORMAL, "일일보스"),
                    new Boss(null, "반반", BossDifficulty.NORMAL, "일일보스"),
                    new Boss(null, "피에르", BossDifficulty.NORMAL, "일일보스"),
                    new Boss(null, "벨룸", BossDifficulty.NORMAL, "일일보스"),
                    new Boss(null, "혼테일", BossDifficulty.NORMAL, "일일보스"),
                    new Boss(null, "반 레온", BossDifficulty.EASY, "일일보스"),
                    new Boss(null, "아카이럼", BossDifficulty.EASY, "일일보스"),
                    new Boss(null, "카웅", BossDifficulty.NORMAL, "일일보스"),
                    new Boss(null, "혼테일", BossDifficulty.CHAOS, "일일보스"),
                    new Boss(null, "핑크빈", BossDifficulty.NORMAL, "일일보스"),
                    new Boss(null, "반 레온", BossDifficulty.NORMAL, "일일보스"),
                    new Boss(null, "반 레온", BossDifficulty.HARD, "일일보스"),
                    new Boss(null, "아카이럼", BossDifficulty.NORMAL, "일일보스"),
                    new Boss(null, "매그너스", BossDifficulty.NORMAL, "일일보스"),
                    new Boss(null, "파풀라투스", BossDifficulty.NORMAL, "일일보스"),

                    // 주간보스
                    new Boss(null, "시그너스", BossDifficulty.EASY, "주간보스"),
                    new Boss(null, "힐라", BossDifficulty.HARD, "주간보스"),
                    new Boss(null, "핑크빈", BossDifficulty.CHAOS, "주간보스"),
                    new Boss(null, "시그너스", BossDifficulty.NORMAL, "주간보스"),
                    new Boss(null, "자쿰", BossDifficulty.CHAOS, "주간보스"),
                    new Boss(null, "블러디퀸", BossDifficulty.CHAOS, "주간보스"),
                    new Boss(null, "반반", BossDifficulty.CHAOS, "주간보스"),
                    new Boss(null, "피에르", BossDifficulty.CHAOS, "주간보스"),
                    new Boss(null, "매그너스", BossDifficulty.HARD, "주간보스"),
                    new Boss(null, "벨룸", BossDifficulty.CHAOS, "주간보스"),
                    new Boss(null, "파풀라투스", BossDifficulty.CHAOS, "주간보스"),
                    new Boss(null, "스우", BossDifficulty.NORMAL, "주간보스"),
                    new Boss(null, "데미안", BossDifficulty.NORMAL, "주간보스"),
                    new Boss(null, "가디언 엔젤 슬라임", BossDifficulty.NORMAL, "주간보스"),
                    new Boss(null, "루시드", BossDifficulty.EASY, "주간보스"),
                    new Boss(null, "윌", BossDifficulty.EASY, "주간보스"),
                    new Boss(null, "루시드", BossDifficulty.NORMAL, "주간보스"),
                    new Boss(null, "윌", BossDifficulty.NORMAL, "주간보스"),
                    new Boss(null, "더스크", BossDifficulty.NORMAL, "주간보스"),
                    new Boss(null, "듄켈", BossDifficulty.NORMAL, "주간보스"),
                    new Boss(null, "데미안", BossDifficulty.HARD, "주간보스"),
                    new Boss(null, "스우", BossDifficulty.HARD, "주간보스"),
                    new Boss(null, "루시드", BossDifficulty.HARD, "주간보스"),
                    new Boss(null, "윌", BossDifficulty.HARD, "주간보스"),
                    new Boss(null, "진 힐라", BossDifficulty.NORMAL, "주간보스"),
                    new Boss(null, "가디언 엔젤 슬라임", BossDifficulty.CHAOS, "주간보스"),
                    new Boss(null, "더스크", BossDifficulty.CHAOS, "주간보스"),
                    new Boss(null, "듄켈", BossDifficulty.HARD, "주간보스"),
                    new Boss(null, "진 힐라", BossDifficulty.HARD, "주간보스"),
                    new Boss(null, "선택받은 세렌", BossDifficulty.NORMAL, "주간보스"),
                    new Boss(null, "감시자 칼로스", BossDifficulty.EASY, "주간보스"),
                    new Boss(null, "카링", BossDifficulty.EASY, "주간보스"),
                    new Boss(null, "선택받은 세렌", BossDifficulty.HARD, "주간보스"),
                    new Boss(null, "감시자 칼로스", BossDifficulty.NORMAL, "주간보스"),
                    new Boss(null, "스우", BossDifficulty.EXTREME, "주간보스"),
                    new Boss(null, "카링", BossDifficulty.NORMAL, "주간보스"),
                    new Boss(null, "감시자 칼로스", BossDifficulty.CHAOS, "주간보스"),
                    new Boss(null, "카링", BossDifficulty.HARD, "주간보스"),
                    new Boss(null, "선택받은 세렌", BossDifficulty.EXTREME, "주간보스"),
                    new Boss(null, "감시자 칼로스", BossDifficulty.EXTREME, "주간보스"),
                    new Boss(null, "카링", BossDifficulty.EXTREME, "주간보스"),
                    new Boss(null, "림보", BossDifficulty.EXTREME, "주간보스"),

                    // 월간보스
                    new Boss(null, "검은 마법사", BossDifficulty.HARD, "월간보스"),
                    new Boss(null, "검은 마법사", BossDifficulty.EXTREME, "월간보스")
            );

            bossRepository.saveAll(bosses);
        }
    }
}