package com.sokolov.touristtelegrambot.repository;

import com.sokolov.touristtelegrambot.entity.CityInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityInfoRepository extends JpaRepository<CityInfo, Long> {
    CityInfo findByName(String name);

    void deleteByName(String name);
}
