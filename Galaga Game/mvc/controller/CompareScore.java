package _08final.mvc.controller;

import java.util.Comparator;

/**
 * Compares two scores to sort
 */
public class CompareScore implements Comparator<Score> {
    public int compare(Score s1, Score s2) {

        int sc1 = s1.getScore();
        int sc2 = s2.getScore();

        if (sc1 > sc2){
            return -1;
        }else if (sc1 < sc2){
            return +1;
        }else{
            return 0;
        }
    }
}
