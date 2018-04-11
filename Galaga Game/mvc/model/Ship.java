package _08final.mvc.model;

import images.SpriteTexLoader;
import javafx.geometry.Point2D;
import java.awt.*;

/**
 * The player. Can move left and right and is bounded by the screen.
 * The player starts at position (350,825).
 */
public class Ship extends Sprite {

    public final static Dimension SHIP_DIM = new Dimension(35,35);

    private enum Direction {
        LEFT,
        RIGHT,
        FROZEN,
    }



    private Direction mMoveDirection = Direction.FROZEN;

    private final static double speed = 125.0;

    public Ship() {
        super(new Point2D(350,825), SHIP_DIM, SpriteTexLoader.load(SpriteTexLoader.SpriteTex.SHIP));
    }
    public void update(double deltaTime) {

            switch (this.mMoveDirection) {
                case LEFT:
                    if(mPos.getX() >= 0) {
                        this.mPos = this.mPos.subtract(Ship.speed * deltaTime, 0);
                        break;
                    }
                case RIGHT:
                    if(mPos.getX() <= 665) {
                        this.mPos = this.mPos.add(Ship.speed * deltaTime, 0);
                        break;
                    }
            }
    }
    public void stopMoving() {
        this.mMoveDirection = Direction.FROZEN;
    }
    public void moveLeft() {
        this.mMoveDirection =  Direction.LEFT;
    }
    public void moveRight() {
        this.mMoveDirection =  Direction.RIGHT;
    }

}

