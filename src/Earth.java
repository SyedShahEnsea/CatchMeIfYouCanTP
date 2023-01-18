import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class Earth extends Group{
    protected Rotate ry;
    protected Sphere sph;
    private ArrayList<Sphere> yellowSphere = new ArrayList<>();
    Image earth = new Image("file:./data/earth_lights_4800.png");
    protected PhongMaterial material;

    public Earth() {
        this.ry = new Rotate();
        this.sph=new Sphere(300);
        this.material = new PhongMaterial();
        this.material.setDiffuseMap(earth);
        this.sph.setMaterial(this.material);
        this.getChildren().add(this.sph);
        this.getTransforms().add(this.ry);

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                ry.setAxis(Rotate.Y_AXIS);
                ry.setAngle(time/(1.5e10/360));

            }
        };
        animationTimer.start();
    }
    public Sphere createSphere(Aeroport a, Color color){
            Sphere redSphere = new Sphere(2);
            PhongMaterial m = new PhongMaterial();
            m.setDiffuseColor(color);
            redSphere.setMaterial(m);
            return redSphere;
    }

    public Earth getEarth(){

        return this;
    }
}
