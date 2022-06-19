package com.luppy.parkingppak.domain;

import com.luppy.parkingppak.domain.enumclass.CardCompName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Card findByCompName(CardCompName compName);
    Card findByName(String cardName);
}
