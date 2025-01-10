 package sejour;

 import okhttp3.OkHttpClient;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.mockito.Mockito;
 import sejour.elements.*;

 import java.text.SimpleDateFormat;
 import java.util.Arrays;
 import java.util.List;

 import static org.junit.jupiter.api.Assertions.*;
 import static org.mockito.Mockito.*;


 public class SearchHandlerTest {

     DataHandler dataHandler;
     GeoUtils geoUtils;

     @BeforeEach
     void setUp() {
         dataHandler = mock(DataHandler.class);
         geoUtils = mock(GeoUtils.class);
     }

     @Test
     void testSearchDeuxActiviteDifferentesMemeDate() throws Exception {

         // Préparer un parseur de dates
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

         // Données fictives
         Hotel hotel = new Hotel("Hotel A", "Lyon", 4, 100.00);
         Trajet trajetAller = new Trajet("Paris", "Lyon", null, null, Trajet.ModeTrajet.TRAIN, 50.00);
         Trajet trajetRetour = new Trajet("Lyon", "Paris", null, null, Trajet.ModeTrajet.TRAIN, 50.00);

         // Activités avec des dates qui se chevauchent
         Activite activite1 = new Activite("123 Rue de Paris", sdf.parse("2025-01-11"), Activite.Categorie.SPORT, 20.00);
         Activite activite2 = new Activite("456 Avenue de Lyon", sdf.parse("2025-01-11"), Activite.Categorie.SPORT, 15.00); // Même date qu'activite1
         Activite activite3 = new Activite("789 Boulevard de Nice", sdf.parse("2025-01-12"), Activite.Categorie.SPORT, 30.00);

         // Configurer le DataHandler
         when(dataHandler.getHotels()).thenReturn(List.of(hotel));
         when(dataHandler.getTrajets()).thenReturn(List.of(trajetAller, trajetRetour));
         when(dataHandler.getActivites()).thenReturn(List.of(activite1, activite2, activite3));


         when(geoUtils.GPS2Coordonnes(hotel.getAdresse()))
                 .thenReturn(new Coordonnes(45.764, 4.8357)); // Lyon
         when(geoUtils.GPS2Coordonnes(anyString()))
                 .thenReturn(new Coordonnes(48.8566, 2.3522)); // Paris par défaut
         when(geoUtils.distanceEntre(Mockito.any(Coordonnes.class), Mockito.any(Coordonnes.class)))
                 .thenReturn(300.0); // Distance fictive

         // Critères de recherche
         CritereHotel critereHotel = new CritereHotel(4, CritereHotel.PrioriteHotel.CLASSEMENT);
         CritereTrajet critereTrajet = new CritereTrajet(Trajet.ModeTrajet.TRAIN, CritereTrajet.PrioriteTrajet.PRIX);
         CritereActivite critereActivite = new CritereActivite(Activite.Categorie.SPORT, 500);
         CritereForfait critereForfait = new CritereForfait("Paris", 3, 400);

         // Instance réelle de la classe à tester
         SearchHandler searchService = new SearchHandler(dataHandler, geoUtils);

         // Appeler la méthode Search
         List<Forfait> result = searchService.Search(critereHotel, critereTrajet, critereActivite, critereForfait);

         // Vérifications
         assertNotNull(result, "La liste des forfaits ne doit pas être nulle");
         assertFalse(result.isEmpty(), "La liste des forfaits ne doit pas être vide");

         // Vérifier qu'aucun forfait ne contient deux activités avec la même date
         for (Forfait forfait : result) {
             List<Activite> activites = forfait.getActivites();
             for (int i = 0; i < activites.size(); i++) {
                 for (int j = i + 1; j < activites.size(); j++) {
                     assertNotEquals(
                             activites.get(i).getDate(),
                             activites.get(j).getDate(),
                             "Deux activités ne doivent pas avoir la même date dans un forfait"
                     );
                 }
             }
         }

         // Vérifier que getHotels, getTrajets, et getActivites ont été appelés
         verify(dataHandler, times(1)).getHotels();
         verify(dataHandler, times(1)).getTrajets();
         verify(dataHandler, times(1)).getActivites();
     }

     @Test
     void testSearchMaximiserActivites() throws Exception {

         // Préparer un parseur de dates
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

         // Données fictives
         Hotel hotel = new Hotel("Hotel A", "Lyon", 4, 100.00); // Prix : 100€/nuit
         Trajet trajetAller = new Trajet("Paris", "Lyon", null, null, Trajet.ModeTrajet.TRAIN, 50.00); // Prix : 50€
         Trajet trajetRetour = new Trajet("Lyon", "Paris", null, null, Trajet.ModeTrajet.TRAIN, 50.00); // Prix : 50€

         // Activités fictives avec des prix différents
         Activite activite1 = new Activite("123 Rue de Paris", sdf.parse("2025-01-11"), null, 20.00); // Prix : 20€
         Activite activite2 = new Activite("456 Avenue de Lyon", sdf.parse("2025-01-12"), null, 30.00); // Prix : 30€
         Activite activite3 = new Activite("789 Boulevard de Nice", sdf.parse("2025-01-13"), null, 50.00); // Prix : 50€
         Activite activite4 = new Activite("321 Place de Lille", sdf.parse("2025-01-14"), null, 70.00); // Prix : 70€

         // Configurer le DataHandler
         when(dataHandler.getHotels()).thenReturn(List.of(hotel));
         when(dataHandler.getTrajets()).thenReturn(List.of(trajetAller, trajetRetour));
         when(dataHandler.getActivites()).thenReturn(List.of(activite1, activite2, activite3, activite4));

         when(geoUtils.GPS2Coordonnes(hotel.getAdresse()))
                 .thenReturn(new Coordonnes(45.764, 4.8357)); // Lyon
         when(geoUtils.GPS2Coordonnes(anyString()))
                 .thenReturn(new Coordonnes(48.8566, 2.3522)); // Paris par défaut
         when(geoUtils.distanceEntre(Mockito.any(Coordonnes.class), Mockito.any(Coordonnes.class)))
                 .thenReturn(300.0); // Distance fictive, toujours dans la limite

         // Critères de recherche
         CritereHotel critereHotel = new CritereHotel(4, CritereHotel.PrioriteHotel.CLASSEMENT);
         CritereTrajet critereTrajet = new CritereTrajet(Trajet.ModeTrajet.TRAIN, CritereTrajet.PrioriteTrajet.PRIX);
         CritereActivite critereActivite = new CritereActivite(Activite.Categorie.SPORT, 500);
         CritereForfait critereForfait = new CritereForfait("Paris", 2, 300);

         // Instance réelle de la classe à tester
         SearchHandler searchService = new SearchHandler(dataHandler, geoUtils);

         // Appeler la méthode Search
         List<Forfait> result = searchService.Search(critereHotel, critereTrajet, critereActivite, critereForfait);

         // Vérifications
         assertNotNull(result, "La liste des forfaits ne doit pas être nulle");
         assertFalse(result.isEmpty(), "La liste des forfaits ne doit pas être vide");

         // Vérifier que le forfait maximise le nombre d'activités
         Forfait selectedForfait = result.get(0); // Supposons qu'un seul forfait est généré
         double totalCost = hotel.getPrix() * critereForfait.getDureeSejour() +
                 trajetAller.getPrix() + trajetRetour.getPrix() +
                 selectedForfait.getActivites().stream().mapToDouble(Activite::getPrix).sum();

         assertTrue(totalCost <= critereForfait.getPrixMax(), "Le coût total du forfait ne doit pas dépasser le budget maximal");
         assertEquals(3, selectedForfait.getActivites().size(), "Le forfait doit contenir le maximum d'activités possibles dans le budget");

         // Vérifier que les activités incluses respectent le budget
         List<Activite> selectedActivities = selectedForfait.getActivites();
         assertTrue(selectedActivities.contains(activite1));
         assertTrue(selectedActivities.contains(activite2));
         assertTrue(selectedActivities.contains(activite3));
         assertFalse(selectedActivities.contains(activite4), "L'activité hors budget ne doit pas être incluse");

         // Vérifier que getHotels, getTrajets, et getActivites ont été appelés
         verify(dataHandler, times(1)).getHotels();
         verify(dataHandler, times(1)).getTrajets();
         verify(dataHandler, times(1)).getActivites();
     }

 }
