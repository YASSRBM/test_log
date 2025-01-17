package sejour.elements;

public class CritereForfait {
    private String villeDepart;
    private int dureeSejour;
    private int prixMax;
    private String villeRetour;
    public int getDureeSejour() {
        return dureeSejour;
    }

    public String getVilleDepart() {
        return villeDepart;
    }

    public int getPrixMax() {
        return prixMax;
    }
    public String getVilleRetour() {
        return villeRetour;
    }


    public CritereForfait(String villeDepart, String villeRetour, int dureeSejour, int prixMax) {
        this.villeDepart = villeDepart;
        this.villeRetour = villeRetour;
        this.dureeSejour = dureeSejour;
        this.prixMax = prixMax;
    }

}
