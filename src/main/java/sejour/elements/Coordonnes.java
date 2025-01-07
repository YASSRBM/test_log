package sejour.elements;

public class Coordonnes {
    public Coordonnes(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private double latitude;
    private double longitude;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }




}
