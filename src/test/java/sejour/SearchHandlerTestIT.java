package sejour;

import org.junit.jupiter.api.Test;
import sejour.elements.*;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchHandlerTestIT {

    @Test
    void testSearchIntegrationForfaitsValides() throws Exception {
        // Préparer un parseur de dates
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Données fictives dans la base ou le fichier réel
        DataHandler dataHandler = new DataHandler();// Charge les données réelles
        dataHandler.initData();
        SearchHandler searchHandler = new SearchHandler(dataHandler, new GeoUtils());

        // Critères de recherche
        CritereHotel critereHotel = new CritereHotel(3, CritereHotel.PrioriteHotel.CLASSEMENT);
        CritereTrajet critereTrajet = new CritereTrajet(Trajet.ModeTrajet.AVION, CritereTrajet.PrioriteTrajet.PRIX);
        CritereActivite critereActivite = new CritereActivite(Activite.Categorie.TOURISTIQUE, 500.00);
        CritereForfait critereForfait = new CritereForfait("Paris", "London", 3, 1000);

        // Appeler la méthode Search
        List<Forfait> result = searchHandler.Search(critereHotel, critereTrajet, critereActivite, critereForfait);

        // Vérifications
        assertNotNull(result, "La liste des forfaits ne doit pas être nulle");
        assertFalse(result.isEmpty(), "La liste des forfaits ne doit pas être vide");

        // Vérifier qu'au moins un forfait est valide
        Forfait forfait = result.get(0); // Supposons qu'un forfait est généré
        assertNotNull(forfait.getHotel(), "Le forfait doit contenir un hôtel");
        assertNotNull(forfait.getTransportAlle(), "Le forfait doit contenir un trajet aller");
        assertNotNull(forfait.getTransportRetour(), "Le forfait doit contenir un trajet retour");
        assertTrue(forfait.getActivites().size() > 0, "Le forfait doit inclure au moins une activité");
    }

    @Test
    void testSearchIntegrationInteractions() throws Exception {
        // Données fictives dans la base ou le fichier réel
        DataHandler dataHandler = new DataHandler();
        dataHandler.initData();
        GeoUtils geoUtils = new GeoUtils();
        SearchHandler searchHandler = new SearchHandler(dataHandler, geoUtils);

        // Critères de recherche
        CritereHotel critereHotel = new CritereHotel(3, CritereHotel.PrioriteHotel.CLASSEMENT);
        CritereTrajet critereTrajet = new CritereTrajet(Trajet.ModeTrajet.AVION, CritereTrajet.PrioriteTrajet.PRIX);
        CritereActivite critereActivite = new CritereActivite(Activite.Categorie.TOURISTIQUE, 500.00);
        CritereForfait critereForfait = new CritereForfait("Paris", "London", 3, 1000);

        // Appeler la méthode Search
        List<Forfait> result = searchHandler.Search(critereHotel, critereTrajet, critereActivite, critereForfait);

        // Vérifications
        assertNotNull(result, "La liste des forfaits ne doit pas être nulle");
        assertFalse(result.isEmpty(), "La liste des forfaits ne doit pas être vide");

        // Vérifier les composants
        Forfait forfait = result.get(0); // Supposons qu'un forfait est généré
        assertNotNull(geoUtils.GPS2Coordonnes(forfait.getHotel().getAdresse()), "GeoUtils doit résoudre les coordonnées de l'hôtel");
        assertTrue(forfait.getActivites().size() > 0, "Le forfait doit inclure des activités proches");
    }


}
