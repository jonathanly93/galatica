package _08final.font;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by lamont on 11/25/16.
 */
public class FontLoader {

    private static Font sGameFont = null;
    private static FontLoader sInstance = new FontLoader();

    public FontLoader() { }

    /**
     * Generates a the main game font with the specified size
     * @param size - the font size to generate for the game
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public static Font getGameFontAtSize(int size) throws IllegalArgumentException {
        if (sGameFont == null) {
            initLoader();
        }
        if(size <= 0){
            throw new IllegalArgumentException("The size must be greater than zero.");
        }
        return sGameFont.deriveFont(Font.PLAIN,size);
    }

    /**
     * Initiliazes the game font
     */
    private static void initLoader() {
        try {
            sGameFont = Font.createFont(Font.TRUETYPE_FONT, new File(sInstance.getClass().getResource("ARCADECLASSIC.TTF").getFile()));
            GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sGameFont);
        } catch (IOException|FontFormatException e) {
            //Handle exception
            System.out.println("Could not open Arcade Classic Font");
            System.exit(1);
        }
    }

}
