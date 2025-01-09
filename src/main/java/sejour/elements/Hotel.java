package sejour.elements;

public class Hotel {
    public Hotel(String adresse, String ville, int classement, double prix) {
        this.adresse = adresse;
        this.ville = ville;
        this.classement = classement;
        this.prix = prix;
    }
    public Hotel(){}

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getClassement() {
        return classement;
    }

    public void setClassement(int classement) {
        this.classement = classement;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    private String adresse;
    private String ville;
    private int classement;
    private double prix;
}
