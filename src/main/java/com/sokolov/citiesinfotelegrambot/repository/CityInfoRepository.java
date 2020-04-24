package com.sokolov.citiesinfotelegrambot.repository;

import com.sokolov.citiesinfotelegrambot.model.entity.CityInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityInfoRepository extends JpaRepository<CityInfo, Long> {
    //TODO: resolve issue with russian values stored in db
    CityInfo findByName(String name);
}
