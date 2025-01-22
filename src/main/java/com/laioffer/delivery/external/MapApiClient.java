package com.laioffer.delivery.external;

import org.json.JSONArray;
import org.json.JSONObject;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapApiClient {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("GOOGLE_MAP_API_KEY");


    public static void main(String[] args) {
        String geoJson = "{ 'type': 'FeatureCollection', 'features': [ " +
                "{ 'type': 'Feature', 'geometry': { 'type': 'Point', 'coordinates': [-76.62435835886544, 39.33739795338395] } }, " +
                "{ 'type': 'Feature', 'geometry': { 'type': 'Point', 'coordinates': [-76.61637964537196, 39.32803685974163] } } ]}";

        try {
            String[] coordinates = extractCoordinatesFromGeoJson(geoJson);
            if (coordinates.length == 2) {
                getRouteInfo(coordinates[0], coordinates[1]);
            } else {
                System.out.println("Invalid GeoJSON format.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Extract coordinates from GEOJSON data
    public static String[] extractCoordinatesFromGeoJson(String geoJson) throws Exception {
        JSONObject json = new JSONObject(geoJson);
        JSONArray features = json.getJSONArray("features");

        JSONObject point1 = features.getJSONObject(0).getJSONObject("geometry");
        JSONObject point2 = features.getJSONObject(1).getJSONObject("geometry");

        // Format coordinates as latitude,longitude
        JSONArray coords1 = point1.getJSONArray("coordinates");
        JSONArray coords2 = point2.getJSONArray("coordinates");

        String origin = coords1.getDouble(1) + "," + coords1.getDouble(0);  // lat, lng
        String destination = coords2.getDouble(1) + "," + coords2.getDouble(0);

        return new String[]{origin, destination};
    }

    // Getting driving route info
    public static void getRouteInfo(String origin, String destination) {
        try {
            String urlString = String.format(
                    "https://maps.googleapis.com/maps/api/directions/json?origin=%s&destination=%s&mode=driving&key=%s",
                    origin, destination, API_KEY
            );
            System.out.println("Generated URL: " + urlString);


            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            parseRouteResponse(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Parsing function
    public static void parseRouteResponse(String response) throws Exception {
        JSONObject jsonResponse = new JSONObject(response);

        if (jsonResponse.getString("status").equals("OK")) {
            JSONArray routes = jsonResponse.getJSONArray("routes");
            JSONObject legs = routes.getJSONObject(0).getJSONArray("legs").getJSONObject(0);

            String distance = legs.getJSONObject("distance").getString("text");
            String duration = legs.getJSONObject("duration").getString("text");

            System.out.println("Distance: " + distance);
            System.out.println("Time: " + duration);
        } else {
            System.out.println("Error: " + jsonResponse.getString("status"));
        }
    }
}
