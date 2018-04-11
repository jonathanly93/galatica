package _08final.mvc.controller;



import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static mvc.controller.Game.ViewState.*;

/**
 * MouseListener class to allow navigation of  the game.
 *
 */
public class MouseInput implements MouseListener {

    @Override
    public  void mouseClicked(MouseEvent e)
    {

    }
    @Override
    public void mousePressed(MouseEvent e)
    {
        int mx = e.getX();
        int my = e.getY();

        /**
         * Mouse Events for Main Menu
         */
        if(Game.getViewState() == MAIN_MENU) {
            //Start Game Button
            if (mx >= 300 && mx <= 750) {
                if (my >= 560 && my <= 650) {

                    Game.setViewState(GAME_ACTIVE);
                }
            }

            //High Score Button
            if (mx >= 300 && mx <= 750) {
                if (my >= 700 && my <= 800) {

                    Game.setViewState(HIGH_SCORES);
                }
            }
        }

        /**
         * Mouse High Scores
         */
        else if(Game.getViewState() == HIGH_SCORES) {

            //Return to Main Menu
            if (mx >= 430 && mx <= 730) {
                if (my >= 770 && my <= 845) {

                    Game.setViewState(MAIN_MENU);
                }
            }
        }

        /**
         * Mouse Events for GAME OVER Screen
         */
        else if(Game.getViewState() == GAME_OVER) {
            //Replay Game Button
            if (mx >= 440 && mx <= 815) {
                if (my >= 530 && my <= 610) {

                    Game.setViewState(GAME_ACTIVE);
                }
            }

            //Main Menu Button
            if (mx >= 440 && mx <= 815) {
                if (my >= 680 && my <= 760) {

                    Game.setViewState(MAIN_MENU);
                }
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e)
    {

    }
    @Override
    public  void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }
}
