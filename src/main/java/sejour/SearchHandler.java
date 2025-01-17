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
        List<Hotel> selectedHotels = SearchHotel(critereHotel);
        List<Trajet> selectedTrajets = SearchTrajet(critereTrajet);
        List<Activite> selectedActivites = SearchActivite(critereActivite);
    
        List<Forfait> selectedForfaits = new ArrayList<>();
    
        for (Hotel hotel : selectedHotels) {
            logger.info("Processing hotel: " + hotel.getAdresse());
            for (Trajet trajetAller : selectedTrajets) {
                logger.info("Processing trajet aller: " + trajetAller.getVilleDepart() + " -> " + trajetAller.getVilleArrivee());
                for (Trajet trajetRetour : selectedTrajets) {
                    logger.info("Processing trajet retour: " + trajetRetour.getVilleDepart() + " -> " + trajetRetour.getVilleArrivee());
                    if (trajetAller.getVilleArrivee().equals(hotel.getVille()) &&
                            trajetAller.getVilleDepart().equals(critereForfait.getVilleDepart()) &&
                            trajetRetour.getVilleArrivee().equals(critereForfait.getVilleDepart())) {
                        logger.info("Matching trajets found for hotel: " + hotel.getAdresse());
    
                        List<Activite> activitesProches = new ArrayList<>();
                        for (Activite activite : selectedActivites) {
                            double distance = geoUtils.distanceEntre(
                                    geoUtils.GPS2Coordonnes(hotel.getAdresse()),
                                    geoUtils.GPS2Coordonnes(activite.getAdresse())
                            );
                            logger.info("Distance between hotel and activity: " + distance);
                            if (critereActivite != null && distance <= critereActivite.getDistanceMax()) {
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

        if(critereHotel != null) {
            // Sort hotels based on priority
            if (critereHotel.getPrioriteHotel() == CritereHotel.PrioriteHotel.CLASSEMENT) {
                selectedForfaits.sort((f1, f2) -> Integer.compare(f2.getHotel().getClassement(), f1.getHotel().getClassement()));
            } else {
                selectedForfaits.sort((f1, f2) -> Double.compare(f1.getHotel().getPrix(), f2.getHotel().getPrix()));
            }
        }
    
        // Sort trajets based on priority
        if(critereTrajet != null) {
            if (critereTrajet.getPrioriteTrajet() == CritereTrajet.PrioriteTrajet.TEMPS) {
                selectedForfaits.sort((f1, f2) -> {
                    Instant tempsAller1 = f1.getTransportAlle().getTempsArrivee();
                    Instant tempsAller2 = f2.getTransportAlle().getTempsArrivee();
                    if (tempsAller1.equals(tempsAller2)) {
                        return Double.compare(f1.getTransportAlle().getPrix(), f2.getTransportAlle().getPrix());
                    }
                    return tempsAller1.compareTo(tempsAller2);
                });
            } else {
                selectedForfaits.sort((f1, f2) -> {
                    double prixAller1 = f1.getTransportAlle().getPrix();
                    double prixAller2 = f2.getTransportAlle().getPrix();
                    if (prixAller1 == prixAller2) {
                        return f1.getTransportAlle().getTempsArrivee().compareTo(f2.getTransportAlle().getTempsArrivee());
                    }
                    return Double.compare(prixAller1, prixAller2);
                });
            }
        }

        if(critereTrajet != null) {
            if (critereTrajet.getPrioriteTrajet() == CritereTrajet.PrioriteTrajet.TEMPS) {
                selectedForfaits.sort((f1, f2) -> {
                    Instant tempsAller1 = f1.getTransportRetour().getTempsArrivee();
                    Instant tempsAller2 = f2.getTransportRetour().getTempsArrivee();
                    if (tempsAller1.equals(tempsAller2)) {
                        return Double.compare(f1.getTransportRetour().getPrix(), f2.getTransportRetour().getPrix());
                    }
                    return tempsAller1.compareTo(tempsAller2);
                });
            } else {
                selectedForfaits.sort((f1, f2) -> {
                    double prixAller1 = f1.getTransportRetour().getPrix();
                    double prixAller2 = f2.getTransportRetour().getPrix();
                    if (prixAller1 == prixAller2) {
                        return f1.getTransportRetour().getTempsArrivee().compareTo(f2.getTransportRetour().getTempsArrivee());
                    }
                    return Double.compare(prixAller1, prixAller2);
                });
            }
        }
    
        logger.info("Search completed");
        return selectedForfaits;
    }

    public List<Hotel> SearchHotel(CritereHotel critere){
        List<Hotel> tousHotels = dataHandler.getHotels();
        if(critere != null) {
            List<Hotel> selectedHotels = new ArrayList<>();
            for (Hotel hotel : tousHotels) {
                if (hotel.getClassement() >= critere.getMinClassement()) {
                    selectedHotels.add(hotel);
                }
            }
            logger.info("Selected hotels: " + selectedHotels.size());
            return selectedHotels;
        }
        return tousHotels;
    }

    public List<Trajet> SearchTrajet(CritereTrajet critere){
        List<Trajet> tousTrajet = dataHandler.getTrajets();
        if(critere != null) {

            logger.info("Total trajets: " + tousTrajet.size());
            List<Trajet> selectedTrajet = new ArrayList<>();
            for (Trajet trajet : tousTrajet) {
                if (trajet.getModeTransport() == critere.getModeTrajet()) {
                    selectedTrajet.add(trajet);
                }
            }
            logger.info("Selected trajets: " + selectedTrajet.size());
            return selectedTrajet;
        }
        return tousTrajet;
    }

    public List<Activite> SearchActivite(CritereActivite critere){
        List<Activite> tousActivite = dataHandler.getActivites();
        logger.info("Total activities: " + tousActivite.size());
        if(critere != null) {
            List<Activite> selectedActivite = new ArrayList<>();
            for (Activite activite : tousActivite) {
                if (activite.getCategorie() != critere.getCategorie()) {
                    selectedActivite.add(activite);
                }
            }
            logger.info("Selected activities: " + selectedActivite.size());
            return selectedActivite;
        }
        return tousActivite;
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