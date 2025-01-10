package sejour.elements;

public class CritereTrajet {
    private Trajet.ModeTrajet modeTrajet;
    private PrioriteTrajet prioriteTrajet;


    public enum PrioriteTrajet {TEMPS, PRIX}

    public CritereTrajet(Trajet.ModeTrajet modeTrajet, PrioriteTrajet prioriteTrajet) {
        this.modeTrajet = modeTrajet;
        this.prioriteTrajet = prioriteTrajet;
    }

    public Trajet.ModeTrajet getModeTrajet() {
        return modeTrajet;
    }

    public PrioriteTrajet getPrioriteTrajet() {
        return prioriteTrajet;
    }



}
