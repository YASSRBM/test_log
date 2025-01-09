package sejour;
import sejour.elements.*;

import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SearchHandler {
    public SearchHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    private DataHandler dataHandler = new DataHandler(Path.of("data"));

    public List<Forfait> Search(CritereHotel critereHotel, CritereTrajet critereTrajet, CritereActivite critereActivite, CritereForfait critereForfait) {
        List<Hotel> selectedHotels = SearchHotel(critereHotel);
        List<Trajet> selectedTrajets = SearchTrajet(critereTrajet);
        List<Activite> selectedActivites = SearchActivite(critereActivite);
    
        List<Forfait> selectedForfaits = new ArrayList<>();
    
        for (Hotel hotel : selectedHotels) {
            for (Trajet trajetAller : selectedTrajets) {
                for (Trajet trajetRetour : selectedTrajets) {
                    if (trajetAller.getVilleArrivee().equals(hotel.getVille()) &&
                            trajetRetour.getVilleDepart().equals(hotel.getVille()) &&
                            trajetAller.getVilleDepart().equals(critereForfait.getVilleDepart()) &&
                            trajetRetour.getVilleArrivee().equals(critereForfait.getVilleDepart())) {
    
                        List<Activite> activitesProches = new ArrayList<>();
                        for (Activite activite : selectedActivites) {
                            double distance = GeoUtils.distanceEntre(
                                    GeoUtils.GPS2Coordonnes(hotel.getAdresse()),
                                    GeoUtils.GPS2Coordonnes(activite.getAdresse())
                            );
                            if (distance <= critereActivite.getDistanceMax()) {
                                activitesProches.add(activite);
                            }
                        }
    
                        double prixTotal = hotel.getPrix() * critereForfait.getDureeSejour() +
                                trajetAller.getPrix() + trajetRetour.getPrix() +
                                activitesProches.stream().mapToDouble(Activite::getPrix).sum();
    
                        if (prixTotal <= critereForfait.getPrixMax()) {
                            Forfait forfait = new Forfait();
                            forfait.setHotel(hotel);
                            forfait.setTransportAlle(trajetAller.toString());
                            forfait.setTransportRetour(trajetRetour.toString());
                            forfait.setActivites(activitesProches);
                            selectedForfaits.add(forfait);
                        }
                    }
                }
            }
        }
    
        // Sort hotels based on priority
        if (critereHotel.getPrioriteHotel() == CritereHotel.PrioriteHotel.CLASSEMENT) {
            selectedForfaits.sort((f1, f2) -> Integer.compare(f2.getHotel().getClassement(), f1.getHotel().getClassement()));
        } else {
            selectedForfaits.sort((f1, f2) -> Double.compare(f1.getHotel().getPrix(), f2.getHotel().getPrix()));
        }
    
        // Sort trajets based on priority
        if (critereTrajet.getPrioriteTrajet() == CritereTrajet.PrioriteTrajet.TEMPS) {
            selectedForfaits.sort((f1, f2) -> {
                Instant tempsAller1 = Instant.parse(f1.getTransportAlle().split(",")[3].trim());
                Instant tempsAller2 = Instant.parse(f2.getTransportAlle().split(",")[3].trim());
                return tempsAller1.compareTo(tempsAller2);
            });
        } else {
            selectedForfaits.sort((f1, f2) -> {
                double prixAller1 = Double.parseDouble(f1.getTransportAlle().split(",")[5].trim());
                double prixAller2 = Double.parseDouble(f2.getTransportAlle().split(",")[5].trim());
            return Double.compare(prixAller1, prixAller2);
});
}

return selectedForfaits;

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
