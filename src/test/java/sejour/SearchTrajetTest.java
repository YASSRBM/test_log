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
                "Train",               // modeTransport
                45.50                  // prix
        );

        Trajet trajet2 = new Trajet(
                "Bordeaux",            // villeDepart
                "Toulouse",            // villeArrivee
                Instant.parse("2025-01-11T09:30:00Z"),  // tempsDepart
                Instant.parse("2025-01-11T11:30:00Z"),  // tempsArrivee
                "Bus",                 // modeTransport
                20.00                  // prix
        );

        Trajet trajet3 = new Trajet(
                "Marseille",           // villeDepart
                "Nice",                // villeArrivee
                Instant.parse("2025-01-12T14:00:00Z"),  // tempsDepart
                Instant.parse("2025-01-12T16:00:00Z"),  // tempsArrivee
                "Voiture",             // modeTransport
                30.00                  // prix
        );

        // Configurer le mock
        when(dataHandler.getTrajets()).thenReturn(Arrays.asList(trajet1, trajet2, trajet3));

        // Critère de recherche
        CritereTrajet critere = new CritereTrajet("Train", CritereTrajet.PrioriteTrajet.PRIX);


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

    @Test
    void testTrajetEgaliteOption() {
        // Mock du DataHandler
        DataHandler dataHandler = mock(DataHandler.class);

        // Données fictives
        Trajet trajet1 = new Trajet("Paris", "Lyon", Instant.parse("2025-01-10T08:00:00Z"), Instant.parse("2025-01-10T12:00:00Z"), "Train", 50.00); // 4h
        Trajet trajet2 = new Trajet("Paris", "Lyon", Instant.parse("2025-01-10T09:00:00Z"), Instant.parse("2025-01-10T12:30:00Z"), "Train", 50.00); // 3h30
        Trajet trajet3 = new Trajet("Paris", "Lyon", Instant.parse("2025-01-10T08:00:00Z"), Instant.parse("2025-01-10T13:00:00Z"), "Train", 55.00); // 5h

        // Configurer le mock
        when(dataHandler.getTrajets()).thenReturn(Arrays.asList(trajet1, trajet2, trajet3));

        // Critère de recherche : mode de transport = "Train" et départage selon le prix, puis la durée
        CritereTrajet critere = new CritereTrajet("Train", CritereTrajet.PrioriteTrajet.PRIX);

        // Instance réelle de la classe à tester
        SearchHandler searchService = new SearchHandler(dataHandler);

        // Appeler la méthode SearchTrajet
        List<Trajet> result = searchService.SearchTrajet(critere);

        // Vérifications
        assertEquals(2, result.size(), "Devrait retourner les 2 trajets les moins chers (trajet1 et trajet2)");
        assertTrue(result.contains(trajet1));
        assertTrue(result.contains(trajet2));

        // Départage par durée
        Trajet bestTrajet = result.stream()
                .min((t1, t2) -> {
                    if (Double.compare(t1.getPrix(), t2.getPrix()) == 0) {
                        // En cas d'égalité sur le prix, départager par durée
                        long duree1 = t1.getTempsArrivee().toEpochMilli() - t1.getTempsDepart().toEpochMilli();
                        long duree2 = t2.getTempsArrivee().toEpochMilli() - t2.getTempsDepart().toEpochMilli();
                        return Long.compare(duree1, duree2);
                    }
                    return Double.compare(t1.getPrix(), t2.getPrix());
                })
                .orElse(null);

        assertEquals(trajet2, bestTrajet, "Le trajet avec la durée la plus courte doit être sélectionné en cas d'égalité sur le prix");

        // Vérifier que getTrajets a été appelé
        verify(dataHandler, times(1)).getTrajets();
    }
}
