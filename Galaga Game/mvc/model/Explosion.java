package _08final.mvc.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import images.SpriteTexLoader;
import javafx.geometry.Point2D;
import javax.swing.*;
import java.awt.*;

/**
 * Explosion sprite from eliminating fighters
 */
public class Explosion extends Sprite {

    public final static Dimension EXPLOSION_DIM = new Dimension(35, 35);

    public Explosion(double x, double y) {
        super(new Point2D(x, y), EXPLOSION_DIM, SpriteTexLoader.load(SpriteTexLoader.SpriteTex.ENEMY_EXPLOSION5));

        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.ENEMY_EXPLOSION2));
                resetImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.ENEMY_EXPLOSION3));
                resetImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.ENEMY_EXPLOSION4));
                resetImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.ENEMY_EXPLOSION5));
                resetImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BLANK));
                move(750,750);

                }
        };
        Timer timer = new Timer(100,taskPerformer);
        timer.setRepeats(false);
        timer.start();

        }

    /**
     *
     * @return position of explosion
     */
    public Point2D getPos() {
        return this.mPos;
    }

    public void update(double deltaTime) {
    }
}









