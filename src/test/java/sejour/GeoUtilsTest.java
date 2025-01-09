package sejour;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sejour.GeoUtils;
import sejour.elements.Coordonnes;

import static org.junit.jupiter.api.Assertions.*;

public class GeoUtilsTest {
    private MockWebServer mockWebServer;

    @BeforeEach
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterEach
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void testGPS2CoordonnesValid() {
        // Arrange
        String mockResponse = "[{\"lat\":44.841225,\"lon\":-0.580036}]";
        mockWebServer.enqueue(new MockResponse().setBody(mockResponse).setResponseCode(200));
        String adresse = "Place de la Bourse, Bordeaux, France";

        GeoUtils.BASE_URL = mockWebServer.url("/").toString(); // Utilisation du mock
        Coordonnes coordonnes = GeoUtils.GPS2Coordonnes(adresse);

        // Assert
        assertNotNull(coordonnes);
        assertEquals(44.841225, coordonnes.getLatitude(), 0.0001);
        assertEquals(-0.580036, coordonnes.getLongitude(), 0.0001);
    }

    @Test
    public void testGPS2CoordonnesInvalid() {
        // Arrange
        mockWebServer.enqueue(new MockResponse().setResponseCode(500)); // Réponse invalide
        String adresse = "Adresse invalide";

        GeoUtils.BASE_URL = mockWebServer.url("/").toString(); // Utilisation du mock
        Coordonnes coordonnes = GeoUtils.GPS2Coordonnes(adresse);

        // Assert
        assertNull(coordonnes, "Les coordonnées doivent être nulles pour une réponse invalide.");
    }

    @Test
    public void testGPS2CoordonnesEmpty() {
        // Arrange
        String adresse = "";

        // Act
        Coordonnes coordonnes = GeoUtils.GPS2Coordonnes(adresse);

        // Assert
        assertNull(coordonnes, "Les coordonnées doivent être nulles pour une adresse vide.");
    }
}
