package sejour;

import org.junit.jupiter.api.Test;
import sejour.elements.Activite;
import sejour.elements.CritereActivite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class SearchActiviteTest {
    @Test
    void testSearchActivite() throws ParseException {
        // Mock du dataHandler
        DataHandler dataHandler = mock(DataHandler.class);

        // Données fictives
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Activite activite1 = new Activite(
                "123 Rue de Paris, Lyon",   // adresse
                sdf.parse("2025-01-15"),    // date
                Activite.Categorie.SPORT,            // categorie
                20.00                       // prix
        );

        Activite activite2 = new Activite(
                "456 Avenue de Bordeaux, Toulouse", // adresse
                sdf.parse("2025-02-10"),            // date
                Activite.Categorie.CULTURELLE,                 // categorie
                15.00                              // prix
        );

        Activite activite3 = new Activite(
                "789 Boulevard de Nice, Marseille", // adresse
                sdf.parse("2025-03-05"),            // date
                Activite.Categorie.SPORT,                   // categorie
                25.00                              // prix
        );

        // Configurer le mock
        when(dataHandler.getActivites()).thenReturn(Arrays.asList(activite1, activite2, activite3));

        // Critère de recherche
        CritereActivite critere = new CritereActivite(Activite.Categorie.SPORT, 300);

        // Instance réelle de la classe à tester
        SearchHandler searchService = new SearchHandler(dataHandler);

        // Appeler la méthode
        List<Activite> result = searchService.SearchActivite(critere);

        // Vérifications
        assertEquals(1, result.size(), "Devrait retourner 1 activité");
        assertEquals(activite2, result.get(0));

        // Vérifier que getActivites a été appelé
        verify(dataHandler, times(1)).getActivites();
    }

    @Test
    void testActiviteSelectionWithTieBreaker() throws Exception {
        // Mock du DataHandler
        DataHandler dataHandler = mock(DataHandler.class);

        // Préparer un parseur de dates
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Données fictives
        Activite activite1 = new Activite("123 Rue de Paris", sdf.parse("2025-01-10"), Activite.Categorie.SPORT, 20.00); // Prix 20, date 2025-01-10
        Activite activite2 = new Activite("456 Avenue de Lyon", sdf.parse("2025-01-09"), Activite.Categorie.SPORT, 20.00); // Prix 20, date 2025-01-09
        Activite activite3 = new Activite("789 Boulevard de Nice", sdf.parse("2025-01-15"), Activite.Categorie.CULTURELLE, 15.00); // Prix 15, catégorie CULTURE
        Activite activite4 = new Activite("321 Place de Lille", sdf.parse("2025-01-20"), Activite.Categorie.CULTURELLE, 15.00); // Prix 15, catégorie CULTURE
        Activite activite5 = new Activite("654 Rue de Nantes", sdf.parse("2025-01-11"), Activite.Categorie.SHOPPING, 25.00); // Prix 25

        // Configurer le mock
        when(dataHandler.getActivites()).thenReturn(Arrays.asList(activite1, activite2, activite3, activite4, activite5));

        // Critère de recherche : exclure la catégorie DETENTE
        CritereActivite critere = new CritereActivite(Activite.Categorie.SHOPPING, 120);

        // Instance réelle de la classe à tester
        SearchHandler searchService = new SearchHandler(dataHandler);

        // Appeler la méthode SearchActivite
        List<Activite> result = searchService.SearchActivite(critere);

        // Vérifications : Exclure les activités de la catégorie DETENTE
        assertEquals(4, result.size(), "Devrait retourner 4 activités non DETENTE");
        assertTrue(result.contains(activite1));
        assertTrue(result.contains(activite2));
        assertTrue(result.contains(activite3));
        assertTrue(result.contains(activite4));

        // Départage par critères multiples : prix, puis date
        Activite bestActivite = result.stream()
                .min((a1, a2) -> {
                    if (Double.compare(a1.getPrix(), a2.getPrix()) == 0) {
                        // En cas d'égalité sur le prix, départager par date
                        return a1.getDate().compareTo(a2.getDate());
                    }
                    return Double.compare(a1.getPrix(), a2.getPrix());
                })
                .orElse(null);

        assertEquals(activite3, bestActivite, "L'activité avec le prix le plus bas et la date la plus proche doit être sélectionnée");

        // Vérifier que getActivites a été appelé
        verify(dataHandler, times(1)).getActivites();
    }
}
