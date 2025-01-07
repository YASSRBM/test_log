package main.java.sejour;

import java.util.List;

public class Forfait {
    private String transportAlle;
    private String transportRetour;
    private Hotel hotel;
    private List<Activite> activites;

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Activite> getActivites() {
        return activites;
    }

    public void setActivites(List<Activite> activites) {
        this.activites = activites;
    }

    public String getTransportRetour() {
        return transportRetour;
    }

    public void setTransportRetour(String transportRetour) {
        this.transportRetour = transportRetour;
    }

    public String getTransportAlle() {
        return transportAlle;
    }

    public void setTransportAlle(String transportAlle) {
        this.transportAlle = transportAlle;
    }


}
