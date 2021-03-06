package model.units;

import model.enums.Type;

import javafx.geometry.Point2D;

/**
 * projectile class to manage projectiles created by spells and unit's attacks
 */
public class Projectile {
    private static final double speed = 6;
    private Point2D start;
    private Point2D end;
    private Point2D current;
    private Type unitType;
    private double xStep;
    private double yStep;
    private boolean hasReached;
    private double customSpeed;

    /**
     *
     * @param start where projectile starts from
     * @param end where projectile ends(destination)
     * @param unitType units have different projectiles
     */
    public Projectile(Point2D start,Point2D end,Type unitType){
        this.start =start;
        this.end = end;
        customSpeed = 0;
        current = new Point2D(0,0);
        current = current.add(start.getX()*32,start.getY()*32);
        this.unitType = unitType;
        calculateStep();

    }

    /**
     *
     * @param start where projectile starts from
     * @param end where projectile ends(destination)
     * @param unitType units have different projectiles
     * @param customSpeed projectiles custom speed
     */
    public Projectile(Point2D start,Point2D end,Type unitType,int customSpeed){
        this.start =start;
        this.end = end;
        this.customSpeed = customSpeed;
        current = new Point2D(0,0);
        current = current.add(start.getX()*32,start.getY()*32);
        this.unitType = unitType;
        calculateStep();

    }

    /**
     * move the projectile based on the steps calculated
     */
    public void move(){
        current = current.add(xStep,yStep);
    }

    /**
     * calculates how many pixels and in which direction should the projectile move
     */
    public void calculateStep(){
        if(end.getY()-start.getY() == 0){
            yStep = 0;
            if(end.getX()>start.getX()){
                xStep = (int) Math.round(speed);
            }
            else {
                xStep = -1 * (int) Math.round(speed);
            }
        }
        else {
            double m = Math.atan( (end.getX()-start.getX()) / (end.getY()-start.getY()));
            if (customSpeed == 0){
                xStep = speed * Math.sin(m);
                yStep = speed * Math.cos(m);
            }
            else {
                xStep = customSpeed * Math.sin(m);
                yStep = customSpeed * Math.cos(m);
            }
            if(end.getX()<start.getX() && xStep>0)xStep *= -1;
            if(end.getY()<start.getY() && yStep>0)yStep *= -1;
            if(end.getX()>start.getX() && xStep<0)xStep *= -1;
            if(end.getY()>start.getY() && yStep<0)yStep *= -1;
        }
    }

    /**
     *
     * @return unit type
     */

    public Type getUnitType() {
        return unitType;
    }

    public Point2D getStart() {
        return start;
    }

    public Point2D getEnd() {
        return end;
    }
    /**
     *
     * @return current location
     */
    public Point2D getCurrent() {
        return current;
    }

    /**
     *
     * @return true if projectile has reached dest
     */
    public boolean hasReached(){
        if(xStep<=0 && yStep>=0)return (Math.ceil(current.getX()/32)== end.getX() && Math.floor(current.getY()/32)==end.getY());
        if(xStep>=0 && yStep>=0)return (Math.floor(current.getX()/32)== end.getX() && Math.floor(current.getY()/32)==end.getY());
        if(xStep<=0 && yStep<=0)return (Math.ceil(current.getX()/32)== end.getX() && Math.ceil(current.getY()/32)==end.getY());
        if(xStep>=0 && yStep<=0)return (Math.floor(current.getX()/32)== end.getX() && Math.ceil(current.getY()/32)==end.getY());
        return false;
    }

    /**
     * calculates how many degrees should the projectile turn based on its direction
     * @return projectile direction
     */
    public double getRotate(){
        if((end.getY()-start.getY()) == 0){
            if((end.getX()-start.getX())<0)return-90;
            else return -270;
        }

        if(end.getX() - start.getX() == 0){
            if((end.getY()-start.getY())<0)return 180;
            else return 0;
        }

        double degrees = Math.toDegrees(Math.atan( (end.getX()-start.getX()) / (end.getY()-start.getY())));
        if((end.getX()-start.getX())<0 && (end.getY()-start.getY())<0){
            degrees+=180;
        }else if((end.getX()-start.getX()) > 0 && (end.getY()-start.getY())<0){
            degrees +=180;
        }
        return degrees;
    }
}
