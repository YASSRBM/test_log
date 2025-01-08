package sejour;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Handler;

import org.json.JSONArray;
import org.json.JSONObject;

import sejour.elements.*;




public class DataHandler {


    private List<Hotel> hotels = new ArrayList<>();
    private List<Trajet> Trajets = new ArrayList<>();
    private List<Activite> activites =  new ArrayList<>();
    private Path folderPath = null;

    public DataHandler(Path folderPath){
        this.folderPath = folderPath;
    }

    public DataHandler(){

    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public List<Activite> getActivites() {
        return activites;
    }

    public List<Trajet> getTrajets() {
        return Trajets;
    }


    public void initHotels() {
        try {
            String hotelsJson = new String(Files.readAllBytes(Paths.get("data/Hotels.json")));

            JSONArray hotelsArray = new JSONArray(hotelsJson);

            // Iterate over the JSON array and create Hotel instances
            for (int i = 0; i < hotelsArray.length(); i++) {
                JSONObject hotelJson = hotelsArray.getJSONObject(i);

                Hotel hotel = new Hotel();
                hotel.setAdresse(hotelJson.getString("adresse"));
                hotel.setVille(hotelJson.getString("ville"));
                hotel.setClassement(hotelJson.getInt("classement"));
                hotel.setPrix(hotelJson.getDouble("prix"));

                hotels.add(hotel);
            }

            for (Hotel hotel : hotels) {
                System.out.println("Hotel: " + hotel.getAdresse() + ", Ville: " + hotel.getVille() + ", Classement: " + hotel.getClassement() + ", Prix: " + hotel.getPrix());
            }
        } catch (IOException e) {
            System.err.println("Error reading Hotels.json file: " + e.getMessage());
        }
    }


    public void initActivites() {
        try {
            String activitiesJson = new String(Files.readAllBytes(Paths.get("data/Activities.json")));

            JSONArray activitiesArray = new JSONArray(activitiesJson);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for (int i = 0; i < activitiesArray.length(); i++) {
                JSONObject activityJson = activitiesArray.getJSONObject(i);

                Activite activite = new Activite();
                activite.setAdresse(activityJson.getString("adresse"));
                String categorieStr = activityJson.getString("categorie");
                activite.setCategorie(activite.fromString(categorieStr));
                activite.setPrix(activityJson.getDouble("prix"));

                // Convert the date string to a Date object
                try {
                    Date date = dateFormat.parse(activityJson.getString("date"));
                    activite.setDate(date);
                } catch (ParseException e) {
                    System.err.println("Error parsing date: " + e.getMessage());
                }

                activites.add(activite);
            }

            for (Activite activite : activites) {
                System.out.println("Activite: " + activite.getAdresse() + ", Date: " + activite.getDate() + ", Categorie: " + activite.getCategorie() + ", Prix: " + activite.getPrix());
            }
        } catch (IOException e) {
            System.err.println("Error reading Activities.json file: " + e.getMessage());
        }
    }
    

    public void initTrajets() {
        try {
            String trajetsJson = new String(Files.readAllBytes(Paths.get("data/Trajet.json")));
            JSONArray trajetsArray = new JSONArray(trajetsJson);

            for (int i = 0; i < trajetsArray.length(); i++) {
                JSONObject trajetJson = trajetsArray.getJSONObject(i);
                Trajet trajet = new Trajet();
                trajet.setVilleDepart(trajetJson.getString("villeDepart"));
                trajet.setVilleArrivee(trajetJson.getString("villeArrivee"));
                trajet.setTempsDepart(Instant.parse(trajetJson.getString("tempsDepart")));
                trajet.setTempsArrivee(Instant.parse(trajetJson.getString("tempsArrivee")));
                trajet.setModeTransport(trajetJson.getString("modeTransport"));
                trajet.setPrix(trajetJson.getDouble("prix"));
                Trajets.add(trajet);
            }

            for (Trajet trajet : Trajets) {
                System.out.println("Trajet: " + trajet.getVilleDepart() + " -> " + trajet.getVilleArrivee() + ", Mode: " + trajet.getModeTransport() + ", Prix: " + trajet.getPrix());
            }
        } catch (IOException e) {
            System.err.println("Error reading trajet.json file: " + e.getMessage());
        }
    }




}
