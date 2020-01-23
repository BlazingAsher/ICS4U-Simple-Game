import java.awt.event.*;
import javax.swing.*;

public class TronLightCycleGame extends JFrame implements ActionListener{
    Timer myTimer;
    GamePanel game;
    public boolean[] finished;

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

    public void start(){
        myTimer.start();
    }

    public void actionPerformed(ActionEvent evt){
        if(!finished[0]) {
            game.move();
            game.repaint();
        }

    }
}