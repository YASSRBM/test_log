package sejour.elements;

public class CritereTrajet {

    private enum ModeTrajet {AVION, TRAIN}
    private enum PrioriteTrajet {TEMPS, PRIX}

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

    private ModeTrajet modeTrajet;
    private PrioriteTrajet prioriteTrajet;

}
