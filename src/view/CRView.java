package view;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.enums.Type;
import model.spells.Rage;
import model.units.Projectile;
import model.units.Troop;
import model.units.Unit;

import javafx.geometry.Point2D;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class CRView {
    private Canvas canvas;
    private GraphicsContext gc;

    private HashMap<Troop, CustomImageview> troops;
    private HashMap<Projectile,ImageView> projectiles;
    private HashMap<Unit,ImageView> buildings;

    private HashMap<Type, Image> projectileImages;
    private HashMap<Type,MutableImage> mutableImages;
    private HashMap<Type,Image> staticImages;

    private Group group;

    private static final String mapAddress = "";


    public CRView(Group group){
        this.group = group;

        troops = new HashMap<>();
        projectiles = new HashMap<>();
        buildings = new HashMap<>();
        projectileImages = new HashMap<>();

        mutableImages = new HashMap<>();
        staticImages = new HashMap<>();

        canvas = new Canvas(576,1024);
        this.group.getChildren().add(canvas);
        initializeImages();
        gc = canvas.getGraphicsContext2D();
        drawBackground();
    }

    public void removeTroop(Troop troop){
        Platform.runLater(() -> group.getChildren().remove(troops.remove(troop)));
    }

    public void removeBuilding (Unit unit){
        Platform.runLater(() -> group.getChildren().remove(buildings.remove(unit)));
    }

    public void removeProjectile(Projectile projectile){
        Platform.runLater(() -> group.getChildren().remove(projectiles.remove(projectile)));
    }

    public void drawBackground(){
        Image image = new Image("recourses/View/map/map.png");
        gc.drawImage(image,0,0);
    }

    public void spawnTroop(Troop troop){
        CustomImageview customImageview = new CustomImageview(mutableImages.get(troop.type));
        group.getChildren().add(customImageview);
        troops.put(troop,customImageview);
    }

    public void spawnProjectile(Projectile projectile){
        Platform.runLater(() ->{
            ImageView imageView = new ImageView(projectileImages.get(projectile.getUnitType()));
            imageView.setX(projectile.getCurrent().getY());
            imageView.setY(projectile.getCurrent().getX());
            imageView.setRotate(projectile.getRotate());
            group.getChildren().add(imageView);
            projectiles.put(projectile,imageView);
        });
    }


    public void spawnBuilding(Unit unit){
        ImageView imageView = new ImageView(staticImages.get(unit.type));
        imageView.setX(unit.getCurrentLocation().getY()*32);
        imageView.setY(unit.getCurrentLocation().getX()*32);
        group.getChildren().add(imageView);
        buildings.put(unit,imageView);
    }

    public void initializeImages(){
        for (Type type : Type.values()){
            if(!isMutable(type))continue;
            mutableImages.put(type,new MutableImage(type));
        }
        initializeProjectiles();
        initializeStaticImages();
    }

    public void initializeProjectiles(){
        projectileImages.put(Type.ARCHER,new Image("recourses/View/staticImages/projectiles/ARCHER.png"));
        projectileImages.put(Type.WIZARD,new Image("recourses/View/staticImages/projectiles/WIZARD.gif"));
        projectileImages.put(Type.BABY_DRAGON,new Image("recourses/View/staticImages/projectiles/BABY_DRAGON.gif"));
        projectileImages.put(Type.INFERNO_TOWER,new Image("recourses/View/staticImages/projectiles/INFERNO_TOWER.gif"));
        projectileImages.put(Type.CANNON,new Image("recourses/View/staticImages/projectiles/CANNON.png"));
        projectileImages.put(Type.KING_TOWER,new Image("recourses/View/staticImages/projectiles/KING_TOWER.png"));
        projectileImages.put(Type.QUEEN_TOWER,new Image("recourses/View/staticImages/projectiles/QUEEN_TOWER.png"));
    }

    public void initializeStaticImages(){
        for(Type type : Type.values()){
            if(isMutable(type)||type == Type.FIREBALL||type==Type.RAGE||type==Type.ARROWS)continue;
            staticImages.put(type,new Image("recourses/View/staticImages/nonProjectiles/"+type.name()+".png"));
        }
        projectileImages.put(Type.ARROWS,new Image("recourses/View/staticImages/nonProjectiles/ARROWS.gif"));
        projectileImages.put(Type.FIREBALL,new Image("recourses/View/staticImages/nonProjectiles/FIREBALL.gif"));
        staticImages.put(Type.RAGE,new Image("recourses/View/staticImages/nonProjectiles/RAGE.gif"));
    }

    public void changeState(Troop troop){
        troops.get(troop).changeState(troop.getState());
    }

    public void render(){
        Platform.runLater(() -> {
            troops.forEach((troop,image) ->{
                image.setX((troop.getPixelLocation().getY())+image.getxScale());
                image.setY((troop.getPixelLocation().getX())+image.getyScale());
            } );
            projectiles.forEach((projectile,image) -> {
                image.setX(projectile.getCurrent().getY());
                image.setY(projectile.getCurrent().getX());
            });
        });
    }

    public boolean isMutable(Type type){
        return type != Type.CANNON && type != Type.INFERNO_TOWER && type != Type.KING_TOWER && type != Type.QUEEN_TOWER && type != Type.ARROWS && type != Type.FIREBALL && type != Type.RAGE;
    }

    public boolean hasProjectile(Type type){
        return type == Type.WIZARD || type == Type.BABY_DRAGON || type == Type.INFERNO_TOWER || type == Type.CANNON || type == Type.ARCHER;
    }

    public HashMap<Type, MutableImage> getMutableImages() {
        return mutableImages;
    }

    public HashMap<Type, Image> getProjectileImages() {
        return projectileImages;
    }

    public Group getGroup() {
        return group;
    }

    public void rage(Point2D location){
        ImageView imageView = new ImageView(staticImages.get(Type.RAGE));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                imageView.setX(location.getY()*32-100);
                imageView.setY(location.getX()*32-100);
                group.getChildren().add(imageView);
            }
        });
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
             Platform.runLater(new Runnable() {
                 @Override
                 public void run() {
                     group.getChildren().remove(imageView);
                 }
             });
            }
        };
        Timer t = new Timer();
        t.schedule(task,2420);
    }
}
