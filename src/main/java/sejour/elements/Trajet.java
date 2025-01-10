package sejour.elements;

import java.time.Instant;

public class Trajet {

    public enum ModeTrajet {AVION, TRAIN, VOITURE, BUS}
    private String villeDepart;
    private String villeArrivee;
    private Instant tempsDepart;
    private Instant tempsArrivee;
    private ModeTrajet modeTransport;
    private double prix;

    public Trajet(String villeDepart, String villeArrivee, Instant tempsDepart, Instant tempsArrivee, ModeTrajet modeTransport, double prix) {
        this.villeDepart = villeDepart;
        this.villeArrivee = villeArrivee;
        this.tempsDepart = tempsDepart;
        this.tempsArrivee = tempsArrivee;
        this.modeTransport = modeTransport;
        this.prix = prix;
    }
    public Trajet(){}

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

    public ModeTrajet getModeTransport() {
        return modeTransport;
    }

    public void setModeTransport(ModeTrajet modeTransport) {
        this.modeTransport = modeTransport;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public ModeTrajet fromString(String modeTrajetStr) {
        switch (modeTrajetStr.toUpperCase()) {
            case "AVION":
                return ModeTrajet.AVION;
            case "TRAIN":
                return ModeTrajet.TRAIN;
            case "VOITURE":
                return ModeTrajet.VOITURE;
            case "BUS":
                return ModeTrajet.BUS;
            default:
                throw new IllegalArgumentException("Unknown category: " + modeTrajetStr);
        }
    }



    

}