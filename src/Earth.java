import javafx.scene.Group;
import javafx.scene.image.Image;
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
    }
    public void createSphere(){
        this.sph = new Sphere(300);
    }

    public Earth getEarth(){
        return this;
    }
}
