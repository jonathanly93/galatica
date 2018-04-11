package _08final.mvc.view;

import font.FontLoader;
import images.SpriteTexLoader;
import mvc.controller.Game;
import mvc.model.Sprite;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

import mvc.controller.HighScore;


/**
 * Updated with the 4 required screens.
 */
public class GamePanel extends JPanel {

    private final static int OPTIONS_SIZE = 20;
    private final static int TEXT_DEFAULT_SIZE = 18;

    public final static Dimension BOTTOM_STATUS_DIM = new Dimension((int) GameFrame.FRAME_DIM.getWidth(), 30);
    public final static Dimension TOP_STATUS_DIM = new Dimension((int) GameFrame.FRAME_DIM.getWidth(), 100);


    public GamePanel() {
        this.setPreferredSize(GameFrame.FRAME_DIM);
    }

    private final static Font menu = FontLoader.getGameFontAtSize(120);
    private final static Font hScore = FontLoader.getGameFontAtSize(60);
    private final static Font activeGame = FontLoader.getGameFontAtSize(45);
    private final static Font gameOver = FontLoader.getGameFontAtSize(75);



    @Override
    public void paintComponent(Graphics g) {
        // Call the super paintComponent of the panel
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        if (Game.getViewState() == Game.ViewState.GAME_ACTIVE) {

            //If Game is active, draw the Stat Screen and Game Screen

            drawGameScreen(g2d);
            drawStatScreen(g2d);
        } else if (Game.getViewState() == Game.ViewState.MAIN_MENU) {    //Draw the Main Menu
            drawMainScreen(g2d);
        } else if (Game.getViewState() == Game.ViewState.GAME_OVER) {    //Draw the Game Over
            drawGameOver(g2d);
        } else if (Game.getViewState() == Game.ViewState.HIGH_SCORES)   //Draw the High Scores
            drawHighScores(g2d);
    }

    /**
     * Draws the informational screen of the game when it is active.
     */
    public void drawStatScreen(Graphics2D g) {
        //Draw a blue border
        g.setColor(Color.darkGray);
        g.fillRect(700, 0, 500, 900);
        int i;
        String sScore;
        String sLevel;
        String sHighScore;
        String sEnemiesLeft;
        String sLives;
        g.setFont(activeGame);
        g.setColor(Color.BLACK);
        sScore = "Score " + score;
        sLevel = "Level " + level;
        sHighScore = "High Score " + highScore;
        sEnemiesLeft = "Enemies Remaining  " + enemiesLeft;
        sLives = "Lives Remaining          " + lives;
        g.drawString(sScore, 710, 100);
        g.drawString(sLevel, 710, 150);
        g.drawString(sEnemiesLeft, 710, 350);
        g.drawString(sLives, 710, 400);

        g.setColor(Color.RED);
        g.drawString(sHighScore, 710, 50);

        for (i = 0; i < getLives(); i++) {
            g.drawImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.SHIP), 700+ 165* i, 700, this);
        }
    }

    /**
     * Draws the main game screen where the action is taking place.
     */
    public void drawGameScreen(Graphics2D g) {

        //Draw a black background
        g.setColor(Color.black);
        g.fillRect(0, 0, 700, 900);


        //Get all the sprites in the current game.
        Set<Sprite> sprites = Game.getSprites();

        for (Sprite sprite : sprites) {
            sprite.draw(g);
        }
    }

    /**
     * Draws the Main Menu Page.
     * Includes a Start Game Button and High Score Button
     */
    public void drawMainScreen(Graphics2D g)
    {
        g.setColor(Color.gray);
        g.fillRect(0, 0, 1200, 900);


        String sStartGame = "Start Game";
        String sHighScore = "High Scores";

        g.setFont(gameOver);
        g.setColor(Color.BLUE);
        g.drawString(sStartGame, 325, 600);
        g.drawString(sHighScore, 325, 750);

        Rectangle startGameButton = new Rectangle(300, 525, 450, 100);
        Rectangle highScoreButton = new Rectangle(300, 675, 450, 100);
        g.draw(startGameButton);
        g.draw(highScoreButton);

        g.drawImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.GALANGA_TITLE), -30, -50, this);


    }

    /**
     * Draws the HighScore Page
     * Displays the current high scores of the game.
     */
    public void drawHighScores(Graphics2D g) {
        g.setColor(Color.gray);
        g.fillRect(0, 0, 1200, 900);


        String sMainMenu = "Main Menu";
        String sHallOfFame = "HALL OF FAME";


        g.setFont(hScore);
        g.setColor(Color.BLUE);
        g.drawString(sMainMenu, 435, 800);

        Rectangle mainMenuButton = new Rectangle(425, 740, 300, 75);
        g.draw(mainMenuButton);

        HighScore highScore = new HighScore();
        String sHighScore = highScore.getHighscoreString();

        drawString(g, sHighScore, 75, 75);

        g.setFont(menu);
        g.drawString(sHallOfFame,240,100);
    }

    /**
     * Draws the GameOver Screen
     * Includes a Retry button and Main Menu
     */
    public void drawGameOver(Graphics2D g)
    {
        g.setColor(Color.gray);
        g.fillRect(0,0,1200,900);


        String sRetry = "Retry";
        String sMainMenu = "Main Menu";
        String sGameOver = "Game Over";
        String sScore = "Score " + score;
        String sLevel = "Level " + level;

        g.setFont(gameOver);
        g.setColor(Color.BLUE);
        g.drawString(sRetry,450,560);
        g.drawString(sMainMenu, 450, 710);
        g.drawString(sScore, 150, 350);
        g.drawString(sLevel, 150, 450);

        Rectangle startGameButton = new Rectangle(435,500,380,75);
        Rectangle highScoreButton = new Rectangle(435,650,380,75);
        g.draw(startGameButton);
        g.draw(highScoreButton);

        g.setFont(menu);
        g.drawString(sGameOver, 350,200);
    }


    /**
     * initializing information for scoreboard
     */
    public static int score = 0;
    public static int level = 0;
    public static int highScore = 0;
    public static int enemiesLeft = 0;
    public static int lives = 0;

    /**
     * retrieves information to update the gameboard live.
     *
     */
    public static int getLives() {return lives; }
    public static int getScore(){ return score;}
    public static void setLives(int x) {lives = x;}
    public static void setEnemiesLeft(int x) {enemiesLeft = x;}
    public static void setScore(int x) {score += x;}
    public static void zeroScore(){score = 0;}
    public static void setLevel(int x) {level += x;}
    public static void zeroLevel(){level = 0;}
    public static void setHighScore(int x) {highScore = x;}

    public void drawString(Graphics g, String s, int x, int y)
    {
        for(String line : s.split("\n"))
        {
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }
    }
}





