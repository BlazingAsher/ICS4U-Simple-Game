/*
 * File: TronLightCycleGame.java
 * Author: David Hui
 * Description: JFrame that houses the GamePanel
 */
import java.awt.event.*;
import javax.swing.*;

public class TronLightCycleGame extends JFrame implements ActionListener{
    Timer myTimer; // game clock
    GamePanel game; // GamePanel instance
    public boolean[] finished; // Array used by GamePanel to notify that game is finished

    public TronLightCycleGame(boolean[] finished, String passargs) {
        super("Tron Lightcycles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.finished = finished;

        myTimer = new Timer(10, this);	 // trigger every 10 ms

        game = new GamePanel(this, passargs);
        game.setLayout(null);
        add(game);
        pack();
        setResizable(false);
        setVisible(true);
    }

    /**
     * Starts the game clock
     */
    public void start(){
        myTimer.start();
    }

    public void actionPerformed(ActionEvent evt){
        if(!finished[0]) {
            // As long as game is not finished, move players and repaint the screen
            game.move();
            game.repaint();
        }

    }
}