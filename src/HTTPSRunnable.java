import javafx.application.Platform;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//Cette classe permet d'executer la requete API et afficher les sphere jaunes sur la terre
public class HTTPSRunnable implements Runnable{
    private String url;

    private Earth eth;

    private World w;

    //Constructeur
    public HTTPSRunnable(String url, Earth eth, World w) {
        this.url = url;
        this.eth = eth;
        this.w = w;
    }

    @Override
    public void run() {
        //Pour executer une requete API, nous créon d'abord un client HTTPS
        HttpClient client = HttpClient.newHttpClient();

        //Avec l'url de la requete API en parallel nous construisons la requete https a envoyer
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.url))
                .build();

        //La réponse de la requete est stocké dans réponse
        HttpResponse<String> response =
                null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString()); //Nous envoyons la requete HTTPS, elle nous retournera une réponse
            //Le JSON est stocké dans reponse.body()
            //Une fois les informations sur les vols récupéré via l'API, nous créons une instance JSONFlightFiller afin d'avoir la liste des vols en Objets FLight
            JsonFlightFiller flightFiller = new JsonFlightFiller(response.body().toString(), this.w);
            Platform.runLater(() -> { //Vu que la class runnable est executé dans un thread séparé, nos élements javafx ne retrouvent pas dans ce thread. Nous utiilsons alors Platform.runlater() pour que la function createYellowSphere soit executé plus tard dans le Thread JavaFX
                this.eth.displayYellowSphere(flightFiller.getList()); //Créer les sphère jaune a partir de la liste des vols
            });


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
