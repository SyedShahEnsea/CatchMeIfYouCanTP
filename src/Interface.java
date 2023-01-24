import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonString;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class Interface extends Application {
    double posY;
    double posX;
    double longitude;
    double latitude;


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("CatchMeIfYouCan");
        Earth eth = new Earth(); //Nous créeon une instance Earth qui est un Group JavaFX
        //Pane pane = new Pane(eth);
        Scene theScene = new Scene(eth, 600, 400,true); //l'instance Earth est ajouté dans la scene theScene de taille 600x400
        PerspectiveCamera camera = new PerspectiveCamera(true); //La Camera pour focaliser sur la terre
        camera.setTranslateZ(-1000);
        camera.setNearClip(0.001); //La terre disparait si on zoom plus que ça
        camera.setFarClip(4000.0);
        camera.setFieldOfView(35);
        theScene.setCamera(camera); //Ajout de la caméra dans la scene



        theScene.addEventHandler(MouseEvent.ANY, event -> { //Cette event handler nous permet de capturer tout type d'action de la souris dans la scene
           /* if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                System.out.println("Clicked on : (" + event.getSceneX()+ ", "+ event.getSceneY()+")");
                posY=event.getSceneY();
                posX=event.getSceneX();
            }*/
            if(event.getButton() == MouseButton.SECONDARY && event.getEventType()==MouseEvent.MOUSE_CLICKED){ //Si l'utilisateur fait un clique droit sur la scene,tout ce qui est dans ce boucle if sera exécuté
                //Nous récupérons le resultat de notre évenement dans un objet PickResult.
                PickResult pickResult = event.getPickResult();
                if(pickResult.getIntersectedNode() != null){
                    Point2D click = pickResult.getIntersectedTexCoord(); //Nous récupérons les coordonnées du click droit de la souris en un objet Point2D. Cette objet contient les coordonnées x et y du click de la souris

                    //Application de la formule de l'énoncé pour convertir les coordonnées du click en longitude et latitude.
                    //Ce calcul est très approximatif
                    latitude = 2*Math.toDegrees(Math.atan(Math.exp((0.5-click.getY())/0.2678))) - 90;
                    longitude = 360 * (click.getX() - 0.5);

                    //Création d'un world w.
                    World w = new World ("./data/airport-codes_no_comma.csv");
                    //Nous recherchons l'aeroport le plus proche du click de l'utilisateur
                    Aeroport a = w.findNearestAirport(latitude,longitude);

                    //Nous affihcons une sphère a l'emplacement de laeroport sur la Terre. Vu que le calcul est approximatif, le sphere est décalé de la vrai position de l'aeroport
                    eth.displayRedSphere(a);
                    System.out.println(a);
                    //System.out.println(x+ " " + y + " "+ z);

                    /*HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://api.aviationstack.com/v1/flights?access_key=891cde7e542de1d127e3f08b470252e2&arr_iata=" + a.getIATA()))
                            .build();

                    HttpResponse<String> response =
                            null;
                    try {
                        response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    JsonFlightFiller flightFiller = new JsonFlightFiller(runnable.getRep().toString(), w);
                    eth.displayYellowSphere(flightFiller.getList());
                    */

                    //Afin que l'animation ne s'arrete pas lors de l'execution de la requete API, je crée une classe runnable qui executera la requete API et affichera les sphère jaunes sur les aeroport de départ
                    //en paramtère l'objet Earth eth, l'objet World w, et la requete api a executé pour recherché les vols récemment arrivé sur l'aeroport a
                    HTTPSRunnable runnable = new HTTPSRunnable("http://api.aviationstack.com/v1/flights?access_key=34bfe0695c91248616993fdafeee7a88&arr_iata=" + a.getIATA(),eth,w);
                    //Pour ne pas bloquer l'animation, la classe HTTPSRunnable(et donc la requete api et l'apparition des sphère jaune) sera executer dans un autre Thread en parrallel
                    Thread thread = new Thread(runnable);
                    thread.start(); //Pour lancer le thread.





                }
            }
            //Pour de zoomer sur la terre
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) { //Détecter si l'utilisateur scroll la souris sur la scene
                Translate translate = new Translate(0, 0, (event.getSceneY()-posY)*0.01); //créer un  translate basé sur le mouvement de la souris
                camera.getTransforms().add(translate); //Apliquer cette translation
            }
        });
        primaryStage.setScene(theScene); // Ajouter la scène au stage
        primaryStage.show(); //Afichier le stage
    }
    public static void main(String[] args) {
        launch(args);

    }
}
