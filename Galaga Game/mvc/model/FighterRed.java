package _08final.mvc.model;

import images.SpriteTexLoader;
import javafx.geometry.Point2D;
import java.awt.*;
import java.util.Random;

/**
 * Fighter Red Units that shoot at the player.
 */
public class FighterRed extends Sprite {
    public final static Dimension SHIP_DIM = new Dimension(35, 35);

    private static double speed = 50;
    private boolean moveRight = true;
    private int low = 0;
    private int high = 200;
    private Random r = new Random();


    public FighterRed(double x, double y) {
        super(new Point2D(x, y), SHIP_DIM, SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER));
    }

    public Point2D getPos() {
        return this.mPos;
    }

    /**
     * Increase speed of unit as the game progresses
     * @param speed: changes the speed at which the unit moves
     */
    public void changeSpeed(double speed)
    {
        this.speed = speed;
    }

    /**
     * random chance that the unit will fire.
     * @return true if number generated is 1 causing the Fighter to fire a bullet
     */
    public boolean fire() {
       int result = r.nextInt(high - low) + low;

        if(result == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * The Fighter red unit only moves from the left side of the screen to the right side.
     * If it starts from the left side, it will move right.
     * If it starts on the right side, it will move left.
     * @param deltaTime
     */
    public void update(double deltaTime)
    {

        if (moveRight == true) {

            if(this.mPos.getX() >= 675)
            {
                moveRight = false;
            }

            this.mPos = this.mPos.subtract(-FighterRed.speed * deltaTime, 0);
        }

        else if (moveRight == false) {
            if (this.mPos.getX() <= 0)
            {
                moveRight = true;
            }
            this.mPos = this.mPos.subtract(FighterRed.speed * deltaTime, 0);
        }
    }
}


