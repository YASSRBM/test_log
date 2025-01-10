package sejour;

import org.junit.jupiter.api.Test;
import sejour.elements.CritereHotel;
import sejour.elements.Hotel;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class SearchHotelTest {
    @Test
    void testSearchHotel() {
        // Mock du dataHandler
        DataHandler dataHandler = mock(DataHandler.class);

        // Données fictives
        Hotel hotel1 = new Hotel("Hotel A", "Ville A", 5, 1200);
        Hotel hotel2 = new Hotel("Hotel B", "Ville B", 3, 120);
        Hotel hotel3 = new Hotel("Hotel C", "Ville C", 4, 500);

        // Configurer le mock
        when(dataHandler.getHotels()).thenReturn(Arrays.asList(hotel1, hotel2, hotel3));

        // Critère de recherche
        CritereHotel critere = new CritereHotel(4, CritereHotel.PrioriteHotel.CLASSEMENT);


        // Instance réelle de la classe à tester
        SearchHandler searchService = new SearchHandler(dataHandler);

        // Appeler la méthode
        List<Hotel> result = searchService.SearchHotel(critere);

        // Vérifications
        assertEquals(2, result.size(), "Devrait retourner 2 hôtels");
        assertEquals(hotel1, result.get(0));
        assertEquals(hotel3, result.get(1));

        // Vérifier que getHotels a été appelé
        verify(dataHandler, times(1)).getHotels();
    }

}
