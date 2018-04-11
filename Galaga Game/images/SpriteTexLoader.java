package package _08final.imagesl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class easily loads the textures for the sprites in the game
 *
 * @version  1.0
 * @author Lamont Samuels
 * @since  11-13-16
 */

public class SpriteTexLoader {

    private static SpriteTexLoader sInstance = new SpriteTexLoader();

    public enum SpriteTex {
        GALANGA_TITLE,
        SHIP,
        BULLET_SHIP,
        BULLET_ENEMY,
        BLUE_FIGHTER,
        RED_FIGHTER,
        ENEMY_EXPLOSION1,
        ENEMY_EXPLOSION2,
        ENEMY_EXPLOSION3,
        ENEMY_EXPLOSION4,
        ENEMY_EXPLOSION5,
        SHIP_EXPLOSION1,
        SHIP_EXPLOSION2,
        SHIP_EXPLOSION3,
        EE,
        BLANK

    }

    private SpriteTexLoader() {}
    /**
     * Retrieves the file path for the a Sprite texture in the images file
     * @param sprite - the sprite file path to retrieve
     */
    private static String getSpriteFile(SpriteTex sprite) {

        String file = "";
        switch (sprite) {
            case SHIP:
                file = "models/ship.png";
                break;
            case BLUE_FIGHTER:
                file = "models/blue_fighter.png";
                break;
            case RED_FIGHTER:
                file = "models/red_fighter.png";
                break;
            case GALANGA_TITLE:
                file = "models/galaga.png";
                break;
            case BULLET_SHIP:
                file = "models/ship_bullet.png";
                break;
            case BULLET_ENEMY:
                file = "models/enemy_bullet.png";
                break;
            case ENEMY_EXPLOSION1:
                file = "explosion/enemy/frame1.png";
                break;
            case ENEMY_EXPLOSION2:
                file = "explosion/enemy/frame2.png";
                break;
            case ENEMY_EXPLOSION3:
                file = "explosion/enemy/frame3.png";
                break;
            case ENEMY_EXPLOSION4:
                file = "explosion/enemy/frame4.png";
                break;
            case ENEMY_EXPLOSION5:
                file = "explosion/enemy/frame5.png";
                break;
            case SHIP_EXPLOSION1:
                file = "explosion/ship/frame1.png";
                break;
            case SHIP_EXPLOSION2:
                file = "explosion/ship/frame2.png";
                break;
            case SHIP_EXPLOSION3:
                file = "explosion/ship/frame3.png";
                break;
            case BLANK:
                file = "models/blank.png";
                break;
        }
        return file;
    }

    /**
     * Returns a buffered image from the images directory of a particular sprite
     * @param sprite - the sprite texture to load
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public static BufferedImage load(SpriteTex sprite)  throws IllegalArgumentException {

        if (sprite == null){
            throw new IllegalArgumentException("Sprite texture parameter must not be null");
        }
        BufferedImage img = null;
        try {
            String file = getSpriteFile(sprite);
            img = ImageIO.read(sInstance.getClass().getResource(file));
        }catch (IOException e){
            System.out.print("Could not open texture :" + sprite.toString());
            System.exit(1);
        }
        return img;
    }

}
