package _08final.mvc.model;

import images.SpriteTexLoader;
import javafx.geometry.Point2D;
import java.awt.*;

/**
 * Bullet fired by red Fighters
 */
public class EnemyBullet extends Sprite{

    public final static Dimension BULLET_DIM = new Dimension(10,10);
    private static double speed = 200.0;

    public EnemyBullet(Point2D p)
    {
        super(p , BULLET_DIM, SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BULLET_ENEMY));
    }

    /**
     * increases the bullet speed as game goes on.
     * @param speed
     */
    public void changeSpeed(double speed){
        this.speed = speed;
    }
    public void update(double deltaTime) {
        this.mPos = this.mPos.subtract(0,-EnemyBullet.speed * deltaTime);
    }
}

