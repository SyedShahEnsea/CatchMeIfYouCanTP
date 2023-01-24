public class Aeroport {
    protected String IATA;
    protected String name;
    protected String country;
    protected double latitude;
    protected double longitude;

   //Constructeur Aeroport
    public Aeroport(String IATA, String name, String country, double latitude, double longitude) {
        this.IATA = IATA;
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //Les Getters
    public String getIATA() {
        return IATA;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    @Override
    public String toString() {
        return "Aeroport{" +
                "IATA='" + IATA + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}
