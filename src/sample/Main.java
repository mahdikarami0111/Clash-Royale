package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.enums.Cell;
import model.enums.CellType;
import model.enums.TargetType;
import model.game.Player;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.Map;
import model.game.sharedRecourses.View;
import model.informationObjects.UnitInformation;
import view.CRView;


import java.io.*;
import java.util.ArrayList;

public class Main {

//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
//    }


    public static void main(String[] args) {
//        UnitInformation unitInformation = new UnitInformation(0,0,0.4,0,6,40,TargetType.GROUND,TargetType.BOTH,false,5,1);
//
//        int[] hp = {800,880,968,1064,1168};
//        int[] damage = {20,22,24,26,29};
//
//        String name = "INFERNO_TOWER";
////
//        for(int  i =1 ; i<6 ; i++){
//            File file = new File("./src/recourses/UnitInformation/"+name+"/"+i+".ser");
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            unitInformation.setHp(hp[i-1]);
//            unitInformation.setDamage(damage[i-1]);
//
//            try {
//                FileOutputStream fos = new FileOutputStream("./src/recourses/UnitInformation/"+name+"/"+i+".ser");
//                ObjectOutputStream oos = new ObjectOutputStream(fos);
//                oos.writeObject(unitInformation);
//                oos.close();
//                fos.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                FileInputStream fis = new FileInputStream("./src/recourses/UnitInformation/"+name+"/"+i+".ser");
//                ObjectInputStream ois = new ObjectInputStream(fis);
//                UnitInformation u =(UnitInformation) ois.readObject();
//                u.print();
//                ois.close();
//                fis.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }
}
