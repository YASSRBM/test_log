package sejour.elements;

import java.util.Date;



public class Activite {
    public enum Categorie {SPORT, MUSIQUE, TOURISTIQUE, SHOPPING, CULTURELLE}

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Categorie fromString(String categorieStr) {
        switch (categorieStr.toUpperCase()) {
            case "SPORT":
                return Categorie.SPORT;
            case "MUSIQUE":
                return Categorie.MUSIQUE;
            case "TOURISTIQUE":
                return Categorie.TOURISTIQUE;
            case "SHOPPING":
                return Categorie.SHOPPING;
            case "CULTURELLE":
                return Categorie.CULTURELLE;
            default:
                throw new IllegalArgumentException("Unknown category: " + categorieStr);
        }
    }
    
    private String adresse;
    private Date date;
    private Categorie categorie;
    private double prix;
}
