package sejour.elements;

public class CritereTrajet {
    private ModeTrajet modeTrajet;
    private PrioriteTrajet prioriteTrajet;

    public enum ModeTrajet {AVION, TRAIN}
    public enum PrioriteTrajet {TEMPS, PRIX}

    public CritereTrajet(ModeTrajet modeTrajet, PrioriteTrajet prioriteTrajet) {
        this.modeTrajet = modeTrajet;
        this.prioriteTrajet = prioriteTrajet;
    }

    public ModeTrajet getModeTrajet() {
        return modeTrajet;
    }

    public PrioriteTrajet getPrioriteTrajet() {
        return prioriteTrajet;
    }



}
