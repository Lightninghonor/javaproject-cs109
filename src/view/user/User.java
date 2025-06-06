package view.user;

import java.io.Serializable;
import java.util.Map;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private boolean isGuest;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isGuest = false;
    }

    public User() {
        this("Guest", "");
        this.isGuest = true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

}