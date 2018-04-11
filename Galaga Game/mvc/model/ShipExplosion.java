package _08final.mvc.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import images.SpriteTexLoader;
import javafx.geometry.Point2D;
import javax.swing.*;
import java.awt.*;

/**
 * Explosion sprite when player Ship is destroyed
 */
public class ShipExplosion extends Sprite {

    public final static Dimension EXPLOSION_DIM = new Dimension(35, 35);

    /**
     * Displays an explosion where player Ship was eliminated
     * @param x: x coordinate of Ship
     * @param y y coordinate of Ship
     */
    public ShipExplosion(double x, double y) {
        super(new Point2D(x, y), EXPLOSION_DIM, SpriteTexLoader.load(SpriteTexLoader.SpriteTex.SHIP_EXPLOSION1));

        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.ENEMY_EXPLOSION3));
                move(750,750);
            }
        };
        Timer timer = new Timer(100,taskPerformer);
        timer.setRepeats(false);
        timer.start();
    }

    public Point2D getPos() {
        return this.mPos;
    }

    public void update(double deltaTime) {
    }
}









