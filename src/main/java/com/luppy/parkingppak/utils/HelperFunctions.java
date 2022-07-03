package com.luppy.parkingppak.utils;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

public class HelperFunctions {

    public static final String[] PARKING_LOT_FIELDS = {"parkingName", "address"};

    public static String buildMuiltiIndexMatchBody(String query) {
        return "{\n" +
                "\"from\": 0, \n" +
                "\"track_total_hits\": true,\n" +
                "\"sort\" : {\n" +
                "      \"id\": {\"order\": \"asc\"}\n" +
                "      },\n" +
                "  \"query\": {\n" +
                "    \"query_string\" : {\n" +
                "      \"query\":      \"*" + query + "*\",\n" +
                "      \"fields\":     " + new JSONArray(Arrays.asList(PARKING_LOT_FIELDS)) + ",\n" +
                "      \"default_operator\": \"AND\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"highlight\": {\n" +
                "    \"fields\": {\n" +
                "      \"*\": {}\n" +
                "    },\n" +
                "    \"require_field_match\": true\n" +
                " }\n" +
                "}";
    }

    public static String buildSearchUri(String elasticSearchUri, String elasticSearchIndex, String elasticSearchSearch) {
        return elasticSearchUri + elasticSearchIndex + elasticSearchSearch;
    }
}
