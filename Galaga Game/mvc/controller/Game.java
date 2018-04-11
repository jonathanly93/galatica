package _08final.mvc.controller;

import images.SpriteTexLoader;
import mvc.model.*;
import mvc.view.GameFrame;
import mvc.view.GamePanel;
import sounds.Sound;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by lamont on 11/20/16.
 */
public class Game implements Runnable, KeyListener {

    /**
     * Represents the JFrame for the game
     */
    private GameFrame mGameFrame;

    /**
     * Represents the drawing delay between frames
     */
    public final static int DRAW_DELAY = 45; // this is in milliseconds

    /**
     * The thread that handles the render loop for the game
     */
    private Thread mRenderThread;

    /**
     * Represents the ship in the game
     */
    private static Ship sShip;
    private static Fighter sFighter;
    private static FighterRed sFighterRed;
    private static Bullet sBullet;
    private static EnemyBullet sEBullet;
    private static Explosion sExplosion;
    private static ShipExplosion sSExplosion;

    private static ArrayList<Bullet> bulletList = new ArrayList<>();
    private static ArrayList<EnemyBullet> enemyBulletList = new ArrayList<>();
    private static ArrayList<Fighter> fighterList = new ArrayList<>();
    private static ArrayList<FighterRed> fighterRedList = new ArrayList<>();
    private static ArrayList<Explosion> explosionList = new ArrayList<>();
    private static ArrayList<Sprite> totalEnemiesList = new ArrayList<>();

    private static HighScore hs = new HighScore();
    private static int noEnemies;
    private static int currentLevel = 0;
    private static int lives = 3;

    private static boolean highScore = false;
    private static boolean inGame = false;
    private static boolean restartGame = false;


    /**
     * List of enemies that need to be rendered
     */
    private static Set<Sprite> sSprites = new HashSet<Sprite>();

    // You may want to use this code for adding new bullets to the game. This data structure is thread safe so you
    // can add bullets (or even enemies) from different threads. Just reaplce "_YOUR_BULLET_CLASS" with the class name
    // for your bullet class.
    //private static CopyOnWriteArrayList<_YOUR_BULLET_CLASS> sShipBullets = new CopyOnWriteArrayList<_YOUR_BULLET_CLASS>();

    /**
     * The current state of the view
     **/
    private static ViewState sViewState = ViewState.MAIN_MENU;

    /**
     * The View state for the game
     */
    public enum ViewState {
        MAIN_MENU,
        GAME_ACTIVE,
        HIGH_SCORES,
        GAME_OVER,
        NEW_HIGH
    }

    /**
     * Change the current state of the game
     * @param vState: directs which screen to be displayed
     */
    public static void setViewState (ViewState vState)
    {
        sViewState = vState;
    }

    public Game() {
        this.mGameFrame = new GameFrame(this);
        mGameFrame.setSize(1200,900);
        sShip = new Ship();
        sBullet = new Bullet(sShip.getPos());
        sSprites.add(sShip);

        int currentHighScore = hs.getHighScore(0);

        GamePanel.setHighScore(currentHighScore);
        GamePanel.setLives(3);
        Sound.playSoundEffect(Sound.SoundEffect.THEME_SONG);

    }
    /**
     * Starts the thread that will handle the render loop for the game
     */
    private void startRenderLoopThread() {
        //Check to make sure the render loop thread has not begun
        if (this.mRenderThread == null) {
            //All threads that are created in java need to be passed a Runnable object.
            //In this case we are making the "Runnable Object" the actual game instance.
            this.mRenderThread = new Thread(this);
            //Start the thread
            this.mRenderThread.start();
        }
    }

    /**
     * This represents the method that will be called for a Runnable object when a thread starts.
     * In this case, this run method represents the render loop.
     */
    public void run() {

        //Make this thread a low priority such that the main thread of the Event Dispatch is always is
        //running first.
        this.mRenderThread.setPriority(Thread.MIN_PRIORITY);

        //Get the current time of rendering this frame
        long elapsedTime = System.currentTimeMillis();

        long currentTime = 0;
        long lastTime = 0;
        long deltaTime = 0;

        // this thread animates the scene
        while (Thread.currentThread() == this.mRenderThread) {

            currentTime = System.currentTimeMillis();

            if (lastTime == 0) {
                lastTime = currentTime;
                deltaTime = 0;
            } else {
                deltaTime = currentTime - lastTime;
                lastTime = currentTime;
            }

            final double MILS_TO_SECS = 0.001f;
            double deltaTimeInSecs = deltaTime * MILS_TO_SECS;

            /****** THIS IS WHERE YOU WANT TO BEGIN IMPLEMENTING THE GAME **********/

            /************* Update game HERE
             * - Move the game models
             * - Check for collisions between the bullet, or fighters and the ship
             * - Check whether we should move to a new level potentially.
             */

            //Update the state of the ship
            sShip.update(deltaTimeInSecs);

            //Update the bullets for Ship
            for (Bullet ammo : bulletList) {
                ammo.update(deltaTimeInSecs);
            }

            //Update the enemies Bullets
            for(EnemyBullet ammo : enemyBulletList) {
                ammo.update(deltaTimeInSecs);
            }

            // Updates enemy lists
            for (Fighter enemy : fighterList) {
                enemy.update(deltaTimeInSecs);
            }
            for (FighterRed enemy : fighterRedList) {
                enemy.update(deltaTimeInSecs);
            }

            //Check for collisions (Enemies & Player)

            ArrayList<Fighter> fighterListCopy= new ArrayList<>(fighterList); //creates copy list
            for(Fighter enemy : fighterListCopy) {
                if (Collide(enemy, sShip)) {
                    lives = lives - 1;
                    GamePanel.setLives(lives);  //resets lives on game panel

                    sSExplosion = new ShipExplosion(sShip.getX(), sShip.getY());  //creates an explosion at collision
                    sSprites.add(sSExplosion);
                    Sound.playSoundEffect(Sound.SoundEffect.SHIP_EXPLOSION);

                    /**
                     * checks for collision with enemy bullets
                     */
                    for (EnemyBullet ammo2 : enemyBulletList) {
                        ammo2.setLocation(1000, 750);
                        ammo2.resetImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BLANK));
                    }
                    enemyBulletList.clear();

                    sShip.setLocation(350, 825); //respawn point
                }
            }
            //Check for collisions (Enemies & Bullets)
            /**
             * Store both types of fighters into a new list
             */
            for (Fighter enemy : fighterList) {
                totalEnemiesList.add(enemy);
            }
            for (FighterRed enemy : fighterRedList) {
                totalEnemiesList.add(enemy);
            }

            ArrayList<Sprite>totalEnemiesListCopy = new ArrayList<>(totalEnemiesList); //creates copy of total enemy list
            ArrayList<Bullet> bulletListCopy= new ArrayList<>(bulletList);      //create copy of bullet list

            /**
             * Cycles through bullet list and total enemy list to see if there are collisions.
             * If a collision occurs, remove the fighter and bullet and decrease the enemy
             * count by 1.
             */
            for(Bullet ammo : bulletListCopy) {
                for (Sprite enemy : totalEnemiesListCopy) {
                    if (Collide(ammo, enemy) && enemy.isAlive()) {
                        try {
                            sExplosion = new Explosion(enemy.getX(), enemy.getY());
                            enemy.notAlive();
                            enemy.resetImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BLANK));
                            enemy.move(1000, 750);

                            fighterList.remove(enemy);
                            fighterRedList.remove(enemy);
                            GamePanel.setScore(200);
                            ammo.setLocation(1000, 750);
                            ammo.update(0);

                            explosionList.add(sExplosion);

                            sSprites.add(sExplosion);
                            Sound.playSoundEffect(Sound.SoundEffect.ENEMY_KILLED);

                        } catch (ConcurrentModificationException e) {
                            continue;
                        }

                        noEnemies = noEnemies - 1;            //decreases number of enemies when fighters are destroyed
                        GamePanel.setEnemiesLeft(noEnemies);
                    }
                }
            }
            /**
             * updates the explosions
             */
            for(Explosion explode : explosionList)
            {
                explode.update(deltaTimeInSecs);
            }

            //Check for collisions (Player & Bullets)

            /**
             * Generate fire rate from red fighters
             */
            for(FighterRed red : fighterRedList)
            {
                if(red.fire()) {
                    sEBullet = new EnemyBullet(red.getPos());
                    sEBullet.update(deltaTimeInSecs);
                    sEBullet.changeSpeed(200 + currentLevel*25);
                    sSprites.add(sEBullet);
                    enemyBulletList.add(sEBullet);
                }
            }

            ArrayList<EnemyBullet>enemyBulletListCopy = new ArrayList<>(enemyBulletList);   //copy of enemy bullet list

            /**
             * Checks for collision with enemy bullts. Cycles through
             * enemy bullts to detect if player is hit.
             */
            for(EnemyBullet ammo : enemyBulletListCopy)
            {
                if(Collide(ammo,sShip))
                {
                    lives = lives - 1;
                    GamePanel.setLives(lives);
                    ammo.setLocation(1000,750);      //removes ammo on impact
                    ammo.update(0);             //makes ammo stationary
                    ammo.resetImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BLANK)); //mask the sprite in the background
                    Sound.playSoundEffect(Sound.SoundEffect.SHIP_EXPLOSION);

                    sSExplosion = new ShipExplosion(sShip.getX(),sShip.getY());  //displays explosion on impact
                    sSprites.add(sSExplosion);

                    /**
                     * Ensures that all enemy bullet is removed when player dies.
                     * Removing this clause will cause random bullets to remain on
                     * when player dies.
                     */
                    for(EnemyBullet ammo2 : enemyBulletList)
                    {
                        ammo2.setLocation(1000,750);
                        ammo2.resetImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BLANK));
                    }
                    enemyBulletList.clear();

                    sShip.setLocation(350,825);       //respawn location
                }
            }
            //Check status of the Game
            if(sViewState == ViewState.GAME_ACTIVE) {
                inGame = true;
                update();
            }
            else{
                inGame = false;
            }
            if(sViewState == ViewState.GAME_OVER)
            {
                if( GamePanel.getScore() > hs.getHighScore(9)) {

                    JFrame frame = new JFrame("High Score");
                    String name = JOptionPane.showInputDialog(frame, "Congratulations! You've" +
                                " reached the High Scores. Please Enter your Name: ");
                    hs.addScore(name, GamePanel.getScore());
                    sViewState = ViewState.HIGH_SCORES;
                }
            }

            //Redraw the game frame with to visually show the updated game state.
            this.mGameFrame.draw();

            try {
                /** We want to ensure that the drawing time is at least the DRAW_DELAY we specified. */
                elapsedTime += DRAW_DELAY;
                Thread.sleep(Math.max(0, elapsedTime - currentTime));
            } catch (InterruptedException e) {
                //If an interrupt occurs then you can just skip this current frame.
                continue;
            }
        }
    }
    /***
     * Generates all the drawable sprites for the game currently
     * @return an arraylist of all the drawable sprites in the game
     */
    public static Set<Sprite> getSprites() {
        return sSprites;
    }

    public static ViewState getViewState() {
        return sViewState;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int nKey = e.getKeyCode();

        switch (nKey) {
            case KeyEvent.VK_LEFT:
                sShip.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                sShip.moveRight();
                break;
            case KeyEvent.VK_SPACE:
                break;
        }
    }
    public void keyReleased(KeyEvent e) {

        int nKey = e.getKeyCode();

        switch (nKey) {
            case KeyEvent.VK_LEFT:
                sShip.stopMoving();
                break;
            case KeyEvent.VK_RIGHT:
                sShip.stopMoving();
                break;
            case KeyEvent.VK_SPACE:                     //fires shot on spacebar release
                sBullet = new Bullet(sShip.getPos());
                sSprites.add(sBullet);
                bulletList.add(sBullet);
                Sound.playSoundEffect(Sound.SoundEffect.FIRE_BULLET);
                break;
        }
    }

    public void keyTyped(KeyEvent e) {}

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() { // uses the Event dispatch thread from Java 5 (refactored)
            public void run() {
                try {
                    //Construct the game controller
                    Game game = new Game();
                    //Start the render loop for the game
                    game.startRenderLoopThread();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Collidion detection: Checks to see if the sprite of one unit crosses the sprite of a second unit
     * @param r1: Sprite 1
     * @param r2: Sprite 2
     * @return true if collision occurs
     */
    public boolean Collide(Sprite r1, Sprite r2) {
        if (r1.getX() < r2.getX() + r2.getWidth() &&
                r1.getX() + r1.getWidth() > r2.getX() &&
                r1.getY() < r2.getY() + r2.getHeight() &&
                r1.getHeight() + r1.getY() > r2.getY()) {
            //collision was detected
            return true;
        }
        return false;
    }

    /**
     * Updates the current status of the game.
     * Redraws the map and increases the level
     * after all enemies are eliminated. Difficulty
     * is increased by speed per level.
     */
    public void update()
    {
        if(restartGame && inGame)
        {
            //Restarts the game
            GamePanel.zeroScore();
            GamePanel.zeroLevel();
            restartGame = false;
        }
        if(getLives() <= 0)
        {
            sViewState = ViewState.GAME_OVER;

            for(Fighter fighter : fighterList)
            {
                fighter.resetImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BLANK));
                fighter.move(1000, 750);
            }

            for(FighterRed fighterRed : fighterRedList)
            {
                fighterRed.resetImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BLANK));
                fighterRed.move(1000, 750);
            }

            for(EnemyBullet ammo : enemyBulletList)
            {
                ammo.resetImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BLANK));
                ammo.move(1000, 750);
            }

            for(Bullet ammo : bulletList)
            {
                ammo.resetImage(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BLANK));
                ammo.move(1000, 750);
            }

            fighterList.clear();
            fighterRedList.clear();
            bulletList.clear();
            explosionList.clear();
            totalEnemiesList.clear();
            enemyBulletList.clear();
            inGame = false;
            setLives(3);
            GamePanel.setLives(lives);
            noEnemies = 0;
            mGameFrame.repaint();
            currentLevel = 0;
            restartGame = true;

        }



        /**
         * If all enemies are eliminated, repopulate and initialize the next level.
         */
        if(noEnemies == 0 && inGame)
            {
                Sound.playSoundEffect(Sound.SoundEffect.LEVEL_START);
                GamePanel.setLevel(1);      //increases level per draw
                /**
                 * removes any excess ammo on the screen before the next round starts.
                 */

                /**
                 * removes all player ammo on the screen. Prevents user from queueing in shots
                 * before the next round begins
                 */
                for(Bullet ammo : bulletList) {
                    ammo.setLocation(1000, 750);
                    ammo.update(0);
                }

                /**
                 * clears all ArrayList to prevent memory from storing up.
                 */
                bulletList.clear();
                explosionList.clear();
                totalEnemiesList.clear();

                enemyBulletList.clear();

                sShip.setLocation(350,825);     //centers the player. Prevents the player from dying if Ship stays
                                                     //at the bottom right corner.

                /**
                 * Creates formation for fighters.
                 * There are a total of 50 fighters: 30 Blue Fighters, 20 Red Fighters
                 * Blue Fighter will be initiated on the bototm right corner and centur at the middle of the screen.
                 * They will move until reaching a trigger point at which they will dive down the screen
                 * Red Fighters will move from left to right at the middle of the screen and fire bullets.
                 *
                 */

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 10; j++) {
                        sFighter = new Fighter(850 + + (10+2*j) * j, 975 + (50+j+i)  * i);
                        sFighter.changeSpeed(25 + currentLevel*10);
                        sSprites.add(sFighter);
                        fighterList.add(sFighter);
                    }
                }

                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 5; j++) {
                        sFighterRed = new FighterRed((700 + i * 100 + 150 * j), 400 + 50 * i);
                        sFighterRed.changeSpeed(40 + currentLevel*10);
                        sSprites.add(sFighterRed);
                        fighterRedList.add(sFighterRed);
                    }
                }

                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 5; j++) {
                        sFighterRed = new FighterRed(-(50 + i * 100 + 150 * j), 250 + 50 * i);
                        sFighterRed.changeSpeed(40 + currentLevel*10);
                        sSprites.add(sFighterRed);
                        fighterRedList.add(sFighterRed);
                    }
                }
                    noEnemies = fighterList.size() + fighterRedList.size();     //calculates of total enemies left
                    GamePanel.setEnemiesLeft(noEnemies);                        //displays total enemies left on panel
            }
    }

    /**
     * @return current # of lives.
     */
    public static int getLives()
    {
        return lives;
    }

    public int setLives(int x)
    {
        return lives = x;
    }
}




