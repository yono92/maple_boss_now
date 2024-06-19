package com.maple.maple_boss_now.config;

import com.maple.maple_boss_now.entity.Boss;
import com.maple.maple_boss_now.repository.BossRepository;
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
                    new Boss(null, "자쿰", "이지", "일일보스"),
                    new Boss(null, "자쿰", "노멀", "일일보스"),
                    new Boss(null, "파풀라투스", "이지", "일일보스"),
                    new Boss(null, "매그너스", "이지", "일일보스"),
                    new Boss(null, "힐라", "노멀", "일일보스"),
                    new Boss(null, "혼테일", "이지", "일일보스"),
                    new Boss(null, "블러디퀸", "노멀", "일일보스"),
                    new Boss(null, "반반", "노멀", "일일보스"),
                    new Boss(null, "피에르", "노멀", "일일보스"),
                    new Boss(null, "벨룸", "노멀", "일일보스"),
                    new Boss(null, "혼테일", "노멀", "일일보스"),
                    new Boss(null, "반 레온", "이지", "일일보스"),
                    new Boss(null, "아카이럼", "이지", "일일보스"),
                    new Boss(null, "카웅", "노멀", "일일보스"),
                    new Boss(null, "혼테일", "카오스", "일일보스"),
                    new Boss(null, "핑크빈", "노멀", "일일보스"),
                    new Boss(null, "반 레온", "노멀", "일일보스"),
                    new Boss(null, "반 레온", "하드", "일일보스"),
                    new Boss(null, "아카이럼", "노멀", "일일보스"),
                    new Boss(null, "매그너스", "노멀", "일일보스"),
                    new Boss(null, "파풀라투스", "노멀", "일일보스"),

                    // 주간보스
                    new Boss(null, "시그너스", "이지", "주간보스"),
                    new Boss(null, "힐라", "하드", "주간보스"),
                    new Boss(null, "핑크빈", "카오스", "주간보스"),
                    new Boss(null, "시그너스", "노멀", "주간보스"),
                    new Boss(null, "자쿰", "카오스", "주간보스"),
                    new Boss(null, "블러디퀸", "카오스", "주간보스"),
                    new Boss(null, "반반", "카오스", "주간보스"),
                    new Boss(null, "피에르", "카오스", "주간보스"),
                    new Boss(null, "매그너스", "하드", "주간보스"),
                    new Boss(null, "벨룸", "카오스", "주간보스"),
                    new Boss(null, "파풀라투스", "카오스", "주간보스"),
                    new Boss(null, "스우", "노멀", "주간보스"),
                    new Boss(null, "데미안", "노멀", "주간보스"),
                    new Boss(null, "가디언 엔젤 슬라임", "노멀", "주간보스"),
                    new Boss(null, "루시드", "이지", "주간보스"),
                    new Boss(null, "윌", "이지", "주간보스"),
                    new Boss(null, "루시드", "노멀", "주간보스"),
                    new Boss(null, "윌", "노멀", "주간보스"),
                    new Boss(null, "더스크", "노멀", "주간보스"),
                    new Boss(null, "듄켈", "노멀", "주간보스"),
                    new Boss(null, "데미안", "하드", "주간보스"),
                    new Boss(null, "스우", "하드", "주간보스"),
                    new Boss(null, "루시드", "하드", "주간보스"),
                    new Boss(null, "윌", "하드", "주간보스"),
                    new Boss(null, "진 힐라", "노멀", "주간보스"),
                    new Boss(null, "가디언 엔젤 슬라임", "카오스", "주간보스"),
                    new Boss(null, "더스크", "카오스", "주간보스"),
                    new Boss(null, "듄켈", "하드", "주간보스"),
                    new Boss(null, "진 힐라", "하드", "주간보스"),
                    new Boss(null, "선택받은 세렌", "노멀", "주간보스"),
                    new Boss(null, "감시자 칼로스", "이지", "주간보스"),
                    new Boss(null, "카링", "이지", "주간보스"),
                    new Boss(null, "선택받은 세렌", "하드", "주간보스"),
                    new Boss(null, "감시자 칼로스", "노멀", "주간보스"),
                    new Boss(null, "스우", "익스트림", "주간보스"),
                    new Boss(null, "카링", "노멀", "주간보스"),
                    new Boss(null, "감시자 칼로스", "카오스", "주간보스"),
                    new Boss(null, "카링", "하드", "주간보스"),
                    new Boss(null, "선택받은 세렌", "익스트림", "주간보스"),
                    new Boss(null, "감시자 칼로스", "익스트림", "주간보스"),
                    new Boss(null, "카링", "익스트림", "주간보스"),

                    // 월간보스
                    new Boss(null, "검은 마법사", "하드", "월간보스"),
                    new Boss(null, "검은 마법사", "익스트림", "월간보스")
            );

            bossRepository.saveAll(bosses);
        }
    }
}
