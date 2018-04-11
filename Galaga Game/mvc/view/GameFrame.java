package _08final.mvc.view;

import mvc.controller.Game;
import mvc.controller.MouseInput;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GameFrame extends JFrame {

    /** The window dimensions for the Frame */
    public static final Dimension FRAME_DIM = new Dimension(700, 900);

    /* The Game panel for the game **/
    private GamePanel mPanel;

    /* The controller for the game **/
    private Game mController;



    public GameFrame(Game controller) {

        //Enable the frame to be notified if the user clicks on the close button
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        //Set the game controller for the Frame
        this.mController = controller;

        try {
            // Try to initialize the game panel
            initPanel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Initialize the frame
        this.setTitle("Galaga");
        this.setSize(FRAME_DIM);
        this.setFocusable(true);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Draws the current game information to the window
     */
    public void draw() {
        this.mPanel.repaint();
    }
    private void initPanel() throws Exception {

        //Set the layout for the panel
        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        //Create a new GamePanel for the controller
        this.mPanel = new GamePanel();

        //Let the controller be the listener for the all actions that happen on the game panel
        this.addKeyListener(this.mController);

        MouseInput input = new MouseInput();
        this.addMouseListener(input);

        //Add the game panel to the window's content panel.
        contentPane.add(mPanel);
    }
    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        //Exit the game if the window closed button is closed.
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }

}