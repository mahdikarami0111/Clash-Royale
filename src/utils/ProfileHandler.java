package utils;
//not complete

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ProfileHandler {
    private static HashMap<String , Account> usernameMap2Account;

    public static HashMap<String, Account> getUsernameMap2Account() throws NullPointerException {
        if (usernameMap2Account == null)
            throw new NullPointerException();
        return usernameMap2Account;
    }

    public static void initialize(){
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("accounts.bin"))){
            usernameMap2Account = (HashMap<String, Account>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            usernameMap2Account = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void addAccount(Account account){
        assert account != null;
        String un = account.getUsername().trim();
        if (usernameMap2Account.containsKey(un))
            return;
        usernameMap2Account.put(un,account);
    }

    public static boolean passwordMatch(String un,String pw){
        if (!usernameMap2Account.containsKey(un.trim()))
            return false;
        String correct = usernameMap2Account.get(un.trim()).getPassWord();
        return correct.trim().equals(pw.trim());
    }

    public static boolean usernameExists(String un){
        return usernameMap2Account.containsKey(un.trim());
    }

    public static void writeAccounts(){
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("accounts.bin"))){
            outputStream.writeObject(usernameMap2Account);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}