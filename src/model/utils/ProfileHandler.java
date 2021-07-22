package model.utils;

import model.enums.Type;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * helper class to handle profile and account,
 * can be used to access player data
 */
public class ProfileHandler {
    private static HashMap<String , Account> usernameMap2Account;
    private static Account currentUser;
    private static int level;
    private static ArrayList<Type> cards;

    /**
     *
     * @return hashmap that maps each username to an account
     * @throws NullPointerException if hash map is not initialized
     */
    public static HashMap<String, Account> getUsernameMap2Account() throws NullPointerException {
        if (usernameMap2Account == null)
            throw new NullPointerException();
        return usernameMap2Account;
    }

    /**
     * initialize the hash map: if there are no prior accounts, a new hashmap is made,
     * otherwise the hashmap is read from file
     */
    public static void initialize(){
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("./src/recourses/Data/accounts.bin"))){
            usernameMap2Account = (HashMap<String, Account>) inputStream.readObject();
        } catch (FileNotFoundException | EOFException e) {
            usernameMap2Account = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param account new account to be added to hashmap
     */
    public static void addAccount(Account account){
        assert account != null;
        String un = account.getUsername().trim();
        if (usernameMap2Account.containsKey(un))
            return;
        usernameMap2Account.put(un,account);
    }

    /**
     *
     * @param un is username
     * @param pw is password
     * @return true if account exists and password is correct
     */
    public static boolean passwordMatch(String un,String pw){
        if (!usernameMap2Account.containsKey(un.trim()))
            return false;
        String correct = usernameMap2Account.get(un.trim()).getPassWord();
        return correct.trim().equals(pw.trim());
    }

    /**
     *
     * @param un is user name
     * @return true if username is taken
     */
    public static boolean usernameExists(String un){
        return usernameMap2Account.containsKey(un.trim());
    }

    /**
     * write hashmap to file
     */
    public static void writeAccounts(){
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("./src/recourses/Data/accounts.bin"))){
            outputStream.writeObject(usernameMap2Account);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return current user
     */
    public static Account getCurrentUser() {
        return currentUser;
    }

    /**
     *
     * @param currentUser is the current user
     */
    public static void setCurrentUser(Account currentUser) {
        ProfileHandler.currentUser = currentUser;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        ProfileHandler.level = level;
    }

    public static ArrayList<Type> getCards() {
        return cards;
    }

    public static void setCards(ArrayList<Type> cards) {
        ProfileHandler.cards = cards;
    }
}