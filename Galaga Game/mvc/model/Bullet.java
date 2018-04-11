package _08final.mvc.model;

import images.SpriteTexLoader;
import javafx.geometry.Point2D;
import java.awt.*;

/**
 * Bullets Fired by Ship
 */
public class Bullet extends Sprite{

    public final static Dimension BULLET_DIM = new Dimension(10,10);
    private final static double speed = 400.0;

    public Bullet(Point2D p)
    {
        super(p , BULLET_DIM, SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BULLET_SHIP));
    }

    /**
     * updates the traveling trajectory of the bullet
     * @param deltaTime in game time counter
     */
    public void update(double deltaTime) {
        this.mPos = this.mPos.subtract(0,Bullet.speed * deltaTime);

        }

}

