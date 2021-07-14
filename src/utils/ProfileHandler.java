package utils;
//not complete

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ProfileHandler {
    private static  ArrayList<String > usernames = new ArrayList<>();
    private static  ArrayList<String> passwords = new ArrayList<>();
    private static final File file = new File("UnPw.txt");


    public static File getFile() {
        return file;
    }

    public static void update(){
        try(Scanner read = new Scanner(new FileReader(file));
            PrintWriter writer = new PrintWriter(new FileWriter(file,true))){
            usernames.clear();
            passwords.clear();
            while (read.hasNext()){
                String tmp = read.nextLine();
                String[] strings = tmp.split(":");
                usernames.add(strings[0].trim());
                passwords.add(strings[1].trim());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean userNameExists(String s){
       return usernames.contains(s);
    }

    public static boolean passwordMatch(String un, String pw){
        if (!userNameExists(un))
            return false;
        int index = 0 ;
        while (!usernames.get(index).equals(un)){
            index++;
        }
        String s = passwords.get(index);
        return pw.trim().equals(s);
    }

    public static void addUnPw(String un, String pw){
        if (un.trim().equals("") || pw.trim().equals(""))
            return;
        try(PrintWriter writer = new PrintWriter(new FileWriter(file,true))) {
            writer.printf("%s:%s\n",un.trim(),pw.trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
