package sejour;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import sejour.elements.Coordonnes;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GeoUtils {
    public static final double EARTH_RADIUS = 6371;
    public static String BASE_URL = "https://geocode.maps.co/search?q="; 

    public static Coordonnes GPS2Coordonnes(String adresse) {
        OkHttpClient client = new OkHttpClient();
        String apiKey = "677fd8e19d2ed057306843cjxc9324a";
    
        // Remove any leading/trailing whitespace from the address
        adresse = adresse.trim();
    
        // Replace commas with spaces in the address
        adresse = adresse.replace(",", " ");
    
        // Encode the address to handle special characters
        String encodedAdresse = URLEncoder.encode(adresse, StandardCharsets.UTF_8);
    
        // Build the request URL with the encoded address and API key
        String url = String.format("https://geocode.maps.co/search?q=%s&api_key=%s", encodedAdresse, apiKey);
    
        Request request = new Request.Builder()
                .url(url)
                .build();
    
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() ) {
                throw new IOException("Unexpected response " + response);
            }
    
            String responseStr = response.body().string();
            JSONArray jsonArray = new JSONArray(responseStr);
    
            if (jsonArray.length() > 0) {
                JSONObject jObject = jsonArray.getJSONObject(0);
                double latitude = jObject.getDouble("lat");
                double longitude = jObject.getDouble("lon");
                return new Coordonnes(latitude, longitude);
            } else {
                throw new IOException("No coordinates found for the given address: " + adresse);
            }
        } catch (IOException e) {
            return null;
        }
    
    }

    static public double distanceEntre(Coordonnes pt1, Coordonnes pt2) {
        if (pt1 == null || pt2 == null) {
            throw new NullPointerException("One of the coordinates is null");
        }
    
        // Convert degrees to radians
        double lat1Rad = Math.toRadians(pt1.getLatitude());
        double lon1Rad = Math.toRadians(pt1.getLongitude());
        double lat2Rad = Math.toRadians(pt2.getLatitude());
        double lon2Rad = Math.toRadians(pt2.getLongitude());
    
        // Differences in latitude and longitude
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;
    
        // Haversine formula
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
    
        // Distance in km
        return EARTH_RADIUS * c;
    }
    
}
