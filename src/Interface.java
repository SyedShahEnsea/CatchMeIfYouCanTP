import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
public class Interface extends Application {
    double posY;
    double posX;
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Hello world");
        Earth eth = new Earth();
        Pane pane = new Pane(eth);
        Scene theScene = new Scene(pane, 600, 400,true);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000);
        camera.setNearClip(0.001);
        camera.setFarClip(4000.0);
        camera.setFieldOfView(35);
        theScene.setCamera(camera);
        primaryStage.setScene(theScene);
        primaryStage.show();

        theScene.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                System.out.println("Clicked on : (" + event.getSceneX()+ ", "+ event.getSceneY()+")");
                posY=event.getSceneY();
                posX=event.getSceneX();
            }
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                Translate translate = new Translate(0, 0, (event.getSceneY()-posY)*0.2);
                camera.getTransforms().add(translate);
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}