import java.time.LocalDateTime;

public class Flight {
    private String airLineCode;
    private String airLineName;

    // les dates et temps d'arrivée et départ sont en format LocalDateTime
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private int number; //le numéro du vol

    private Aeroport arrival; //l'aéroport d'arrivé du vol

    private Aeroport departure; //l'aéroport de départ du vol


    //constructeur
    public Flight(String airLineCode, String airLineName, LocalDateTime arrivalTime, LocalDateTime departureTime, int number, Aeroport arrival, Aeroport departure) {
        this.airLineCode = airLineCode;
        this.airLineName = airLineName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.number = number;
        this.arrival = arrival;
        this.departure = departure;
    }

    //Getters
    public Aeroport getArrival() {
        return arrival;
    }

    public Aeroport getDeparture() {
        return departure;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "airLineCode='" + airLineCode + '\'' +
                ", airLineName='" + airLineName + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", departureTime=" + departureTime +
                ", number=" + number +
                '}';
    }
}
