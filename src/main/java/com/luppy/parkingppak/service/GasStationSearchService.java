package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.dto.IGasStationDto;
import com.luppy.parkingppak.utils.GasStationResultQuery;
import com.luppy.parkingppak.utils.HelperFunctions;
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
public class GasStationSearchService {

    @Value("${api.elasticsearch.uri}")
    private String elasticSearchUri;

    @Value("${api.elasticsearch.search}")
    private String elasticSearchPrefix;

    public GasStationResultQuery searchFromQuery(String query) throws IOException {
        String body = HelperFunctions.gasStationbuildMuiltiIndexMatchBody(query);
        return executeHttpRequest(body);
    }
    public GasStationResultQuery searchLocationFromQuery(int distance, double lat, double lon, double searchAfter,
                                                         String keyword) throws IOException {
        String body = HelperFunctions.searchGeoLocation(distance,lat, lon, searchAfter, keyword);
        return executeHttpRequest(body);
    }

    private GasStationResultQuery executeHttpRequest(String body) throws IOException{
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            GasStationResultQuery gasStationResultQuery = new GasStationResultQuery();
            HttpPost httpPost = new HttpPost(HelperFunctions.buildSearchUri(elasticSearchUri, "gas_station",
                    elasticSearchPrefix));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            try {
                httpPost.setEntity(new StringEntity(body, "UTF-8"));
                HttpResponse response = httpClient.execute(httpPost);
                String message = EntityUtils.toString(response.getEntity());
                JSONObject object = new JSONObject(message);
                if (object.getJSONObject("hits").getJSONObject("total").getInt("value") != 0) {
                    gasStationResultQuery.setNumberOfResults(object.getJSONObject("hits").getJSONObject("total").getInt("value"));
                    gasStationResultQuery.setTimeTook((float) ((double) object.getInt("took") / 1000));
                    gasStationResultQuery.setData(parseDtoFromObject(object));
                } else {
                    List<IGasStationDto> gasStationDtos = parseDtoFromObject(object);
                    if (gasStationDtos.size()!= 0) {
                        gasStationResultQuery.setNumberOfResults(0);
                        gasStationResultQuery.setTimeTook((float) ((double) object.getInt("took") / 1000));
                        gasStationResultQuery.setData((gasStationDtos));
                    } else {
                        log.info("no search results");
                        gasStationResultQuery.setNumberOfResults(0);
                        gasStationResultQuery.setTimeTook((float) ((double) object.getInt("took") / 1000));
                    }
                }
            }
            catch (IOException | JSONException e) {
                log.error("Error while connecting to elastic engine :: {}", e.getMessage());
                gasStationResultQuery.setNumberOfResults(0);
            }
            return gasStationResultQuery;
        }
    }

    private List<IGasStationDto> parseDtoFromObject(JSONObject object) {
        JSONArray hitsArray = object.getJSONObject("hits").getJSONArray("hits");
        List<IGasStationDto> gasStationDtos = new ArrayList<>();
        for (Object hits : hitsArray) {
            JSONObject jsonObject = new JSONObject(hits.toString());
            JSONObject source = jsonObject.getJSONObject("_source");
            JSONObject geoPoint = source.getJSONObject("location");
            JSONArray sort = jsonObject.getJSONArray("sort");
            IGasStationDto gasStationDto = IGasStationDto.builder()
                    .id(source.getLong("id"))
                    .compName(source.getString("compName"))
                    .name(source.getString("name"))
                    .uniqueId(source.getString("uniqueId"))
                    .lat(geoPoint.getDouble("lat"))
                    .lon(geoPoint.getDouble("lon"))
                    .gasolinePrice(source.getInt("gasolinePrice"))
                    .dieselPrice(source.getInt("dieselPrice"))
                    .address(source.getString("address"))
                    .carWash(source.getBoolean("carWash"))
                    .cvsExist(source.getBoolean("cvsExist"))
                    .tel(source.getString("tel"))
                    .distance(sort.getDouble(0))
                    .build();

            gasStationDtos.add(gasStationDto);
        }
        return gasStationDtos;
    }
}
