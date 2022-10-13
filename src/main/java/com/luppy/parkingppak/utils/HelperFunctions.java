package com.luppy.parkingppak.utils;

import org.codehaus.jettison.json.JSONArray;

import java.util.Arrays;

public class HelperFunctions {

    public static final String[] PARKING_LOT_FIELDS = {"parkingName", "address"};
    public static final String[] GAS_STATION_FIELDS = {"name"};

    public static String parkingLotbuildMuiltiIndexMatchBody(String query) {
        return "{\n" +
                "\"from\": 0, \"size\": 50, \n" +
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
                "\"from\": 0, \"size\": 50, \n" +
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

    public static String searchGeoLocation(int distance, double lat, double lon, double searchAfter) {
        if (searchAfter == 0.0d) {
            return getQueryString(distance, lat, lon);
        } else {
            return getQueryStringWithSearchAfter(distance, lat, lon, searchAfter);
        }
    }

    private static String getQueryString(int distance, double lat, double lon) {
        return "{\n" +
                "\"sort\" : {\n" +
                "      \"_geo_distance\": {" +
                "           \"location\": {\n" +
                "               \"lat\":" + lat + ",\n" +
                "               \"lon\":" + lon + "\n" +
                "           },\n" +
                "           \"order\": \"asc\",\n" +
                "           \"unit\": \"m\",\n" +
                "           \"mode\": \"min\"\n" +
                "       }\n" +
                "},\n" +
                "\"track_total_hits\": true,\n" +
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

    private static String getQueryStringWithSearchAfter(int distance, double lat, double lon, double searchAfter) {
        return "{\n" +
                "\"search_after\" : [" + searchAfter + "]" + ",\n" +
                "\"sort\" : {\n" +
                "      \"_geo_distance\": {" +
                "           \"location\": {\n" +
                "               \"lat\":" + lat + ",\n" +
                "               \"lon\":" + lon + "\n" +
                "           },\n" +
                "           \"order\": \"asc\",\n" +
                "           \"unit\": \"m\",\n" +
                "           \"mode\": \"min\"\n" +
                "       }\n" +
                "},\n" +
                "\"track_total_hits\": true,\n" +
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
