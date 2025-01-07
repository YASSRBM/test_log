package main.java.sejour;

import main.java.sejour.elements.Activite;
import main.java.sejour.elements.Forfait;
import main.java.sejour.elements.Hotel;

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
