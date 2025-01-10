package sejour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;
import sejour.elements.*;


public class DataHandlerTest {

    private DataHandler DataHandler;

    @BeforeEach
    public void setUp() {
        DataHandler = new DataHandler();
    }

    @Test
    public void testInitHotels() throws Exception {
        // Arrange: Mock the file content
        String mockJson = "[\n" +
        "    {\"adresse\": \"Hotel Louvre\", \"ville\": \"Paris\", \"classement\": 5, \"prix\": 200.0},\n" +
        "    {\"adresse\": \"Hotel Montmartre\", \"ville\": \"Paris\", \"classement\": 4, \"prix\": 150.0}\n" +
        "]";

        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllBytes(Paths.get("data/Hotels.json")))
                       .thenReturn(mockJson.getBytes());

            // Act
            DataHandler.initHotels();


            //Assert
            List<Hotel> hotels = DataHandler.getHotels();
            assertNotNull(hotels);
            assertEquals(2, hotels.size());

            assertEquals("Hotel Louvre", hotels.get(0).getAdresse());
            assertEquals("Paris", hotels.get(0).getVille());
            assertEquals(5, hotels.get(0).getClassement());
            assertEquals(200.0, hotels.get(0).getPrix());

            assertEquals("Hotel Montmartre", hotels.get(1).getAdresse());
            assertEquals("Paris", hotels.get(1).getVille());
            assertEquals(4, hotels.get(1).getClassement());
            assertEquals(150.0, hotels.get(1).getPrix());
        }
    }

    @Test
    public void testinitActivities(){

        String mockJson = "[\n" +
                "    {\n" +
                "        \"adresse\": \"Louvre Museum, Rue de Rivoli\",\n" +
                "        \"date\": \"2025-03-15\",\n" +
                "        \"categorie\": \"CULTURELLE\",\n" +
                "        \"prix\": 25.0,\n" +
                "        \"ville\": \"Paris\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"adresse\": \"Eiffel Tower, Champ de Mars\",\n" +
                "        \"date\": \"2025-03-16\",\n" +
                "        \"categorie\": \"TOURISTIQUE\",\n" +
                "        \"prix\": 20.0,\n" +
                "        \"ville\": \"Paris\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"adresse\": \"Seine River, Quai de la Tournelle\",\n" +
                "        \"date\": \"2025-03-17\",\n" +
                "        \"categorie\": \"TOURISTIQUE\",\n" +
                "        \"prix\": 30.0,\n" +
                "        \"ville\": \"Paris\"\n" +
                "    }\n" +
                "]";


        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllBytes(Paths.get("data/Activities.json")))
                       .thenReturn(mockJson.getBytes());

            DataHandler.initActivites();
            List<Activite> Activities = DataHandler.getActivites();
            assertNotNull(Activities);
            assertEquals(3, Activities.size());
            assertEquals("Seine River, Quai de la Tournelle", Activities.get(2).getAdresse());
            assertEquals(30.0, Activities.get(2).getPrix());

        }

    }

@Test
    public void testinitTrajets(){
        String mockJson = "[\n" +
            "    {\n" +
            "        \"villeDepart\": \"Paris\",\n" +
            "        \"villeArrivee\": \"London\",\n" +
            "        \"tempsDepart\": \"2025-02-01T08:00:00Z\",\n" +
            "        \"tempsArrivee\": \"2025-02-01T10:30:00Z\",\n" +
            "        \"modeTransport\": \"train\",\n" +
            "        \"prix\": 90.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"villeDepart\": \"Paris\",\n" +
            "        \"villeArrivee\": \"London\",\n" +
            "        \"tempsDepart\": \"2025-02-01T14:00:00Z\",\n" +
            "        \"tempsArrivee\": \"2025-02-01T15:30:00Z\",\n" +
            "        \"modeTransport\": \"avion\",\n" +
            "        \"prix\": 120.0\n" +
            "    }\n" +
            "]";

            try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {
                mockedFiles.when(() -> Files.readAllBytes(Paths.get("data/Trajet.json")))
                           .thenReturn(mockJson.getBytes());

            DataHandler.initTrajets();

            List<Trajet> Trajets= DataHandler.getTrajets();
            assertNotNull(Trajets);
            assertEquals("Paris", Trajets.get(0).getVilleDepart());
            assertEquals("London", Trajets.get(0).getVilleArrivee());
            assertEquals("avion", Trajets.get(1).getModeTransport());

            }
        }

}

