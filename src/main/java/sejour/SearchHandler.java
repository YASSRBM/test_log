package sejour;
import sejour.elements.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SearchHandler {
    DataHandler dataHandler = new DataHandler(Path.of("data"));

    public  List<Forfait> Search(CritereHotel critereHotel, CritereTrajet critereTrajet, CritereActivite critereActivite, CritereForfait critereForfait){
        return null;
    }

    public List<Hotel> SearchHotel(CritereHotel critere){
        List<Hotel> tousHotels = dataHandler.getHotels();
        List<Hotel> selectedHotels = new ArrayList<>();
        for(Hotel hotel : tousHotels){
            if(hotel.getClassement() >= critere.getMinClassement()){
                selectedHotels.add(hotel);
            }
        }
        return  selectedHotels;
    }

    public List<Trajet> SearchTrajet(CritereTrajet critere){
        List<Trajet> tousTrajet = dataHandler.getTrajets();
        List<Trajet> selectedTrajet = new ArrayList<>();
        for(Trajet trajet : tousTrajet){
            if(trajet.getModeTransport().equals(critere.getModeTrajet())){
               selectedTrajet.add(trajet);
            }
        }
        return  selectedTrajet;
    }

    public List<Activite> SearchActivite(CritereActivite critere){
        List<Activite> tousActivite = dataHandler.getActivites();
        List<Activite> selectedActivite = new ArrayList<>();
        for(Activite activite : tousActivite){
            if(activite.getCategorie() != critere.getCategorie()){
                selectedActivite.add(activite);
            }
        }
        return  selectedActivite;
    }

}
