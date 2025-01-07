package main.java.sejour.elements;

import java.time.Instant;

public class Trajet {

    public String getVilleDepart() {
        return villeDepart;
    }

    public void setVilleDepart(String villeDepart) {
        this.villeDepart = villeDepart;
    }

    public String getVilleArrivee() {
        return villeArrivee;
    }

    public void setVilleArrivee(String villeArrivee) {
        this.villeArrivee = villeArrivee;
    }

    public Instant getTempsDepart() {
        return tempsDepart;
    }

    public void setTempsDepart(Instant tempsDepart) {
        this.tempsDepart = tempsDepart;
    }

    public Instant getTempsArrivee() {
        return tempsArrivee;
    }

    public void setTempsArrivee(Instant tempsArrivee) {
        this.tempsArrivee = tempsArrivee;
    }

    public String getModeTransport() {
        return modeTransport;
    }

    public void setModeTransport(String modeTransport) {
        this.modeTransport = modeTransport;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    private String villeDepart;
    private String villeArrivee;
    private Instant tempsDepart;
    private Instant tempsArrivee;
    private String modeTransport;
    private double prix;

    

}