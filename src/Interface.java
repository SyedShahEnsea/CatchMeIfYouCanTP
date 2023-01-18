import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;


public class Interface extends Application {
    double posY;
    double posX;
    double longitude;
    double latitude;

    public static final double PI = 3.14159265358979323846;
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Hello world");
        Earth eth = new Earth();
        //Pane pane = new Pane(eth);
        Scene theScene = new Scene(eth, 600, 400,true);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000);
        camera.setNearClip(0.001);
        camera.setFarClip(4000.0);
        camera.setFieldOfView(35);
        theScene.setCamera(camera);


        theScene.addEventHandler(MouseEvent.ANY, event -> {
           /* if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                System.out.println("Clicked on : (" + event.getSceneX()+ ", "+ event.getSceneY()+")");
                posY=event.getSceneY();
                posX=event.getSceneX();
            }*/
            if(event.getButton() == MouseButton.SECONDARY && event.getEventType()==MouseEvent.MOUSE_CLICKED){
                PickResult pickResult = event.getPickResult();
                if(pickResult.getIntersectedNode() != null){
                    Point2D click = pickResult.getIntersectedTexCoord();
                    System.out.println(click);

                    latitude = 2*(Math.toDegrees(Math.atan(Math.exp(0.5-click.getY()/0.2678)))) - (Math.PI/4);
                    longitude = 360 * (click.getX() - 0.5);

                    System.out.println("Longitude: " + longitude);
                    System.out.println("Latitude: " + latitude);
                    World w = new World ("./data/airport-codes_no_comma.csv");
                    System.out.println(w.findNearestAirport(latitude,longitude));
                }
            }

            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                Translate translate = new Translate(0, 0, (event.getSceneY()-posY)*0.2);
                camera.getTransforms().add(translate);
            }
        });
        primaryStage.setScene(theScene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);

    }
}