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
            //Cette boucle while va lire chaque ligne s dans le fichier
            while (s != null) {
                s = s.replaceAll("\"", "");
                //Enleve les guillemets qui s´eparent les champs de donn´ees GPS.
                //Chaque case/valeur est séparé par des virgules, le split nous permets de récupérer toutes ces valeurs séparement dans un tableau
                String fields[] = s.split(",");
                //On extrait uniquement les grands aeroports, la prmière valeur du tableau correspond au type de l'aeroport
                if (fields[1].equals("large_airport")) {
                    //a partir du tableau fields, nous récupérons les valeur pour créer une instance aeroport
                    Aeroport aero = new Aeroport(fields[9], fields[1], fields[5], parseDouble(fields[12]), parseDouble(fields[11]));
                    //l'aeroport crée est rajouté dans la liste des aéroports
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


    public Aeroport findByCode(String IATA){ //Recherche d'aeroport par code IATA
        for (Aeroport aero:list) { //Boucle for each qui regarde chaque aeroport aero dans la liste list
            if(aero.getIATA().equals(IATA)) //retourne l'aeroport qui correspond au code en parametre
                return aero;
        }
        return null;
    }
    public Aeroport findNearestAirport(double lat1, double long1) { //Cherche l'aeroport le plus proche basé sur les coordonnées

        Aeroport closestAeroport = list.get(0); //Nous commençons la comparaison avec le premier aeroport dans la liste
        double distance = distance(lat1,long1,closestAeroport.getLatitude(),closestAeroport.getLongitude()); //calcul du distance entre les coordonnées saisie et l'aeroport closestAeroport
        for (int i = 1; i < list.size(); i++) { //boucle for pour rechercher l'aeroport le plus proche
            double newDist = distance(lat1,long1,list.get(i).getLatitude(),list.get(i).getLongitude());
            if(distance>newDist) //si l'aeroport i est plus proche que l'aeroport closestAeroport, l'aeroport i deviens le closest aeroport
            {
                distance = newDist;
                closestAeroport = list.get(i);
            }
        }
        //le closestAeroport a la fin de la boucle est l'aeroport le plus proche
        return closestAeroport;
    }

    public ArrayList<Aeroport> getList() {
        return list;
    }
    public double distance(double lat1, double long1, double lat2, double long2) { //calcul du distance entre 2 coordonnées
        double norme;
        double distance;
        // application de la formule norme dans l'énoncé
        norme = Math.pow((lat2-lat1),2) + Math.pow(((long2-long1)*(Math.cos((lat2+lat1)/2))),2);
        // nous utilisons le rayon de la terre en km pour calculer la distance
        distance = Math.sqrt(norme) * 6371;
        return distance;
    }
    public static void main(String[] args){ //Le main pour tester les fonctions
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
