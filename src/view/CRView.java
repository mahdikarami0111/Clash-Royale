package view;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.enums.CellType;
import model.enums.Type;
import model.game.sharedRecourses.Game;
import model.spells.Rage;
import model.units.KingTower;
import model.units.Projectile;
import model.units.Troop;
import model.units.Unit;

import javafx.geometry.Point2D;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * initialize the game view and handle it
 */
public class CRView {
    private Canvas canvas;
    private GraphicsContext gc;

    private HashMap<Troop, CustomImageview> troops;
    private HashMap<Projectile,ImageView> projectiles;
    private HashMap<Unit,ImageView> buildings;

    private HashMap<Type, Image> projectileImages;
    private HashMap<Type,MutableImage> mutableImages;
    private HashMap<Type,Image> staticImages;
    private HashMap<Unit,Rectangle> hpBars;

    private TextField elixir;
    private TextField playerCrown;
    private TextField botCrown;
    private TextField timer;

    private Group group;

    private static final String mapAddress = "";

    /**
     *
     * @param group initialize the object based on an object that extends group
     */
    public CRView(Group group){
        this.group = group;

        troops = new HashMap<>();
        projectiles = new HashMap<>();
        buildings = new HashMap<>();
        projectileImages = new HashMap<>();
        hpBars = new HashMap<>();

        mutableImages = new HashMap<>();
        staticImages = new HashMap<>();

        canvas = new Canvas(576,1024);
        this.group.getChildren().add(canvas);
        initializeImages();
        gc = canvas.getGraphicsContext2D();
        drawBackground();
    }

    /**
     *
     * @param troop is troop to be removed
     */
    public void removeTroop(Troop troop){
        Platform.runLater(() -> group.getChildren().remove(troops.remove(troop)));
    }

    /**
     *
     * @param unit building to be removed
     */
    public void removeBuilding (Unit unit){
        Platform.runLater(() -> group.getChildren().remove(buildings.remove(unit)));
    }

    /**
     *
     * @param projectile is projectile to be removed
     */
    public void removeProjectile(Projectile projectile){
        Platform.runLater(() -> group.getChildren().remove(projectiles.remove(projectile)));
    }

    /**
     * set the background image
     */
    public void drawBackground(){
        Image image = new Image("recourses/View/map/map.png");
        gc.drawImage(image,0,0);
    }

    /**
     *
     * @param troop is the troop to be deployed
     */
    public void spawnTroop(Troop troop){
        CustomImageview customImageview = new CustomImageview(mutableImages.get(troop.type));
        group.getChildren().add(customImageview);
        troops.put(troop,customImageview);
    }

    /**
     *
     * @param projectile is the projectile to be made
     */
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


    /**
     *
     * @param unit is building to be made
     */
    public void spawnBuilding(Unit unit){
        ImageView imageView = new ImageView(staticImages.get(unit.type));
        imageView.setX(unit.getCurrentLocation().getY()*32);
        imageView.setY(unit.getCurrentLocation().getX()*32);
        group.getChildren().add(imageView);
        buildings.put(unit,imageView);
    }

    /**
     * initialize images
     */
    public void initializeImages(){
        for (Type type : Type.values()){
            if(!isMutable(type))continue;
            mutableImages.put(type,new MutableImage(type));
        }
        initializeProjectiles();
        initializeStaticImages();
    }

    /**
     * initialize projectiles
     */
    public void initializeProjectiles(){
        projectileImages.put(Type.ARCHER,new Image("recourses/View/staticImages/projectiles/ARCHER.png"));
        projectileImages.put(Type.WIZARD,new Image("recourses/View/staticImages/projectiles/WIZARD.gif"));
        projectileImages.put(Type.BABY_DRAGON,new Image("recourses/View/staticImages/projectiles/BABY_DRAGON.gif"));
        projectileImages.put(Type.INFERNO_TOWER,new Image("recourses/View/staticImages/projectiles/INFERNO_TOWER.gif"));
        projectileImages.put(Type.CANNON,new Image("recourses/View/staticImages/projectiles/CANNON.png"));
        projectileImages.put(Type.KING_TOWER,new Image("recourses/View/staticImages/projectiles/KING_TOWER.png"));
        projectileImages.put(Type.QUEEN_TOWER,new Image("recourses/View/staticImages/projectiles/QUEEN_TOWER.png"));
    }

    /**
     * initial static images
     */
    public void initializeStaticImages(){
        for(Type type : Type.values()){
            if(isMutable(type)||type == Type.FIREBALL||type==Type.RAGE||type==Type.ARROWS)continue;
            staticImages.put(type,new Image("recourses/View/staticImages/nonProjectiles/"+type.name()+".png"));
        }
        projectileImages.put(Type.ARROWS,new Image("recourses/View/staticImages/nonProjectiles/ARROWS.gif"));
        projectileImages.put(Type.FIREBALL,new Image("recourses/View/staticImages/nonProjectiles/FIREBALL.gif"));
        staticImages.put(Type.RAGE,new Image("recourses/View/staticImages/nonProjectiles/RAGE.gif"));
    }

    /**
     *
     * @param troop is the troop that the state will be changed
     */
    public void changeState(Troop troop){
        troops.get(troop).changeState(troop.getState());
    }

    /**
     * render the frame
     */
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
        updateHpBars();
    }

    /**
     *
     * @param type the card to be checked
     * @return true if it is mutable(not a spell or building/tower)
     */
    public boolean isMutable(Type type){
        return type != Type.CANNON && type != Type.INFERNO_TOWER && type != Type.KING_TOWER && type != Type.QUEEN_TOWER && type != Type.ARROWS && type != Type.FIREBALL && type != Type.RAGE;
    }

    /**
     *
     * @param type card to be checked
     * @return true if the attack has projectile
     */
    public boolean hasProjectile(Type type){
        return type == Type.WIZARD || type == Type.BABY_DRAGON || type == Type.INFERNO_TOWER || type == Type.CANNON || type == Type.ARCHER;
    }

    /**
     *
     * @return mutable images
     */

    public HashMap<Type, MutableImage> getMutableImages() {
        return mutableImages;
    }

    /**
     *
     * @return projectile images
     */
    public HashMap<Type, Image> getProjectileImages() {
        return projectileImages;
    }

    /**
     *
     * @return group
     */
    public Group getGroup() {
        return group;
    }

    /**
     *
     * @param location where rage is deployed
     */

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

    public void start(){

    }

    /**
     *
     * @param elixir amount of elixir to be set
     */

    public void setElixir(TextField elixir) {
        this.elixir = elixir;
    }

    /**
     *
     * @param playerCrown the crowns player has earned
     */
    public void setPlayerCrown(TextField playerCrown) {
        this.playerCrown = playerCrown;
    }

    /**
     *
     * @param botCrown the crowns bot has earned
     */

    public void setBotCrown(TextField botCrown) {
        this.botCrown = botCrown;
    }

    /**
     *
     * @param timer set timer
     */
    public void setTimer(TextField timer) {
        this.timer = timer;
    }

    /**
     *
     * @param time is seconds passed
     */
    public void updateTimer(int time){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                timer.setText(""+time/60+":"+time%60);
            }
        });
    }

    /**
     *
     * @param elixirCount new elixir amount
     */
    public void updateElixir(int elixirCount){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                elixir.setText(elixirCount+"");
            }
        });
    }


    /**
     *
     * @param count new bot crown
     */
    public void updateBotCrown(int count){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                botCrown.setText(count+"");
            }
        });
    }

    /**
     *
     * @param count new player crown
     */
    public void updatePlayerCrown(int count){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                playerCrown.setText(count+"");
            }
        });
    }

    /**
     *
     * @return a hash map that maps each unit to hp bar
     */
    public HashMap<Unit, Rectangle> getHpBars() {
        return hpBars;
    }

    /**
     * update hp bars
     */
    public void updateHpBars(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                hpBars.forEach((k,v) ->{
                    if(k instanceof KingTower){
                        v.setWidth(((double) k.getHp()/ (double) Game.gameManager().getUnitInformationHashMap().get(k.type).hp)*96);
                    }
                    else {
                        v.setWidth(((double) k.getHp()/ (double) Game.gameManager().getUnitInformationHashMap().get(k.type).hp)*64);
                    }
                });
            }
        });
    }

    /**
     *
     * @param unit the unit which hp bar will be set to
     * initialize hp bars
     */
    public void neHpBar(Unit unit){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(unit == Game.gameManager().getPlayer().getQueenTowers()[0]){
                    Rectangle r  =new Rectangle();
                    r.setFill(Color.RED);
                    r.setArcHeight(10);
                    r.setArcWidth(10);
                    r.setStroke(Color.BLACK);
                    r.setX(32);
                    r.setY(29*32 + 5);
                    r.setHeight(10);
                    r.setWidth(64);
                    hpBars.put(unit,r);
                    group.getChildren().add(r);
                }
                if(unit == Game.gameManager().getPlayer().getQueenTowers()[1]){
                    Rectangle r  =new Rectangle();
                    r.setFill(Color.RED);
                    r.setArcHeight(10);
                    r.setArcWidth(10);
                    r.setStroke(Color.BLACK);
                    r.setX(14*32);
                    r.setY(29*32 + 5);
                    r.setHeight(10);
                    r.setWidth(64);
                    hpBars.put(unit,r);
                    group.getChildren().add(r);
                }
                if(unit == Game.gameManager().getPlayer().getKingTower()){
                    Rectangle r  =new Rectangle();
                    r.setFill(Color.RED);
                    r.setArcHeight(10);
                    r.setArcWidth(10);
                    r.setStroke(Color.BLACK);
                    r.setX(7*32);
                    r.setY(31*32 + 5);
                    r.setHeight(10);
                    r.setWidth(96);
                    hpBars.put(unit,r);
                    group.getChildren().add(r);
                }
                if(unit == Game.gameManager().getPlayer(CellType.BOT).getQueenTowers()[0]){
                    Rectangle r  =new Rectangle();
                    r.setFill(Color.RED);
                    r.setArcHeight(10);
                    r.setArcWidth(10);
                    r.setStroke(Color.BLACK);
                    r.setHeight(10);
                    r.setWidth(64);
                    r.setX(32);
                    r.setY(2*32+17);
                    hpBars.put(unit,r);
                    group.getChildren().add(r);
                }
                if(unit == Game.gameManager().getPlayer(CellType.BOT).getQueenTowers()[1]){
                    Rectangle r  =new Rectangle();
                    r.setFill(Color.RED);
                    r.setArcHeight(10);
                    r.setArcWidth(10);
                    r.setStroke(Color.BLACK);
                    r.setHeight(10);
                    r.setWidth(64);
                    r.setX(14 * 32);
                    r.setY(2*32+17);
                    hpBars.put(unit,r);
                    group.getChildren().add(r);
                }
                if(unit == Game.gameManager().getPlayer(CellType.BOT).getKingTower()){
                    Rectangle r  =new Rectangle();
                    r.setFill(Color.RED);
                    r.setArcHeight(10);
                    r.setArcWidth(10);
                    r.setStroke(Color.BLACK);
                    r.setX(7*32);
                    r.setY(17);
                    r.setHeight(10);
                    r.setWidth(96);
                    hpBars.put(unit,r);
                    group.getChildren().add(r);
                }
            }
        });
    }
}
