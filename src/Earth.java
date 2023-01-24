import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.ArrayList;

public class Earth extends Group{
    private static final double R = 300; //Rayon du sphere Terre
    protected Rotate ry;
    protected Sphere sph;
    private ArrayList<Sphere> yellowSphere = new ArrayList<>(); //liste des spheres jaunes
    Image earth = new Image("file:./data/earth_lights_4800.png"); //l'image a mettre sur la sphere Terre a travers PhongMaterial
    protected PhongMaterial material;

    //Constructeur Earth
    public Earth() {
        this.ry = new Rotate();
        this.sph=new Sphere(300); //Créer une sphere de rayon 300
        this.material = new PhongMaterial();
        this.material.setDiffuseMap(earth); //application de l'image comme phongMaterial sur la sphere Terre
        this.sph.setMaterial(this.material);
        this.getChildren().add(this.sph); //Ajout du noeud sphere dans le Group JavaFX
        this.getTransforms().add(this.ry); //Ajout de la rotation

        //Rajouter la rotation comme animation
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                ry.setAxis(Rotate.Y_AXIS); //La rotation est fait sur l'axe y
                //le time est en nano seconds, permet de faire une rotation compléte tout les 15 secondes. Je divison 15 secondes(en nano) par 360 degrès (calcul approximatif qui m'a permit d'avoir une rotation de 15s)
                ry.setAngle(time/(1.5e10/360));

            }
        };
        animationTimer.start(); //démarrage de l'animation
    }
    //Function permettant de générer une sphère correspondant a un aéroport
    public Sphere createSphere(Aeroport a, Color color){
            Sphere redSphere = new Sphere(2); //Création de Sphère rayon 2
        //Application de la formule du tp pour avoir les coordonnées x,y,z de l'aeroport
            Double x = R*Math.cos(Math.toRadians(a.getLatitude() - 13)) * Math.sin(Math.toRadians(a.getLongitude()));
            Double y = -1*R*Math.sin(Math.toRadians(a.getLatitude() - 13));
            //Pour Z, la formule a été modifié comparé a celle dans l'énoncé pour avoir le resultat correcte
            Double z = -1*R*Math.cos(Math.toRadians(a.getLatitude() - 13)) * Math.cos(Math.toRadians(a.getLongitude()));
            //Création d'une translation basé sur les coordonnées récupéré
            Translate redT = new Translate(x,y,z);
            //Application de la translation sur la sphère
            redSphere.getTransforms().add(redT);
            //Application du couleur saisie en paramètre sur la sphère
            PhongMaterial m = new PhongMaterial();
            m.setDiffuseColor(color);
            redSphere.setMaterial(m);
            return redSphere;
    }
    public void displayRedSphere(Aeroport a){ //Création d'une sphère rouge pour un aéroport
        //Création et ensuite ajout de la sphère rouge dans le Groupe JavaFX
        this.getChildren().add(createSphere(a,Color.RED));
    }

    //Création des sphères jaunes pour les aéroport de départs des vols arrivant a un aéroport
    public void displayYellowSphere(ArrayList<Flight> f){ //nous avons la liste des vols en paramètre
        for (Flight flight : f) { //boucle foreach
            if(flight.getDeparture() != null) { //test pour voir si un vol n'a pas de aéroport de départ renseigné
                Sphere yS = createSphere(flight.getDeparture(), Color.YELLOW); //Création du sphère jaune sur l'aéroport du départ du vol
                this.getChildren().add(yS); //Ajout du sphére dans le Group JavaFx
                yellowSphere.add(yS); //ajout du sphére dans la liste yellowSphere
            }
        }
    }

    public Earth getEarth(){

        return this;
    }
}
