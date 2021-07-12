package View;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import model.enums.Type;
import model.units.Projectile;
import model.units.Troop;
import model.units.Unit;

import java.util.HashMap;

public class CRView extends Group {
    private Canvas canvas;
    private GraphicsContext gc;

    private HashMap<Troop, CustomImageview> troops;
    private HashMap<Projectile,ImageView> projectiles;
    private HashMap<Unit,ImageView> buildings;

    private HashMap<Type,CustomImageview> soldierImages;
    private HashMap<Type,ImageView> staticImages;

    private static final String mapAddress = "";


    public CRView(){
    }

    public void removeTroop(Troop troop){

    }

    public void removeBuilding (Unit unit){

    }

    public void drawBackground(){
        //use canvas and gc tro draw back ground map (picture)
    }

    public synchronized void spawnTroop(Troop troop){
        //put new unit in HashMap
        //CustomImageview customImageview = new CustomImageview(unit.type);
    }

    public synchronized void spawnProjectile(Projectile projectile){
        //creates new projectile based on type
        //hashmap put(projectile imageview(type)
    }
    public synchronized void spawnBuilding(Unit unit){
        buildings.put(unit,staticImages.get(unit.type));
    }

    public synchronized void changeState(Unit unit){
        //gets the unit value form hashmap and changes image to state
    }

    public void render(){
        //for all projectiles and units in hashmap
        //set their images x and y to units x and y
    }
}
