package sejour.elements;

public class CritereActivite {
    private enum Categorie {SPORT, MUSIQUE}

    public Categorie getCategorie() {
        return categorie;
    }

    public double getDistanceMax() {
        return distanceMax;
    }

    private Categorie categorie;
    private double distanceMax;

    public CritereActivite(Categorie categorie, double distanceMax) {
        this.categorie = categorie;
        this.distanceMax = distanceMax;
    }


}
