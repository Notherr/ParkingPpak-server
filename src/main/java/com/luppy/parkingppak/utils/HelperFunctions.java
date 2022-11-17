package com.luppy.parkingppak.utils;

import org.codehaus.jettison.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    public static String searchGeoLocation(int distance, double lat, double lon, double searchAfter, String keyword,
                                           String searchType) {
        JSONObject queryObject = getQueryString(distance, lat, lon, keyword, searchType);
        if (searchAfter == 0.0d) {
            return queryObject.toString();
        }
        queryObject.put("search_after", List.of(searchAfter));
        return queryObject.toString();
    }

    private static JSONObject getQueryString(int distance, double lat, double lon, String keyword, String searchType) {
        JSONObject queryObject = new JSONObject();
        JSONObject sortObject = new JSONObject();
        sortObject.put("_geo_distance",
                Map.of("location",
                        Map.of("lat", lat, "lon", lon),
                        "order", "asc", "unit", "m", "mode", "min"));
        queryObject.put("sort", sortObject);
        queryObject.put("track_total_hits", true);
        Map<String, Map<String, Object>> geo_distance = Map.of("geo_distance",
                Map.of("distance", distance + "km",
                        "location", Map.of("lat", lat, "lon", lon)
                )
        );
        Map<String, Map<String, Map<String, ? extends Map<? extends Object, Object>>>> default_match = Map.of("bool",
                Map.of("must",
                        Map.of("match_all", Map.of()),
                        "filter", geo_distance
                )
        );
        if (keyword == null) {
            queryObject.put("query", default_match);
        } else {
            List<String> fieldList;
            if (searchType == "gas_station") {
                fieldList = Arrays.asList(GAS_STATION_FIELDS);
            } else {
                fieldList = Arrays.asList(PARKING_LOT_FIELDS);
            }
            queryObject.put("query",
                    Map.of("query_string",
                            Map.of("query", keyword, "fields", fieldList,
                                    "default_operator", "AND")
                    )
            );
        }
        return queryObject;
    }

    public static String buildSearchUri(String elasticSearchUri, String elasticSearchIndex, String elasticSearchSearch) {
        return elasticSearchUri + elasticSearchIndex + "/" + elasticSearchSearch;
    }
}
