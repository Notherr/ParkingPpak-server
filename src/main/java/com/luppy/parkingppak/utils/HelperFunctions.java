package com.luppy.parkingppak.utils;

import org.codehaus.jettison.json.JSONArray;

import java.util.Arrays;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;

public class HelperFunctions {

    public static final String[] PARKING_LOT_FIELDS = {"parkingName", "address"};
    public static final String[] GAS_STATION_FIELDS = {"name"};

    public static String parkingLotbuildMuiltiIndexMatchBody(String query) {
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

    public static String gasStationbuildMuiltiIndexMatchBody(String query) {
        return "{\n" +
                "\"from\": 0, \n" +
                "\"track_total_hits\": true,\n" +
                "\"sort\" : {\n" +
                "      \"id\": {\"order\": \"asc\"}\n" +
                "      },\n" +
                "  \"query\": {\n" +
                "    \"query_string\" : {\n" +
                "      \"query\":      \"*" + query + "*\",\n" +
                "      \"fields\":     " + new JSONArray(Arrays.asList(GAS_STATION_FIELDS)) + ",\n" +
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

    public static String searchGeoLocation(int distance, double lat, double lon) {
        return "{\n" +
                "\"query\": {\n" +
                "      \"geo_distance\": {\n" +
                "       \"distance\":      \"" + distance + "km\",\n" +
                "       \"location\": {\n" +
                "        \"lat\":" + lat + ",\n" +
                "        \"lon\":" + lon + "\n" +
                "     }\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }
    public static String buildSearchUri(String elasticSearchUri, String elasticSearchIndex, String elasticSearchSearch) {
        return elasticSearchUri + elasticSearchIndex + "/" + elasticSearchSearch;
    }
}
