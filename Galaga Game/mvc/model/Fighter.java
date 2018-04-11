package _08final.mvc.model;

import images.SpriteTexLoader;
import javafx.geometry.Point2D;
import sounds.Sound;

import java.awt.*;
import java.util.Random;

/**
 * Fighter blue units that dive down at players.
 */
public class Fighter extends Sprite {

    public final static Dimension SHIP_DIM = new Dimension(35, 35);

    private static double speed = 50;
    private boolean moveRight = true;
    private boolean moveDown = false;
    private boolean repeat = false;
    private boolean initiate = true;
    private Random r = new Random();
    private int distance = 0;
    private double startingPoint = mPos.getX();

    public Fighter(double x, double y) {
        super(new Point2D(x, y), SHIP_DIM, SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BLUE_FIGHTER));
    }

    public Point2D getPos() {
        return this.mPos;
    }

    /**
     * increase speed of fighters as the game progresses
     * @param speed: sets the speed of the fighter unit
     */
    public void changeSpeed(double speed)
    {
        this.speed = speed;
    }

    /**
     * Movement of the fighter unit.
     * Fighters start at the bottom right corner of the screen and move to the upper middle area.
     * Fighters will then move left and right according to their current position generating a
     * distance value.
     * After a certain amount of distance is generated, the fighter unit will be triggered to fly down on the player.
     * The Fighter unit will then have it's distance gauge resetted and return back to the top of the screen
     * @param deltaTime
     */
    public void update(double deltaTime) {
        int result = r.nextInt(4);

        /**
         * Initiated only occurs at the beginning of the level.
         * Moves the unit from the bottom right to the top middle
         */
        if(initiate)
        {
            if(this.mPos.getX() > 375) {
                this.mPos = this.mPos.subtract(35 * 5 * deltaTime, 35 * 8 * deltaTime);
                distance = distance + 2 * r.nextInt(10);
            }
            else if(this.mPos.getX() < 375) {
                this.mPos = this.mPos.subtract(-35 * 5 * deltaTime, 35 * 8 * deltaTime);
                distance = distance + 2 * r.nextInt(10);
                moveRight = false;
            }

            if(distance > 600) {
                initiate = false;
                distance = 0;
            }
        }
        /**
         * First phase of the loop. Moves right if the unit is closer to the right side.
         * Once unit reaches the edge, turn around and move the other way.
         */

        if (moveRight && !moveDown && !initiate) {
            if(repeat) {
                this.mPos = this.mPos.subtract(0, -Fighter.speed  * 7 * deltaTime);
                distance = distance + 10 * r.nextInt(3);

                if(distance > 200) {
                    repeat = false;
                }
            }

            if(this.mPos.getX() - startingPoint >= -250) {
                moveRight = false;
                distance = distance + 200 * r.nextInt(3);
            }

            this.mPos = this.mPos.subtract(-Fighter.speed * 1.5 * result *deltaTime, -Fighter.speed * .25 *deltaTime);
        }
        /**
         * Starts moving left if closer to the left side or when the unit has already touch the right side of the screen
         */
        else if (!moveRight && !initiate) {
            if (this.mPos.getX() - startingPoint <= -750) {
                distance = distance + 200 * r.nextInt(3);
                moveRight = true;

                /**
                 * If enough distance is generate, queues up for Phase two where the fighter unit will dive down
                 * on the player.
                 *
                 * If not enough distance is generated, the loop is repeated.
                 */
                if(distance > 600) {
                    moveDown = true;
                    Sound.playSoundEffect(Sound.SoundEffect.ENEMY_FLYING);
                }
                else {
                    moveDown = false;
                }
            }
            this.mPos = this.mPos.subtract(Fighter.speed * 1.5 * result * deltaTime, Fighter.speed * .25 *deltaTime);
        }
        /**
         * Phase two: the fighter unit dives at the player.
         */
        else if(moveDown && !repeat && !initiate) {
            this.mPos = this.mPos.subtract(Fighter.speed  * .25 * deltaTime, -Fighter.speed  * 7 * deltaTime);

            /**
             * Once the fighter unit reaches the bottom of the screen, it resets it's position at the top of the screen.
             */
            if(this.mPos.getY() > 835) {
                distance = 0;
                setLocation(this.mPos.getX(),0);
                moveDown = false;
                repeat = true;
            }
        }
    }
}






