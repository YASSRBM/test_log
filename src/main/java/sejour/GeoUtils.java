package sejour;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import sejour.elements.Coordonnes;

import java.io.IOException;

public class GeoUtils {
    public static final double EARTH_RADIUS = 6371;
    public static String BASE_URL = "https://geocode.maps.co/search?q="; 

    public static Coordonnes GPS2Coordonnes(String adresse) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL + adresse) 
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response " + response);
            }

            String responseStr = response.body().string();

            JSONObject jObject = new JSONObject(responseStr.substring(1, responseStr.length() - 1));
            double longitude = jObject.getDouble("lon");
            double latitude = jObject.getDouble("lat");
            return new Coordonnes(latitude, longitude);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double distanceEntre(Coordonnes pt1, Coordonnes pt2) {
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
