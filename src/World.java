import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;


public class World {

    private ArrayList<Aeroport> list = new ArrayList<>();

    public World (String fileName) {
        try {
            BufferedReader buf = new BufferedReader(new FileReader(fileName));
            String s = buf.readLine();
            while (s != null) {
                s = s.replaceAll("\"", "");
                //Enleve les guillemets qui s´eparent les champs de donn´ees GPS.
                String fields[] = s.split(",");
                // Une bonne id´ee : placer un point d'arr^et ici pour du debuggage.
                if (fields[1].equals("large_airport")) {
                    Aeroport aero = new Aeroport(fields[9], fields[1], fields[5], parseDouble(fields[12]), parseDouble(fields[11]));
                    list.add(aero);
                    //System.out.println(aero);
                }
                s = buf.readLine();
            }
        } catch (Exception e) {
            System.out.println("Maybe the file isn't there ?");
            System.out.println(list.get(list.size() - 1));
            e.printStackTrace();
        }
    }
    public Aeroport findByCode(String IATA){
        for (Aeroport aero:list) {
            if(aero.getIATA().equals(IATA))
                return aero;
        }
        return null;
    }
    public Aeroport findNearestAirport(double lat1, double long1) {

        Aeroport closestAeroport = list.get(0);
        double distance = distance(lat1,long1,closestAeroport.getLatitude(),closestAeroport.getLongitude());
        for (int i = 1; i < list.size(); i++) {
            double newDist = distance(lat1,long1,list.get(i).getLatitude(),list.get(i).getLongitude());
            if(distance>newDist)
            {
                distance = newDist;
                closestAeroport = list.get(i);
            }
        }
        return closestAeroport;
    }

    public ArrayList<Aeroport> getList() {
        return list;
    }
    public double distance(double lat1, double long1, double lat2, double long2) {
        double norme;
        double distance;

        norme = Math.pow((lat2-lat1),2) + Math.pow(((long2-long1)*(Math.cos((lat2+lat1)/2))),2);
        distance = Math.sqrt(norme) * 6371;
        return distance;
    }
    public static void main(String[] args){
        World w = new World ("./data/airport-codes_no_comma.csv");
        System.out.println("Found "+w.getList().size()+" airports.");
        Aeroport paris = w.findNearestAirport(48.866,2.316);
        Aeroport cdg = w.findByCode("CDG");
        double distance = w.distance(48.866,2.316,paris.getLongitude(),paris.getLatitude());
        System.out.println(paris);
        System.out.println(distance);
        double distanceCDG = w.distance(48.866,2.316,cdg.getLongitude(),cdg.getLatitude());
        System.out.println(cdg);
        System.out.println(distanceCDG);
        Aeroport tokyo = w.findNearestAirport(35.6762,139.6503);
        System.out.println(tokyo);

    }
}
