package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.ParkingLogRepository;
import com.luppy.parkingppak.domain.ParkingLot;
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
    private final ParkingLogRepository parkingLogRepository;
    private final JwtUtil jwtUtil;

    public RecommendedParkingLotDto recommendParkingLot(String jwt){
        //추천 시스템 실행 및 결과값 반환.
        // Step 0. jwt로 유저의 이용 내역 및 찜 목록 데이터 셋 만들기(전처리).

        String jwtToken = jwt.replace("Bearer ", "");
        List<Travel> travelList = travelRepository.findAllByIdAndGasStation(Long.valueOf(jwtUtil.getAccountId(jwtToken)), null);

        //주차장 전체 데이터로 만들어야함.
        List<ParkingLot> parkingLotList = parkingLogRepository.findAll();

        DataModel dataModel = (DataModel) parkingLotList;

        // Step 1. 유사도 계산.
        ItemSimilarity itemSimilarity = new LogLikelihoodSimilarity(dataModel);

        // Step 2. 추천 시스템 만들기.
        GenericItemBasedRecommender recommender = new GenericBooleanPrefItemBasedRecommender(dataModel, itemSimilarity);


        // Step 3. 추천 받기. (itemId와 가장 유사한 것들 추천 해줌)
        Long itemId = 1L;
        try {
            List<RecommendedItem> recommendedItems = recommender.mostSimilarItems(itemId, 2);
            return (RecommendedParkingLotDto) recommendedItems;

        } catch (TasteException e) {
            e.printStackTrace();
        }

        //recommendedItems 값 반환.
        return null;
    }

    public RecommendedGasStationDto recommendGasStation(String jwt){

        String jwtToken = jwt.replace("Bearer ", "");
        List<Travel> travelList = travelRepository.findAllByIdAndParkingLot(Long.valueOf(jwtUtil.getAccountId(jwtToken)), null);


        //추천 시스템 실행 및 결과값 반환.
        return null;
    }
}
