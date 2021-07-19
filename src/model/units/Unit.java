package model.units;

import model.enums.*;
import model.game.Cell;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.Map;
import model.informationObjects.UnitInformation;

import javafx.geometry.Point2D;

import java.util.Timer;
import java.util.TimerTask;

public  class Unit {
    protected int hp;
    protected int damage;
    protected double attackSpeed;
    protected double range;
    protected TargetType unitType;
    protected TargetType targetType;
    protected boolean areaSplash;
    private int cost;
    private int count;
    protected long timer;


    protected CellType team;
    protected Point2D currentLocation;
    protected State state;
    public Type type;


    public Unit(Type type, CellType team, Point2D location){
        setData(type);
        this.team = team;
        currentLocation = location;
        this.type = type;
        timer = 0;
    }

    public boolean checkForAttack(){
        for (int i =0;i<32;i++){
            for(int j = 0;j<18;j++){
                CellType temp = Map.getMap()[i][j].getCellType();
                Unit enemy = Map.getMap()[i][j].getUnit();
                if(currentLocation.distance(new Point2D(i,j)) < range && temp != team&&temp!=CellType.BLOCK&&temp!=CellType.PATH && attackTypeMatch(enemy)){
                    return true;
                }
            }
        }
        return false;
    }

    public void setState(State state){
        if(this.state == State.ATTACKING && state !=State.ATTACKING){
            timer=((long) attackSpeed*1000) - 30;
        }
        this.state = state;
    }

    public synchronized void attack(){
        if(state != State.ATTACKING){
            setState(State.ATTACKING);
        }
        timer +=30;
        if(timer >= attackSpeed*1000){
            timer = 0;
            Point2D target = null;
            Point2D temp;
            for(int i = 0;i<32;i++){
                for (int j = 0;j<18;j++){
                    temp = new Point2D(i,j);
                    CellType t = Map.getMap()[i][j].getCellType();
                    Unit enemy = Map.getMap()[i][j].getUnit();
                    if(currentLocation.distance(temp)<range &&  t != team && t != CellType.BLOCK && t != CellType.PATH && attackTypeMatch(enemy)){
                        if(target == null){
                            target = temp;
                            continue;
                        }
                        if(currentLocation.distance(target)>currentLocation.distance(temp)){
                            target = temp;
                        }
                    }
                }
            }
            if(target != null){
                if(areaSplash){
                    for(int i =(int) target.getX()-1;i<target.getX()-1;i++){
                        for(int j =(int) target.getX()-1;j<target.getX()-1;j++){
                            CellType t = Map.getMap()[i][j].getCellType();
                            Unit unit = Map.getMap()[i][j].getUnit();
                            if(t != team && t != CellType.BLOCK && t != CellType.PATH &&unit != null && attackTypeMatch(unit)){
                                unit.decreaseHp(damage);
                            }
                        }
                    }
                }
                else {
                    Map.getMap()[(int)target.getX()][(int)target.getY()].getUnit().decreaseHp(damage);
                }
                if(range>=2){
                    Game.gameManager().getPlayer(team).summonProjectile(new Projectile(currentLocation,target,type));
                }
                if(type == Type.INFERNO_TOWER){
                    damage += (Game.gameManager().getLvl()+8.5)*0.4;
                }
            }
        }
    }

    public void setData(Type type){
        UnitInformation info = Game.gameManager().getUnitInformationHashMap().get(type);
        this.hp = info.hp;
        this.damage = info.damage;
        this.attackSpeed = info.attackSpeed;
        this.range = info.range;
        this.targetType = info.targetType;
        this.unitType = info.unitType;
        this.areaSplash = info.areaSplash;
        this.cost = info.cost;
        this.count = info.count;

    }



    public CellType getTeam() {
        return team;
    }

    public State getState() {
        return state;
    }

    public int getHp() {
        return hp;
    }

    public Point2D getCurrentLocation() {
        return currentLocation;
    }

    public void decreaseHp(int damage){
        hp -= damage;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean attackTypeMatch(Unit enemy){
        if(this.targetType == TargetType.BOTH)return true;

        if(this.type == Type.GIANT){
            return enemy instanceof Building || enemy instanceof KingTower || enemy instanceof QueenTower;
        }

        return this.targetType == enemy.unitType;
    }

    public int getDamage() {
        return damage;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setAttackSpeed(double attackSpeed) {
        System.out.println("changed");
        this.attackSpeed = attackSpeed;
    }
}
