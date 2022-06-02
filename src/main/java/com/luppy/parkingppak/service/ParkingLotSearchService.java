package com.luppy.parkingppak.service;

import com.luppy.parkingppak.utils.HelperFunctions;
import com.luppy.parkingppak.utils.ResultQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@Slf4j
public class ParkingLotSearchService {

    @Value("${api.elasticsearch.uri}")
    private String elasticSearchUri;

    @Value("${api.elasticsearch.search}")
    private String elasticSearchPrefix;

    public ResultQuery searchFromQuery(String query) throws IOException {
        String body = HelperFunctions.buildMuiltiIndexMatchBody(query);
        return executeHttpRequest(body);
    }

    private ResultQuery executeHttpRequest(String body) throws IOException{
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ResultQuery resultQuery = new ResultQuery();
            HttpPost httpPost = new HttpPost(HelperFunctions.buildSearchUri(elasticSearchUri, "", elasticSearchPrefix));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            try {
                httpPost.setEntity(new StringEntity(body, "UTF-8"));
                HttpResponse response = httpClient.execute(httpPost);
                String message = EntityUtils.toString(response.getEntity());
                JSONObject object = new JSONObject(message);
                System.out.println("object = " + object);
                if (object.getJSONObject("hits").getJSONObject("total").getInt("value") != 0) {
                    resultQuery.setElements(object.getJSONObject("hits").getJSONArray("hits").toString());
                    resultQuery.setNumberOfResults(object.getJSONObject("hits").getJSONObject("total").getInt("value"));
                    resultQuery.setTimeTook((float) ((double) object.getInt("took") / 1000));
                } else {
                    resultQuery.setElements(null);
                    resultQuery.setNumberOfResults(0);
                    resultQuery.setTimeTook((float) ((double) object.getInt("took") / 1000));
                }
            }
            catch (IOException |JSONException e) {
                log.error("Error while connecting to elastic engine :: {}", e.getMessage());
                resultQuery.setNumberOfResults(0);
            }
            return resultQuery;
        }
    }
}
