package sejour;


import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sejour.GeoUtils;
import sejour.elements.Coordonnes;

import static org.junit.jupiter.api.Assertions.*;

public class GeoUtilsDistanceTest {

    private GeoUtils geoUtils;

    @BeforeEach
    void setUp() {
        geoUtils = new GeoUtils();
    }

    @Test
    public void testDistanceEntrePoints() {
        // Arrange
        Coordonnes bordeaux = new Coordonnes(44.841225, -0.580036);
        Coordonnes rennes = new Coordonnes(48.856613, 2.352222);

        double distance = geoUtils.distanceEntre(bordeaux, rennes);

        // Assert
        assertEquals(499.0, distance, 1.0); // Distance approximative à vol d'oiseau
    }

    @Test
    public void testDistanceEntreSamePoint() {
        // Arrange
        Coordonnes point = new Coordonnes(44.841225, -0.580036);

        // Act
        double distance = geoUtils.distanceEntre(point, point);

        // Assert
        assertEquals(0.0, distance, 0.001, "La distance entre un point et lui-même doit être 0.");
    }
}
