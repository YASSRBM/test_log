package main.java.sejour;

import java.nio.file.Path;
import java.util.List;

public class DataHandler {
    public DataHandler(Path folderPath){
        this.folderPath = folderPath;
    }

    private Path folderPath;

    public List<Hotel> getHotels() {
        return hotels;
    }

    public List<Activite> getActivites() {
        return activites;
    }

    public List<String> getTransports() {
        return transports;
    }

    public List<Forfait> getForfaits() {
        return forfaits;
    }

    private List<Hotel> hotels;
    private List<Forfait> forfaits;
    private List<String> transports;
    private List<Activite> activites;

    public void initHotels(){}
    public void initForfaits(){}
    public void initTransports(){}
    public void initActivites(){}

}
