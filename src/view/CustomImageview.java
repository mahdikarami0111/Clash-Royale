package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.enums.State;
import model.enums.Type;
import model.units.Unit;

/**
 * set size and scale of an image and its state(moving left or right, attacking)
 */
public class CustomImageview extends ImageView {
    private Image moveR;
    private Image moveL;
    private Image attack;
    private int size;
    private int xScale;
    private int yScale;

    /**
     *
     * @param mutableImage initializes this class based on a MutableImage object
     */

    public CustomImageview(MutableImage mutableImage){
        moveR = mutableImage.getMoveR();
        moveL = mutableImage.getMoveL();
        attack = mutableImage.getAttack();
        this.size = mutableImage.getSizeScale();
        xScale = mutableImage.getxScale();
        yScale = mutableImage.getyScale();
        this.setPreserveRatio(true);
        this.setFitWidth(size);
        this.setImage(null);
    }

    /**
     *
     * @param state is the state to be set
     */
    public void changeState(State state){
        switch (state){
            case MOVING_RIGHT:
                this.setImage(moveR);
                break;
            case MOVING_LEFT:
                this.setImage(moveL);
                break;
            case ATTACKING:
                this.setImage(attack);
                break;
        }
    }

    /**
     *
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * @return x scale
     */
    public int getxScale() {
        return xScale;
    }

    /**
     *
     * @return y scale
     */
    public int getyScale() {
        return yScale;
    }
}
