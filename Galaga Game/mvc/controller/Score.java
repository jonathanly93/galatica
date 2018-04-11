package _08final.mvc.controller;

import java.io.Serializable;

public class Score  implements Serializable {

    private String user;
    private int score;

    public String getUser() {
        return user;
    }

    public int getScore() {
        return score;
    }

    /**
     * A score is defined by a user and a scoore
     * @param user: name of user who placed the score
     * @param score: score amount
     */
    public Score(String user, int score) {
        this.user = user;
        this.score = score;
    }
}