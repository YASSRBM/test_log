// package sejour;

// import org.junit.jupiter.api.Test;
// import sejour.elements.*;

// import java.text.SimpleDateFormat;
// import java.util.Arrays;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// public class SearchHandlerTest {

//     @Test
//     void testSearchDeuxActivitesDifferentesMemeTemps() throws Exception {
//         // Mock du DataHandler
//         DataHandler dataHandler = mock(DataHandler.class);

//         // Préparer un parseur de dates
//         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//         // Données fictives
//         Hotel hotel = new Hotel("Hotel A", "Lyon", 4, 100.00);
//         Trajet trajet = new Trajet("Paris", "Lyon", sdf.parse("2025-01-10").toInstant(), sdf.parse("2025-01-10T12:00:00Z").toInstant(), "Train", 50.00);
//         Activite activite1 = new Activite("123 Rue de Paris", sdf.parse("2025-01-11"), Activite.Categorie.SPORT, 20.00);
//         Activite activite2 = new Activite("456 Avenue de Lyon", sdf.parse("2025-01-11"), Activite.Categorie.CULTURELLE, 15.00); // Même date qu'activite1
//         Activite activite3 = new Activite("789 Boulevard de Nice", sdf.parse("2025-01-12"), Activite.Categorie.SHOPPING, 30.00);

//         // Configurer le mock
//         when(dataHandler.getHotels()).thenReturn(List.of(hotel));
//         when(dataHandler.getTrajets()).thenReturn(List.of(trajet));
//         when(dataHandler.getActivites()).thenReturn(Arrays.asList(activite1, activite2, activite3));

//         // Critères de recherche
//         CritereHotel critereHotel = new CritereHotel(4, CritereHotel.PrioriteHotel.CLASSEMENT);

//         CritereTrajet critereTrajet = new CritereTrajet("Train", CritereTrajet.PrioriteTrajet.PRIX);

//         CritereActivite critereActivite = new CritereActivite(Activite.Categorie.SPORT, 120);

//         CritereForfait critereForfait = new CritereForfait("Paris", 3, 200);

//         // Instance réelle de la classe à tester
//         SearchHandler searchService = new SearchHandler(dataHandler);

//         // Appeler la méthode Search
//         List<Forfait> result = searchService.Search(critereHotel, critereTrajet, critereActivite, critereForfait);

//         // Vérifications
//         assertNotNull(result, "La liste des forfaits ne doit pas être nulle");
//         assertFalse(result.isEmpty(), "La liste des forfaits ne doit pas être vide");

//         // Vérifier qu'aucun forfait n'a deux activités à la même date
//         for (Forfait forfait : result) {
//             List<Activite> activites = forfait.getActivites();
//             for (int i = 0; i < activites.size(); i++) {
//                 for (int j = i + 1; j < activites.size(); j++) {
//                     assertNotEquals(
//                             activites.get(i).getDate(),
//                             activites.get(j).getDate(),
//                             "Deux activités ne doivent pas avoir la même date dans un forfait"
//                     );
//                 }
//             }
//         }

//         // Vérifier que les méthodes du DataHandler ont été appelées
//         verify(dataHandler, times(1)).getHotels();
//         verify(dataHandler, times(1)).getTrajets();
//         verify(dataHandler, times(1)).getActivites();
//     }

//     @Test
//     void testSearchMaximiserActivites() throws Exception {
//         // Mock du DataHandler
//         DataHandler dataHandler = mock(DataHandler.class);

//         // Préparer un parseur de dates
//         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//         // Données fictives
//         Hotel hotel = new Hotel("Hotel A", "Lyon", 4, 100.00); // Prix : 100
//         Trajet trajet = new Trajet("Paris", "Lyon", sdf.parse("2025-01-10").toInstant(), sdf.parse("2025-01-10T12:00:00Z").toInstant(), "Train", 50.00); // Prix : 50

//         Activite activite1 = new Activite("123 Rue de Paris", sdf.parse("2025-01-11"), Activite.Categorie.SPORT, 20.00); // Prix : 20
//         Activite activite2 = new Activite("456 Avenue de Lyon", sdf.parse("2025-01-12"), Activite.Categorie.CULTURELLE, 15.00); // Prix : 15
//         Activite activite3 = new Activite("789 Boulevard de Nice", sdf.parse("2025-01-13"), Activite.Categorie.SHOPPING, 30.00); // Prix : 30
//         Activite activite4 = new Activite("321 Place de Lille", sdf.parse("2025-01-14"), Activite.Categorie.SPORT, 25.00); // Prix : 25
//         Activite activite5 = new Activite("654 Rue de Nantes", sdf.parse("2025-01-15"), Activite.Categorie.CULTURELLE, 35.00); // Prix : 35

//         // Configurer le mock
//         when(dataHandler.getHotels()).thenReturn(List.of(hotel));
//         when(dataHandler.getTrajets()).thenReturn(List.of(trajet));
//         when(dataHandler.getActivites()).thenReturn(Arrays.asList(activite1, activite2, activite3, activite4, activite5));

//         CritereHotel critereHotel = new CritereHotel(4, CritereHotel.PrioriteHotel.CLASSEMENT);

//         CritereTrajet critereTrajet = new CritereTrajet("Train", CritereTrajet.PrioriteTrajet.PRIX);

//         CritereActivite critereActivite = new CritereActivite(Activite.Categorie.SPORT, 120);

//         CritereForfait critereForfait = new CritereForfait("Paris", 3, 200);

//         // Instance réelle de la classe à tester
//         SearchHandler searchService = new SearchHandler(dataHandler);

//         // Appeler la méthode Search
//         List<Forfait> result = searchService.Search(critereHotel, critereTrajet, critereActivite, critereForfait);

//         // Vérifications
//         assertNotNull(result, "La liste des forfaits ne doit pas être nulle");
//         assertFalse(result.isEmpty(), "La liste des forfaits ne doit pas être vide");

//         // Vérifier que le forfait maximise le nombre d'activités sans dépasser le budget
//         Forfait selectedForfait = result.get(0); // Supposons qu'un seul forfait est généré
//         double totalCost = hotel.getPrix() + trajet.getPrix() +
//                 selectedForfait.getActivites().stream().mapToDouble(Activite::getPrix).sum();

//         assertTrue(totalCost <= critereForfait.getPrixMax(), "Le coût total du forfait ne doit pas dépasser le budget maximal");
//         assertEquals(3, selectedForfait.getActivites().size(), "Le forfait doit contenir le maximum d'activités possibles dans le budget");

//         // Vérifier que getHotels, getTrajets, et getActivites ont été appelés
//         verify(dataHandler, times(1)).getHotels();
//         verify(dataHandler, times(1)).getTrajets();
//         verify(dataHandler, times(1)).getActivites();
//     }

// }
