package view.user;

import java.io.Serializable;

public class PlayerRanking implements Serializable {
    private String username;
    private int steps;
    private int time;

    public PlayerRanking(String username, int steps, int time) {
        this.username = username;
        this.steps = steps;
        this.time = time;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}