import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

//Cette classe nous permets de créer la liste des vols en Objet FLight a partir du JSON reçu de la requete API
public class JsonFlightFiller {

    private ArrayList<Flight> list = new ArrayList<Flight>(); //Liste des vols (Liste Objets FLight)

    public JsonFlightFiller(String jsonString,World w) { //Nous récupérons la réponse de l'api en format JSON et la Terre en paramètre
        try {
            //lire et convertir la réponse JSON String en objet JSON
            InputStream is = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
            JsonReader rdr = Json.createReader(is);
            JsonObject obj = rdr.readObject();
            JsonArray results = obj.getJsonArray("data");
            //for each pour avoir l'information de chaque vol result dans l'objet JSON results
            for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                try {
                    //Formetter qui sera utilisé pour convertir la date String en LocalDateTime. J'ai choisi le formatter prédéfinie ISO_OFFSET_DATE_TIME après avoir regardé le formats des dates dans la réponse API
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
                    //Nous créons l'objet flight pour chaque Vol dans le JSON
                    //Chaque JSON Object result contient des JSON Objects airline, departure, arrival et flight.Ces obets eux meme contiennent des informations en format Clé-Valeur
                    //Le getString permet de récupérer la valeur en fournissant la clé. Par exemple getString("iata") nous retourne la valeur  retrouve a la clé "iata"
                    //Nous utilisons un parser pour convertir le string en format LocalDateTime.
                    //Nous avons parfois l'erreur "javax.json.JsonValueImpl cannot be cast to class javax.json.JsonString". Ceci arrive quand une valeur retourné par l'API est null
                    Flight f = new Flight(result.getJsonObject("airline").getString("iata"),result.getJsonObject("airline").getString("name"),LocalDateTime.parse(result.getJsonObject("arrival").getString("scheduled"),formatter),LocalDateTime.parse(result.getJsonObject("departure").getString("scheduled"),formatter),Integer.parseInt(result.getJsonObject("flight").getString("number")),w.findByCode(result.getJsonObject("arrival").getString("iata")),w.findByCode(result.getJsonObject("departure").getString("iata")));
                    list.add(f); //Ajout de du vol (objet flightà dans la liste list
                    System.out.println(f);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Flight> getList() {
        return list;
    }

    public static void main (String[] args){
        try {
            World w = new World ("./data/airport-codes_no_comma.csv");
            BufferedReader br = new BufferedReader(new FileReader("data/JsonOrly.txt"));
            String test = br.readLine();
            JsonFlightFiller jSonFlightFiller = new JsonFlightFiller(test,w);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

