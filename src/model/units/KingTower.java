package model.units;


import javafx.geometry.Point2D;
import model.enums.CellType;
import model.enums.State;
import model.enums.Type;
import model.game.Player;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.Map;

/**
 * king tower extends unit
 */
public class KingTower extends Unit {

    private boolean canAttack;
    private Unit lockedTarget;


    /**
     *
     * @param team is towers team
     * @param location is towers location
     */
    public KingTower(CellType team, Point2D location) {
        super( Type.KING_TOWER, team, location);
        lockedTarget = null;
    }

    /**
     *
     * @return true if king tower has been activated and enemy is in range
     */
    @Override
    public boolean checkForAttack(){
        Player player = Game.gameManager().getPlayer(team);
        if(!player.queenIsDead() && hp == Game.gameManager().getUnitInformationHashMap().get(type).hp)return false;

        for (int i =0;i<32;i++){
            for(int j = 0;j<18;j++){
                CellType temp = Map.getMap()[i][j].getCellType();
                Unit enemy = Map.getMap()[i][j].getUnit();
                if(currentLocation.add(2,2).distance(new Point2D(i,j)) <= range && temp != team&&temp!=CellType.BLOCK&&temp!=CellType.PATH && attackTypeMatch(enemy))return true;
            }
        }
        return false;
    }


    /**
     * king's speciall attack method , locks on one target until it dies
     */
    @Override
    public void attack(){
        if(state != State.ATTACKING)setState(State.ATTACKING);
        timer += 30;
        if(timer >= attackSpeed*1000){
            timer = 0;
            if(lockedTarget == null || lockedTarget.getHp()<=0){
                for(int i = 0; i<32;i++){
                    for(int j=0;j<18;j++){
                        CellType temp = Map.getMap()[i][j].getCellType();
                        Unit enemy = Map.getMap()[i][j].getUnit();
                        if(currentLocation.add(2,2).distance(new Point2D(i,j)) <= range &&temp!=CellType.PATH&&temp!=CellType.BLOCK&&temp!= team){
                            lockedTarget = enemy;
                            break;
                        }
                    }
                }
            }
            if(lockedTarget != null){
                lockedTarget.decreaseHp(damage);
                Game.gameManager().getPlayer(team).summonProjectile(new Projectile(currentLocation,lockedTarget.getCurrentLocation(),type));
            }
        }
    }
}
