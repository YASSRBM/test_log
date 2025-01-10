 package sejour;

 import org.junit.jupiter.api.Test;
 import sejour.elements.CritereTrajet;
 import sejour.elements.Trajet;

 import javax.xml.crypto.Data;
 import java.time.Instant;
 import java.util.Arrays;
 import java.util.List;

 import static org.junit.jupiter.api.Assertions.assertEquals;
 import static org.junit.jupiter.api.Assertions.assertTrue;
 import static org.mockito.Mockito.*;
 import static org.mockito.Mockito.times;

 public class SearchTrajetTest {

     private final DataHandler dataHandler = mock(DataHandler.class);
     @Test
     void testSearchTrajet() {

         // Données fictives
         Trajet trajet1 = new Trajet(
                 "Paris",               // villeDepart
                 "Lyon",                // villeArrivee
                 Instant.parse("2025-01-10T08:00:00Z"),  // tempsDepart
                 Instant.parse("2025-01-10T12:00:00Z"),  // tempsArrivee
                 Trajet.ModeTrajet.TRAIN,               // modeTransport
                 45.50                  // prix
         );

         Trajet trajet2 = new Trajet(
                 "Bordeaux",            // villeDepart
                 "Toulouse",            // villeArrivee
                 Instant.parse("2025-01-11T09:30:00Z"),  // tempsDepart
                 Instant.parse("2025-01-11T11:30:00Z"),  // tempsArrivee
                 Trajet.ModeTrajet.BUS,                 // modeTransport
                 20.00                  // prix
         );

         Trajet trajet3 = new Trajet(
                 "Marseille",           // villeDepart
                 "Nice",                // villeArrivee
                 Instant.parse("2025-01-12T14:00:00Z"),  // tempsDepart
                 Instant.parse("2025-01-12T16:00:00Z"),  // tempsArrivee
                 Trajet.ModeTrajet.VOITURE,             // modeTransport
                 30.00                  // prix
         );

         // Configurer le mock
         when(dataHandler.getTrajets()).thenReturn(Arrays.asList(trajet1, trajet2, trajet3));

         // Critère de recherche
         CritereTrajet critere = new CritereTrajet(Trajet.ModeTrajet.TRAIN, CritereTrajet.PrioriteTrajet.PRIX);


         // Instance réelle de la classe à tester
         SearchHandler searchService = new SearchHandler(dataHandler);

         // Appeler la méthode
         List<Trajet> result = searchService.SearchTrajet(critere);

         // Vérifications
         assertEquals(1, result.size(), "Devrait retourner 1 trajet");
         assertEquals(trajet1, result.get(0));

         // Vérifier que getTrajets a été appelé
         verify(dataHandler, times(1)).getTrajets();
     }
 }
