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

    public void initData() {
        initHotels();
        initActivites();
        initTrajets();
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
            Path hotelsFilePath;
            if(folderPath==null) 
            hotelsFilePath = Paths.get("src/main/java/sejour/data/Hotels.json");
            else {
                hotelsFilePath=Paths.get(folderPath.toString()+"/Hotels.json");
            }
        

        String hotelsJson = new String(Files.readAllBytes(hotelsFilePath));
    
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
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initActivites() {
        try {
            Path activitiesFilePath;
            if(folderPath==null) 
               activitiesFilePath = Paths.get("src/main/java/sejour/data/Activities.json");
            else {
                activitiesFilePath=Paths.get(folderPath.toString()+"/Activities.json");
            }

        String activitiesJson = new String(Files.readAllBytes(activitiesFilePath));
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
                    e.printStackTrace();
                }

                activites.add(activite);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public void initTrajets() {
        try {
            Path trajetsFilePath;
            if(folderPath==null) 
                trajetsFilePath = Paths.get("src/main/java/sejour/data/Trajet.json");
            else {
                trajetsFilePath=Paths.get(folderPath.toString()+"/Trajet.json");
            }
        
            String trajetsJson = new String(Files.readAllBytes(trajetsFilePath));
            JSONArray trajetsArray = new JSONArray(trajetsJson);

            for (int i = 0; i < trajetsArray.length(); i++) {
                JSONObject trajetJson = trajetsArray.getJSONObject(i);
                Trajet trajet = new Trajet();
                trajet.setVilleDepart(trajetJson.getString("villeDepart"));
                trajet.setVilleArrivee(trajetJson.getString("villeArrivee"));
                trajet.setTempsDepart(Instant.parse(trajetJson.getString("tempsDepart")));
                trajet.setTempsArrivee(Instant.parse(trajetJson.getString("tempsArrivee")));
                trajet.setModeTransport(trajet.fromString(trajetJson.getString("modeTransport")));
                trajet.setPrix(trajetJson.getDouble("prix"));
                Trajets.add(trajet);
            }

            for (Trajet trajet : Trajets) {
                System.out.println("Trajet: " + trajet.getVilleDepart() + " -> " + trajet.getVilleArrivee() + ", Mode: " + trajet.getModeTransport() + ", Prix: " + trajet.getPrix());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
