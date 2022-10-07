package com.luppy.parkingppak.service;

import com.google.gson.Gson;
import com.luppy.parkingppak.domain.GasStation;
import com.luppy.parkingppak.domain.GasStationRepository;
import com.luppy.parkingppak.init.data.GasStationData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class GasStationService implements PublicDataService<GasStation>{

    private final GasStationRepository gasStationRepository;

    @Value("${secret.gas_station}")
    private String token;

    private int xBasePoint = 300400;
    private int yBasePoint = 544800;
    private int distance = 5000;


    @Override
    public void registerData(GasStation gasStation) {

    }

    @Override
    public GasStation searchData(Long id) {
        return null;
    }

    @Override
    public GasStation updateData(Long id, GasStation gasStation) {
        return null;
    }

    @Override
    public void processRegister() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient httpClient = HttpClient.newHttpClient();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                boolean[] gasAndDiesel = {true, false};
                for (boolean isGasoline : gasAndDiesel) {
                    GasStationData.GasStationDataResponse responseGas = getGasStationDataResponse(gson, httpClient, y, x,
                            isGasoline);
                    List<GasStation> gasStations = responseDataToGasStations(responseGas, isGasoline);
                    if (gasStations.size() > 0 ) {
                        gasStationRepository.saveAll(gasStations);
                    }
                }
            }
        }
    }

    private GasStationData.GasStationDataResponse getGasStationDataResponse(Gson gson, HttpClient httpClient, int y,
                                                                            int x, boolean isGasoline) throws IOException,
            InterruptedException {
        String oilCode = isGasoline == true ? "B027" : "D047";
        String uri = "http://www.opinet.co.kr/api/aroundAll.do?code=" + token + "&out=json&x=" + (xBasePoint + distance * x) + "&y" +
                "=" + (yBasePoint + distance * y) +
                "&radius=" + distance +
                "&prodcd=" +
                oilCode +
                "&sort=2";
        HttpRequest requestGasoline= HttpRequest.newBuilder(URI.create(uri)).GET().build();
        HttpResponse<String> httpResponse = httpClient.send(requestGasoline, HttpResponse.BodyHandlers.ofString());
        GasStationData.GasStationDataResponse response = gson.fromJson(httpResponse.body(),
                GasStationData.GasStationDataResponse.class);
        return response;
    }


    public List<GasStation> responseDataToGasStations(GasStationData.GasStationDataResponse gasStationDataResponse,
                                                      boolean isGasoline) throws NullPointerException {
        List<GasStation> gasStations = new ArrayList<>();
        if (gasStationDataResponse.getRESULT().getOIL().size() == 0) {
            return gasStations;
        }
        for (GasStationData.Row row : gasStationDataResponse.getRESULT().getOIL()) {
            try {
                GasStation gasStationFromRepository = gasStationRepository.findByUniqueId(row.getUNI_ID()).get();
                gasStationFromRepository.updatePrice(isGasoline, Integer.parseInt(row.getPRICE()));
            } catch (NoSuchElementException exception) {
                GasStation gasStation = new GasStation();
                gasStation.gasStationMapper(row);
                gasStation.updatePrice(isGasoline, Integer.parseInt(row.getPRICE()));
                gasStations.add(gasStation);
            }

        }
        return gasStations;
    }

    public boolean isEmpty() {
        return gasStationRepository.findAll().size() == 0;
    }

    @Transactional
    public void addGasStationDetail() {

        List<GasStation> gasStations = gasStationRepository.findAll();
        for (GasStation gasStation : gasStations) {
            if (gasStation.getAddress() == null) {
                try {
                    GasStation updatedGasStation = updateGasStationDetail(gasStation);
                    gasStation.setAddress(updatedGasStation.getAddress());
                    gasStation.setCvsExist(updatedGasStation.isCvsExist());
                    gasStation.setCarWash(updatedGasStation.isCarWash());
                    gasStation.setTel(updatedGasStation.getTel());
                } catch (RuntimeException e) {
                    log.info("주유소 주소 등록 에러 발생" + e);
                }
            }
        }

    }

    private GasStation updateGasStationDetail(GasStation gasStation) {
        String uri = "http://www.opinet.co.kr/api/detailById.do?code=" + token + "&out=json&id=" + gasStation.getUniqueId();
        log.info("uri :: " + uri);
        HttpRequest request = HttpRequest.newBuilder(URI.create(uri)).GET().build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            GasStationData.GasStationDetailResponse dataResponse = gson.fromJson(response.body(),
                    GasStationData.GasStationDetailResponse.class);
            if (dataResponse.getRESULT().getOIL().size()==0) {
                return gasStation;
            }
            GasStationData.DetailRow detailRow = dataResponse.getRESULT().getOIL().get(0);
            String cvs_yn = detailRow.getCVS_YN();
            if (cvs_yn != "N") {
                gasStation.setCvsExist(true);
            }
            String car_wash_yn = detailRow.getCAR_WASH_YN();
            if (car_wash_yn != "N") {
                gasStation.setCarWash(true);
            }
            String tel = detailRow.getTEL();
            if (!tel.isBlank()) {
                gasStation.setTel(tel);
            }
            String new_adr = detailRow.getNEW_ADR();
            if (!new_adr.isBlank()) {
                gasStation.setAddress(new_adr);
            }
            return gasStation;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
