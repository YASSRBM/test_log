package sejour;
import sejour.elements.*;

import java.nio.file.Path;
import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;

public class SearchHandler {
    private static final Logger logger = Logger.getLogger(SearchHandler.class.getName());
    public SearchHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public SearchHandler(DataHandler dataHandler, GeoUtils geoUtils) {
        this.geoUtils = geoUtils;
        this.dataHandler = dataHandler;
    }

    public SearchHandler() {

    }

    public DataHandler dataHandler = new DataHandler(null);
    private GeoUtils geoUtils = new GeoUtils();

    public List<Forfait> Search(CritereHotel critereHotel, CritereTrajet critereTrajet, CritereActivite critereActivite, CritereForfait critereForfait) {
        // logger.info("Starting search for forfaits...");
        List<Hotel> selectedHotels = SearchHotel(critereHotel);
        // logger.info("Selected hotels: " + selectedHotels.size());
        List<Trajet> selectedTrajets = SearchTrajet(critereTrajet);
        // logger.info("Selected trajets: " + selectedTrajets.size());
        List<Activite> selectedActivites = SearchActivite(critereActivite);
        // logger.info("Selected activities: " + selectedActivites.size());
    
        List<Forfait> selectedForfaits = new ArrayList<>();
    
        for (Hotel hotel : selectedHotels) {
            logger.info("Processing hotel: " + hotel.getAdresse());
            for (Trajet trajetAller : selectedTrajets) {
                logger.info("Processing trajet aller: " + trajetAller.getVilleDepart() + " -> " + trajetAller.getVilleArrivee());
                for (Trajet trajetRetour : selectedTrajets) {
                    logger.info("Processing trajet retour: " + trajetRetour.getVilleDepart() + " -> " + trajetRetour.getVilleArrivee());
                    if (trajetAller.getVilleArrivee().equals(hotel.getVille()) &&
                            trajetRetour.getVilleDepart().equals(hotel.getVille()) &&
                            trajetAller.getVilleDepart().equals(critereForfait.getVilleDepart()) &&
                            trajetRetour.getVilleArrivee().equals(critereForfait.getVilleDepart())) {
                        logger.info("Matching trajets found for hotel: " + hotel.getAdresse());
    
                        List<Activite> activitesProches = new ArrayList<>();
                        for (Activite activite : selectedActivites) {
                            System.out.println(" asdasdasdasd!"+ hotel.getAdresse() + "    "+activite.getAdresse());
                            double distance = geoUtils.distanceEntre(
                                    geoUtils.GPS2Coordonnes(hotel.getAdresse()),
                                    geoUtils.GPS2Coordonnes(activite.getAdresse())
                            );
                            logger.info("Distance between hotel and activity: " + distance);
                            if (distance <= critereActivite.getDistanceMax()) {
                                logger.info("----------------------------Close Activity added");
                                activitesProches.add(activite);
                            }
                        }
                        logger.info("Number of nearby activities: " + activitesProches.size());

                        double prixAdd = hotel.getPrix() * critereForfait.getDureeSejour() +
                                trajetAller.getPrix() + trajetRetour.getPrix();
                        activitesProches = maximiserActivite(activitesProches, critereForfait.getPrixMax() - prixAdd);
                        logger.info("Number of nearby activities within budget: " + activitesProches.size());
    
                        double prixTotal = prixAdd + activitesProches.stream().mapToDouble(Activite::getPrix).sum();
                        logger.info("Total price of the forfait: " + prixTotal);
    
                        if (prixTotal <= critereForfait.getPrixMax()) {
                            Forfait forfait = new Forfait();
                            forfait.setHotel(hotel);
                            forfait.setTransportAlle(trajetAller);
                            forfait.setTransportRetour(trajetRetour);
                            forfait.setActivites(activitesProches);
                            selectedForfaits.add(forfait);
                            logger.info("Added forfait to selected forfaits");
                        } else {
                            logger.info("Forfait price exceeds the maximum price");
                        }
                    } else {
                        logger.info("Trajets do not match the hotel and forfait criteria");
                    }
                }
            }
        }
    
        logger.info("Number of selected forfaits: " + selectedForfaits.size());
    
        // Sort hotels based on priority
        if (critereHotel.getPrioriteHotel() == CritereHotel.PrioriteHotel.CLASSEMENT) {
            selectedForfaits.sort((f1, f2) -> Integer.compare(f2.getHotel().getClassement(), f1.getHotel().getClassement()));
            // logger.info("Sorted forfaits based on hotel classement");
        } else {
            selectedForfaits.sort((f1, f2) -> Double.compare(f1.getHotel().getPrix(), f2.getHotel().getPrix()));
            // logger.info("Sorted forfaits based on hotel price");
        }
    
        // Sort trajets based on priority
        if (critereTrajet.getPrioriteTrajet() == CritereTrajet.PrioriteTrajet.TEMPS) {
            selectedForfaits.sort((f1, f2) -> {
                Instant tempsAller1 = f1.getTransportAlle().getTempsArrivee();
                Instant tempsAller2 = f2.getTransportAlle().getTempsArrivee();
                return tempsAller1.compareTo(tempsAller2);
            });
            // logger.info("Sorted forfaits based on trajet time");
        } else {
            selectedForfaits.sort((f1, f2) -> {
                double prixAller1 = f1.getTransportAlle().getPrix();
                double prixAller2 = f2.getTransportAlle().getPrix();
                return Double.compare(prixAller1, prixAller2);
            });
            // logger.info("Sorted forfaits based on trajet price");
        }
    
        logger.info("Search completed");
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
        logger.info("Selected hotels: " + selectedHotels.size());
        return  selectedHotels;
    }

    public List<Trajet> SearchTrajet(CritereTrajet critere){
        List<Trajet> tousTrajet = dataHandler.getTrajets();
        logger.info("Total trajets: " + tousTrajet.size());
        List<Trajet> selectedTrajet = new ArrayList<>();
        for(Trajet trajet : tousTrajet){
            if(trajet.getModeTransport() == critere.getModeTrajet()){
                selectedTrajet.add(trajet);
            }
        }
        logger.info("Selected trajets: " + selectedTrajet.size());
        return selectedTrajet;
    }

    public List<Activite> SearchActivite(CritereActivite critere){
        List<Activite> tousActivite = dataHandler.getActivites();
        logger.info("Total activities: " + tousActivite.size());
        List<Activite> selectedActivite = new ArrayList<>();
        for(Activite activite : tousActivite){
            if(activite.getCategorie() != critere.getCategorie()){
                selectedActivite.add(activite);
            }
        }
        logger.info("Selected activities: " + selectedActivite.size());
        return  selectedActivite;
    }

    private List<Activite> maximiserActivite(List<Activite> activites, double budget){
        activites.sort(Comparator.comparingDouble(Activite::getPrix));
        List<Activite> maximise = new ArrayList<>();
        double budgetAct = 0;
        for(Activite act : activites){
            if(budgetAct + act.getPrix() <= budget){
                maximise.add(act);
                budgetAct += act.getPrix();
            }else {
                break;
            }
        }
        return maximise;
    }
}