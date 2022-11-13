package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.dto.IParkingLotDto;
import com.luppy.parkingppak.utils.HelperFunctions;
import com.luppy.parkingppak.utils.ParkingLotResultQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ParkingLotSearchService {

    @Value("${api.elasticsearch.uri}")
    private String elasticSearchUri;

    @Value("${api.elasticsearch.search}")
    private String elasticSearchPrefix;

    public ParkingLotResultQuery searchFromQuery(String query) throws IOException {
        String body = HelperFunctions.parkingLotbuildMuiltiIndexMatchBody(query);
        return executeHttpRequest(body);
    }
    public ParkingLotResultQuery searchLocationFromQuery(int distance, double lat, double lon, double searchAfter,
                                                         String keyword) throws IOException {
        String body = HelperFunctions.searchGeoLocation(distance,lat, lon, searchAfter, keyword);
        return executeHttpRequest(body);
    }

    private ParkingLotResultQuery executeHttpRequest(String body) throws IOException{
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ParkingLotResultQuery parkingLotResultQuery = new ParkingLotResultQuery();
            HttpPost httpPost = new HttpPost(HelperFunctions.buildSearchUri(elasticSearchUri, "parking_lot",
                    elasticSearchPrefix));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            try {
                httpPost.setEntity(new StringEntity(body, "UTF-8"));
                HttpResponse response = httpClient.execute(httpPost);
                String message = EntityUtils.toString(response.getEntity());
                JSONObject object = new JSONObject(message);
                if (object.getJSONObject("hits").getJSONObject("total").getInt("value") != 0) {
                    parkingLotResultQuery.setNumberOfResults(object.getJSONObject("hits").getJSONObject("total").getInt("value"));
                    parkingLotResultQuery.setTimeTook((float) ((double) object.getInt("took") / 1000));
                    parkingLotResultQuery.setData(parseDtoFromObject(object));
                } else {
                    log.info("no search results");
                    parkingLotResultQuery.setNumberOfResults(0);
                    parkingLotResultQuery.setTimeTook((float) ((double) object.getInt("took") / 1000));
                }
            }
            catch (IOException |JSONException e) {
                log.error("Error while connecting to elastic engine :: {}", e.getMessage());
                parkingLotResultQuery.setNumberOfResults(0);
            }
            return parkingLotResultQuery;
        }
    }

    private List<IParkingLotDto> parseDtoFromObject(JSONObject object) {
        JSONArray hitsArray = object.getJSONObject("hits").getJSONArray("hits");
        List<IParkingLotDto> parkingLotDtos = new ArrayList<>();
        for (Object hits : hitsArray) {
            JSONObject jsonObject = new JSONObject(hits.toString());
            JSONObject source = jsonObject.getJSONObject("_source");
            JSONObject geoPoint = source.getJSONObject("location");
            JSONArray sort = jsonObject.getJSONArray("sort");
            IParkingLotDto parkingLotDto = IParkingLotDto.builder()
                    .id(source.getLong("id"))
                    .parkingName(source.getString("parkingName"))
                    .lat(geoPoint.getDouble("lat"))
                    .lon(geoPoint.getDouble("lon"))
                    .address(source.getString("address"))
                    .payYN(source.getBoolean("payYN"))
                    .weekdayBegin(source.getString("weekdayBegin"))
                    .weekdayEnd(source.getString("weekdayEnd"))
                    .weekendBegin(source.getString("weekendBegin"))
                    .weekendEnd(source.getString("weekendEnd"))
                    .holidayBegin(source.getString("holidayBegin"))
                    .holidayEnd(source.getString("holidayEnd"))
                    .rates(source.getInt("rates"))
                    .timeRates(source.getInt("timeRates"))
                    .addRates(source.getInt("addRates"))
                    .addTimeRates(source.getInt("addTimeRates"))
                    .distance(sort.getDouble(0))
                    .build();
            parkingLotDtos.add(parkingLotDto);
        }
        return parkingLotDtos;
    }
}
