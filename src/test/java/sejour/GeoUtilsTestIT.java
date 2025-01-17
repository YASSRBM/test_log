package sejour;
import java.io.IOException;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sejour.GeoUtils;
import sejour.elements.Coordonnes;

import static org.junit.jupiter.api.Assertions.*;

class GeoUtilsTestIT {

    private GeoUtils geoUtils;


    @BeforeEach
    void setUp() {
        geoUtils = new GeoUtils();
    }

    @Test
    void testIntegration_distanceEntreAdresses() throws IOException {
        Coordonnes coordPlaceDeLaBourse = geoUtils.GPS2Coordonnes("Place de la Bourse, Bordeaux, France");
        Coordonnes coordCiteDuVin = geoUtils.GPS2Coordonnes("134 Quai de Bacalan, Bordeaux, France");
    
        assertNotNull(coordPlaceDeLaBourse, "Les coordonnées de Place de la Bourse ne doivent pas être nulles");
        assertNotNull(coordCiteDuVin, "Les coordonnées de la Cité du Vin ne doivent pas être nulles");
    
        double distance = geoUtils.distanceEntre(coordPlaceDeLaBourse, coordCiteDuVin);
    
        assertEquals(2.2, distance, 0.1, "Distance incorrecte entre Place de la Bourse et Cité du Vin");
    }

    @Test
    void testIntegration_distanceEntreSameAdresses() throws IOException {
        Coordonnes coordCiteDuVin_0 = geoUtils.GPS2Coordonnes("134 Quai de Bacalan, Bordeaux, France");
        ;
        Coordonnes coordCiteDuVin = geoUtils.GPS2Coordonnes("134 Quai de Bacalan, Bordeaux, France");
    
        assertNotNull(coordCiteDuVin_0, "Les coordonnées de Place de la Bourse ne doivent pas être nulles");
        assertNotNull(coordCiteDuVin, "Les coordonnées de la Cité du Vin ne doivent pas être nulles");
    
        double distance = geoUtils.distanceEntre(coordCiteDuVin_0, coordCiteDuVin);
    
        assertEquals(0, distance, 0.1, "Distance incorrecte entre Place de la Bourse et Cité du Vin");
    }

    @Test
    void testIntegration_distanceEntreVillesFrancaises() throws IOException {
        Coordonnes coordBordeaux = geoUtils.GPS2Coordonnes("Bordeaux, France");
        Coordonnes coordRennes = geoUtils.GPS2Coordonnes("Rennes, France");

        assertNotNull(coordBordeaux, "Les coordonnées de Bordeaux ne doivent pas être nulles");
        assertNotNull(coordRennes, "Les coordonnées de Marseille ne doivent pas être nulles");

        double distance = geoUtils.distanceEntre(coordBordeaux, coordRennes);

        assertEquals(373.77, distance, 50.0, "Distance incorrecte entre Bordeaux et Rennes");
    }

    @Test
    void testGPS2Coordonnes() {
        // Simulate an invalid address to cause failure in geocoding
        String invalidAdresse = "invalid address";
        Coordonnes result = geoUtils.GPS2Coordonnes(invalidAdresse);
    
        // Assert that the result is null since no coordinates were found
        assertNull(result);
    }
    @Test
    void testIntegration_adresseVide() {
        // Étape 1 : Tester avec une adresse vide
        Coordonnes result = geoUtils.GPS2Coordonnes("");
    
        // Étape 2 : Vérifier que le résultat est null
        assertNull(result, "Le résultat ne devrait pas être null pour une adresse vide");
    }

    @Test
    void testIntegration_distanceAvecCoordonneeNulle() {
        // Étape 1 : Une coordonnée valide et une coordonnée nulle
        Coordonnes coordValide = new Coordonnes(44.837789, -0.57918);
        Coordonnes coordNulle = null;
    
        Exception exception = assertThrows(NullPointerException.class, () -> geoUtils.distanceEntre(coordValide, coordNulle));
    
        assertEquals("One of the coordinates is null", exception.getMessage(), "Message d'erreur inattendu pour une coordonnée nulle");
    }
    


}
