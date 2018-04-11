package _08final.mvc.controller;

import java.util.*;
import java.io.*;

public class HighScore {

    // Array to hold the scores of a a list of high scores.
    private ArrayList<Score> scores;

    // The file name to be read
    private static String highScore = "GALAGA_HIGHSCORE.txt";

    //Initialising an in and outputStream for working with the file
    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;

    public HighScore() {
        //initialising the scores-arraylist
        scores = new ArrayList<Score>();
    }


    public ArrayList<Score> getScores() {
        loadScoreFile();
        sortScore();
        return scores;
    }

    /**
     * Sorts the score in order of highest to lowest
     */
    private void sortScore() {
        CompareScore comparer = new CompareScore();
        Collections.sort(scores, comparer);
    }

    /**
     * Adds to the Score File
     */
    public void addScore(String name, int score) {
        loadScoreFile();
        scores.add(new Score(name, score));
        updateScoreFile();
    }

    public void loadScoreFile() {
        try {
            inputStream = new ObjectInputStream(new FileInputStream(highScore));
            scores = (ArrayList<Score>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("[Laad] FNF Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Laad] IO Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[Laad] CNF Error: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Laad] IO Error: " + e.getMessage());
            }
        }
    }

    public void updateScoreFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(highScore));
            outputStream.writeObject(scores);
        } catch (FileNotFoundException e) {
            System.out.println("[Update] FNF Error: " + e.getMessage() + ",the program will try and make a new file");
        } catch (IOException e) {
            System.out.println("[Update] IO Error: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Update] Error: " + e.getMessage());
            }
        }
    }

    /**
     * List only the top 10 high scores
     * @return
     */

    public int getHighScore(int i)
    {
        ArrayList<Score> scores;
        scores = getScores();
        return scores.get(i).getScore();
    }



    public String getHighscoreString() {
        String highscoreString = "";
        int max = 10;

        ArrayList<Score> scores;
        scores = getScores();

        int i = 0;
        int x = scores.size();


        if (x > max) {
            x = max;
        }

        /**
         * create proper allignment of scores.
         */
        while (i < x) {
            int y = 100; // spacing correction
            y = y - String.valueOf(scores.get(i).getScore()).length() - String.valueOf(i+1).length() - scores.get(i).getUser().length(); // create definite spacing
            String space = ""; // the amount of space needed per line
            for(int z = 0; z < y; z++)
            {
                space += ' ';
            }
            if(i < 9) {
                highscoreString += " "+(i + 1) +" " +  scores.get(i).getUser() + space + scores.get(i).getScore() + "\n";
                i++;
            }
            else
            {
                highscoreString += (i + 1) + " " + scores.get(i).getUser() + space + scores.get(i).getScore() + "\n";
                i++;
            }
        }
        return highscoreString;
    }

}
