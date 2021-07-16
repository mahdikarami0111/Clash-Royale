package utils;
//not complete

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ProfileHandler {

    private static HashMap<String, String > usernameMap2password = new HashMap<>();
    private static final File file = new File("UnPw.txt");


    public static File getFile() {
        return file;
    }

    public static void update(){
        try(Scanner read = new Scanner(new FileReader(file))){
            while (read.hasNext()){
                String tmp = read.nextLine();
                String[] strings = tmp.split(":");
                add(strings[0],strings[1]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean userNameExists(String s){
       return usernameMap2password.containsKey(s);
    }

    public static boolean passwordMatch(String un, String pw){
        if (!userNameExists(un))
            return false;
        String s = usernameMap2password.get(un.trim());
        return pw.trim().equals(s);

    }

    public static void addUnPw(String un, String pw){
        if (un.trim().equals("") || pw.trim().equals(""))
            return;
        try(PrintWriter writer = new PrintWriter(new FileWriter(file,true))) {
            writer.printf("%s:%s:%d\n",un.trim(),pw.trim(),1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void add(String un, String pw){
        if (userNameExists(un)|| un.trim().equals("")|| pw.trim().equals(""))
            return;
        usernameMap2password.put(un.trim(),pw.trim());
    }




}
