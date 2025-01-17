package sejour;
import org.junit.jupiter.api.Test;
import sejour.elements.*;

import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class DataHandlerTestIT {


    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void testInitActivitesWithExistingFile() throws Exception {
        Path activitiesFilePath = Paths.get("src/main/java/sejour/data");

        assertTrue(Files.exists(activitiesFilePath), "Le fichier Activities.json pour les tests doit exister");

        DataHandler dataHandler = new DataHandler(activitiesFilePath);

        dataHandler.initActivites();

        List<Activite> activites = dataHandler.getActivites();

        assertNotNull(activites);
        Activite activite1 = activites.get(0);
        assertEquals("Louvre", activite1.getAdresse());
        assertEquals(Activite.Categorie.MUSIQUE, activite1.getCategorie());
        assertEquals(25.0, activite1.getPrix());

        Activite activite2 = activites.get(1);
        assertEquals("Tour Eiffel", activite2.getAdresse());
        assertEquals(Activite.Categorie.TOURISTIQUE, activite2.getCategorie());
        assertEquals(20.0, activite2.getPrix());
    }

    @Test
    public void testInitActivitesFileNotFound() {
        DataHandler dataHandler = new DataHandler(Paths.get("src/test/resources"));

        dataHandler.initActivites();

        assertTrue(dataHandler.getActivites().isEmpty(), "Aucune activité ne doit être ajoutée si le fichier est introuvable");
    }


    @Test 
    public void testInitHotelsWithExistingFile(){
        Path hotelsFilePath = Paths.get("src/main/java/sejour/data");
        DataHandler dataHandler =  new DataHandler(hotelsFilePath);
        dataHandler.initHotels();
        List<Hotel> hotels = dataHandler.getHotels();

        assertNotNull(hotels);

        Hotel hotel1 = hotels.get(0);
        assertEquals("10 Rue de Rivoli", hotel1.getAdresse());
        assertEquals(hotel1.getClassement(), 5);
        assertEquals(250.0, hotel1.getPrix());


        Hotel hotel2 = hotels.get(1);
        assertEquals("55 Avenue des Champs-Élysées", hotel2.getAdresse());
        assertEquals(hotel2.getClassement(), 4);
        assertEquals(180.0, hotel2.getPrix());

    }   

@Test 
    public void testInitHotelsWithNoExistingFile(){

        DataHandler dataHandler = new DataHandler(Paths.get("src/test/resources"));

        dataHandler.initHotels();

        assertTrue(dataHandler.getHotels().isEmpty(), "Aucun hotel ne doit être ajoutée si le fichier est introuvable");
    }


    @Test 
    public void testInitTrajetWithExistingFile(){
        Path hotelsFilePath = Paths.get("src/main/java/sejour/data");
        DataHandler dataHandler =  new DataHandler(hotelsFilePath);
        dataHandler.initTrajets();
        List<Trajet> trajets = dataHandler.getTrajets();

        assertNotNull(trajets);

        Trajet trajet1 = trajets.get(0);
        assertEquals("Paris", trajet1.getVilleDepart());
        assertEquals(trajet1.getVilleArrivee(), "London");
        assertEquals(90.0, trajet1.getPrix());
        assertEquals("TRAIN", trajet1.getModeTransport().toString());



        Trajet trajet2 = trajets.get(1);
        assertEquals("Paris", trajet2.getVilleDepart());
        assertEquals(trajet2.getVilleArrivee(), "London");
        assertEquals(120.0, trajet2.getPrix());
        assertEquals("AVION", trajet2.getModeTransport().toString());

    }  


    @Test 
    public void testInitTrajetWithNoExistingFile(){

        DataHandler dataHandler = new DataHandler(Paths.get("src/test/resources"));

        dataHandler.initTrajets();

        assertTrue(dataHandler.getTrajets().isEmpty(), "Aucun trajet ne doit être ajoutée si le fichier est introuvable");
    }



}
