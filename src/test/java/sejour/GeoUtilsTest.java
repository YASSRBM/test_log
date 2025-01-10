package sejour;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sejour.elements.Coordonnes;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GeoUtilsTest {

    private OkHttpClient mockClient;
    private GeoUtils geoUtils;


    @BeforeEach
    void setUp() {
        mockClient = Mockito.mock(OkHttpClient.class);
        geoUtils = new GeoUtils();
    }

    @Test
    void testGPS2Coordonnes_validAddress() throws IOException {
        // Mock response
        String mockResponseJson = "[{\"lat\": 48.8588897, \"lon\": 2.3200410217200766}]"; // Paris coordinates
        Response mockResponse = new Response.Builder()
                .request(new Request.Builder().url("https://mockurl").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create(mockResponseJson, MediaType.parse("application/json")))
                .build();

        // Mock the client behavior
        Call mockCall = Mockito.mock(Call.class);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);

        // Test the method
        GeoUtils.BASE_URL = "https://mockurl"; // Ensure no real API calls
        Coordonnes coordonnes = geoUtils.GPS2Coordonnes("Paris");

        // Assert the results
        assertNotNull(coordonnes);
        assertEquals(48.8588897, coordonnes.getLatitude());
        assertEquals(2.3200410217200766, coordonnes.getLongitude());
    }

    @Test
    void testGPS2Coordonnes_invalidAddress() throws IOException {
        // Mock an empty response
        String mockResponseJson = "[]";
        Response mockResponse = new Response.Builder()
                .request(new Request.Builder().url("https://mockurl").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create(mockResponseJson, MediaType.parse("application/json")))
                .build();

        // Mock the client behavior
        Call mockCall = Mockito.mock(Call.class);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);

        // Test the method
        GeoUtils.BASE_URL = "https://mockurl"; // Ensure no real API calls
        Coordonnes coordonnes = geoUtils.GPS2Coordonnes("Invalid Address");

        // Assert the results
        assertNull(coordonnes);
    }

/*     @Test
    void testGPS2Coordonnes_apiError() throws IOException {
        // Mock a failed response
        Response mockResponse = new Response.Builder()
                .request(new Request.Builder().url("https://mockurl").build())
                .protocol(Protocol.HTTP_1_1)
                .code(500)
                .message("Internal Server Error")
                .body(ResponseBody.create("", MediaType.parse("application/json")))
                .build();

        // Mock the client behavior
        Call mockCall = Mockito.mock(Call.class);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);

        // Test the method
        GeoUtils.BASE_URL = "https://mockurl"; // Ensure no real API calls
        Coordonnes coordonnes = GeoUtils.GPS2Coordonnes("Paris");

        assertNull(coordonnes);
    } */
}
