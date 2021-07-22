package view;

import javafx.scene.image.Image;
import model.enums.Type;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * units movement and attacks images(gifs)
 */
public class MutableImage {
    private Image attack;
    private Image moveR;
    private Image moveL;
    private int sizeScale;
    private int xScale;
    private int yScale;

    /**
     *
     * @param type is unit(card) type
     *initialize based on card type
     */
    public MutableImage(Type type){
        File file = new File("./src/recourses/View/mutableImages/"+type.name()+"/scale.txt");
        try {
            Scanner scanner = new Scanner(file);
            xScale = Integer.parseInt(scanner.nextLine());
            yScale = Integer.parseInt(scanner.nextLine());
            sizeScale = Integer.parseInt(scanner.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        attack = new Image("recourses/View/mutableImages/"+type.name()+"/A.gif");
        moveR = new Image("recourses/View/mutableImages/"+type.name()+"/R.gif");
        moveL = new Image("recourses/View/mutableImages/"+type.name()+"/L.gif");
    }

    public Image getAttack() {
        return attack;
    }

    public Image getMoveR() {
        return moveR;
    }

    public Image getMoveL() {
        return moveL;
    }

    public int getSizeScale() {
        return sizeScale;
    }

    public int getxScale() {
        return xScale;
    }

    public int getyScale() {
        return yScale;
    }
}
