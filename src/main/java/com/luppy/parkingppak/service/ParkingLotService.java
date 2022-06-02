package com.luppy.parkingppak.service;

import com.google.gson.Gson;
import com.luppy.parkingppak.domain.ParkingLot;
import com.luppy.parkingppak.domain.ParkingLotRepository;
import com.luppy.parkingppak.init.data.ParkingLotData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingLotService implements PublicDataService{

    private final ParkingLotRepository parkingLotRepository;

    @Value("${secret.seoul_parking}")
    private String token;

    @Override
    public void registerData(Object o) {

    }

    @Override
    public Object searchData(Long id) {
        return null;
    }

    @Override
    public Object updateData(Long id, Object o) {
        return null;
    }

    public boolean isEmpty() {
        return parkingLotRepository.findAll().size() == 0;
    }

    public void processRegister() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient httpClient = HttpClient.newHttpClient();
        HashSet<String> addressSet = new HashSet<>();
        List<ParkingLot> parkingLots = new ArrayList<>();
        for (int cur = 1; cur < 16002; cur = cur+1000) {
            String uriString =
                    "http://openapi.seoul.go.kr:8088/" + token + "/json/GetParkInfo/" + cur + "/" + (cur + 999);
            HttpRequest request = HttpRequest.newBuilder(URI.create(uriString)).GET().build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            ParkingLotData.ParkingLotDataResponse response = gson.fromJson(httpResponse.body(), ParkingLotData.ParkingLotDataResponse.class);
            try {
                List<ParkingLot> responseDataToParkingLots = responseDataToParkingLots(response);
                for (ParkingLot parkingLot : responseDataToParkingLots) {
                    if (!addressSet.contains(parkingLot.getAddress())) {
                        addressSet.add(parkingLot.getAddress());
                        parkingLots.add(parkingLot);
                    }
                }
            } catch (NullPointerException exception) {
                log.error(exception.getMessage());
            }
        }
        parkingLotRepository.saveAll(parkingLots);
    }

    public List<ParkingLot> responseDataToParkingLots(ParkingLotData.ParkingLotDataResponse parkingLotDataResponse) throws NullPointerException{
        if (!parkingLotDataResponse.getGetParkInfo().getRESULT().getCODE().equals("INFO-000")) {
            log.error("ResponseData 의 RESULT CODE 에러" + parkingLotDataResponse.getGetParkInfo().getRESULT().getCODE() +  "\n" + parkingLotDataResponse.getGetParkInfo().getRESULT().getMESSAGE());
            return null;
        }
        List<ParkingLot> parkingLots = new ArrayList<>();
        for (ParkingLotData.Row row : parkingLotDataResponse.getGetParkInfo().getRow()) {
            ParkingLot parkingLot = new ParkingLot();
            parkingLot.parkingLotMapper(row);
            parkingLots.add(parkingLot);
        }
        return parkingLots;
    }
}
