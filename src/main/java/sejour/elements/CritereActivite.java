package sejour.elements;


public class CritereActivite {

    public Activite.Categorie getCategorie() {
        return categorie;
    }

    public double getDistanceMax() {
        return distanceMax;
    }

    private Activite.Categorie categorie;
    private double distanceMax;

    public CritereActivite(Activite.Categorie categorie, double distanceMax) {
        this.categorie = categorie;
        this.distanceMax = distanceMax;
    }


}
