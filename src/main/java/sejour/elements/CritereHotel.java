package main.java.sejour.elements;

public class CritereHotel {
    private enum PrioriteHotel {CLASSEMENT, PRIX}

    public CritereHotel(int minClassement, PrioriteHotel prioriteHotel) {
        this.minClassement = minClassement;
        this.prioriteHotel = prioriteHotel;
    }

    public int getMinClassement() {
        return minClassement;
    }

    public PrioriteHotel getPrioriteHotel() {
        return prioriteHotel;
    }

    private int minClassement;
    private PrioriteHotel prioriteHotel;
}
