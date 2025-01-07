package main.java.sejour.elements;

public class CritereForfait {
    public int getDureeSejour() {
        return dureeSejour;
    }

    public String getVilleDepart() {
        return villeDepart;
    }

    public int getPrixMax() {
        return prixMax;
    }

    private String villeDepart;
    private int dureeSejour;
    private int prixMax;

    public CritereForfait(String villeDepart, int dureeSejour, int prixMax) {
        this.villeDepart = villeDepart;
        this.dureeSejour = dureeSejour;
        this.prixMax = prixMax;
    }


}
