package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.Travel;
import com.luppy.parkingppak.domain.TravelRepository;
import com.luppy.parkingppak.domain.dto.RecommendedGasStationDto;
import com.luppy.parkingppak.domain.dto.RecommendedParkingLotDto;
import com.luppy.parkingppak.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecommendSystemService {
    private final TravelRepository travelRepository;
    private final JwtUtil jwtUtil;

    public RecommendedParkingLotDto recommendParkingLot(String jwt){
        //추천 시스템 실행 및 결과값 반환.
        // Step 1. jwt로 유저의 이용 내역 및 찜 목록 데이터 셋 만들기.

        String jwtToken = jwt.replace("Bearer ", "");
        List<Travel> travelList = travelRepository.findAllByIdAndGasStation(Long.valueOf(jwtUtil.getAccountId(jwtToken)), null);


        DataModel dataModel = null;

        // Step 2. 데이터셋을 추천 시시스템 모델에 넣고 결과 값 얻기.
        ItemSimilarity itemSimilarity = new LogLikelihoodSimilarity(dataModel);
        GenericItemBasedRecommender recommender = new GenericBooleanPrefItemBasedRecommender(dataModel, itemSimilarity);

        Long itemId = 1L;
        try {
            List<RecommendedItem> recommendedItems = recommender.mostSimilarItems(itemId, 10);
        } catch (TasteException e) {
            e.printStackTrace();
        }

        //recommendedItems 값 반환.
        return null;
    }

    public RecommendedGasStationDto recommendGasStation(String jwt){

        String jwtToken = jwt.replace("Bearer ", "");
        List<Travel> travelList = travelRepository.findAllByIdAndParkingLot(Long.valueOf(jwtUtil.getAccountId(jwtToken)), null);


        //추천 시스템 실행 및 결과값 반화.
        return null;
    }
}
