package com.luppy.parkingppak.domain;

import com.luppy.parkingppak.domain.enumclass.CardCompName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    //카드 종류가 정해지면, card 이름으로 등록될 수 있도록 수정.
    Card findByCompName(CardCompName compName);
}
