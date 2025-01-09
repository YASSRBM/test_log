package sejour.elements;

public class CritereTrajet {

    public enum ModeTrajet {AVION, TRAIN}
    public enum PrioriteTrajet {TEMPS, PRIX}

    public CritereTrajet(String modeTrajet, PrioriteTrajet prioriteTrajet) {
        this.modeTrajet = modeTrajet;
        this.prioriteTrajet = prioriteTrajet;
    }

    public String getModeTrajet() {
        return modeTrajet;
    }

    public PrioriteTrajet getPrioriteTrajet() {
        return prioriteTrajet;
    }

    private String modeTrajet;
    private PrioriteTrajet prioriteTrajet;

}
