package com.maple.maple_boss_now.repository;

import com.maple.maple_boss_now.entity.Boss;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BossRepository extends JpaRepository<Boss, Long> {
    List<Boss> findByCategory(String category);

    List<Boss> findByName(String name);
    //save
    //findById
    //findAll
}
