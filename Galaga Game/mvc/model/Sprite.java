package _08final.mvc.model;

import javafx.geometry.Point2D;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by lamont on 11/20/16.
 */
public abstract class Sprite {

    /** The dimensions of the sprite */
    private Dimension mDim;

    /** The position of the sprite */
    protected Point2D mPos;

    /** The texture for the sprite */
    protected BufferedImage mTex;

    private boolean isAlive = true;

    public Sprite(Point2D initPos, Dimension dim, BufferedImage texture) {
        this.mPos = initPos;
        this.mDim = dim;
        this.mTex = texture;
    }
    public Point2D getPos() {
        return this.mPos;
    }

    public Double getX() {
        return this.mPos.getX();
    }

    public Double getY() {
        return this.mPos.getY();
    }

    public double getHeight() {
        return this.mDim.getHeight();
    }

    public double getWidth() {
        return this.mDim.getWidth();
    }

    public void setLocation(double x, double y) {
        this.mPos = new Point2D(x,y);
    }

    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        g2d.drawImage(this.mTex, (int) this.mPos.getX(), (int) this.mPos.getY(), (int) this.mDim.getWidth(),
                                 (int) this.mDim.getHeight(), null);
    }

    /**
     * Changes the sprite of an image
     * @param texture: buffered sprite that will be used to chnage.
     */
    public void resetImage(BufferedImage texture) {
        this.mTex = texture;
    }

    /**
     * changes the mobility of the sprite
     * @param x: changes the x direction
     * @param y changes the y direction
     */
    public void move(double x, double y) {
        this.mPos = this.mPos.subtract(x,y);
    }

    /**
     * changes status to notAlive
     * @return
     */
    public boolean notAlive() {
        return isAlive = false;

    }

    /**
     * checks to see if status is alive
     * @return
     */
    public boolean isAlive() {
        return isAlive;
    }
    abstract void update (double deltaTime);
}
