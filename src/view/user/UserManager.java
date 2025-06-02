package view.user;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class UserManager {
    private List<User> users;
    private User currentUser;
    private final String filePath;

    public UserManager(String filePath) {
        this.filePath = filePath;
        this.users = new ArrayList<>();
        loadUsers();
    }

    public void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            users = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No saved data found. Creating a new user list.");
        }
    }

    public void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String password) {
        if (checkUsernameExists(username)) {
            return false;
        }
        User user = new User(username, password);
        users.add(user);
        saveUsers();
        return true;
    }

    public boolean checkUsernameExists(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)&&user.getUsername()!=null) {
                return true;
            }
        }
        return false;
    }
//新增
    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

}