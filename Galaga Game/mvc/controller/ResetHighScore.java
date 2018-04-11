package _08final.mvc.controller;

public class ResetHighScore {
    public static void main(String[] args) {
        HighScore hs = new HighScore();

        hs.addScore("JON",100000);
        hs.addScore("JEF",90000);
        hs.addScore("SAM",80000);
        hs.addScore("STV",70000);
        hs.addScore("DAN",60000);
        hs.addScore("LUK",5000);
        hs.addScore("JUL",4000);
        hs.addScore("LFT",3000);
        hs.addScore("CRT",2000);
        hs.addScore("KEV",100);
    }
}

