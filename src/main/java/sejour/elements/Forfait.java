package sejour.elements;

import java.util.List;

public class Forfait {
    private Trajet trajetAlle;
    private Trajet trajetRetour;
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

    public Trajet getTransportRetour() {
        return trajetRetour;
    }

    public void setTransportRetour(Trajet transportRetour) {
        this.trajetRetour = transportRetour;
    }

    public Trajet getTransportAlle() {
        return trajetAlle;
    }

    public void setTransportAlle(Trajet transportAlle) {
        this.trajetAlle = transportAlle;
    }


}
