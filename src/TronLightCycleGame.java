import java.awt.event.*;
import javax.swing.*;

public class TronLightCycleGame extends JFrame implements ActionListener{
    Timer myTimer;
    GamePanel game;

    public TronLightCycleGame(String passargs) {
        super("Tron Lightcycles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(800,650);

        myTimer = new Timer(10, this);	 // trigger every 10 ms

        game = new GamePanel(this, passargs);
        game.setLayout(null);
        add(game);
        pack();
        setResizable(false);
        setVisible(true);
    }

    public void start(){
        myTimer.start();
    }

    public void actionPerformed(ActionEvent evt){
        game.move();
        game.repaint();
    }
}