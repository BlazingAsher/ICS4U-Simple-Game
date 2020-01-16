import java.awt.event.*;
import javax.swing.*;

public class TronLightCycleGame extends JFrame implements ActionListener{
    Timer myTimer;
    GamePanel game;

    public TronLightCycleGame() {
        super("Move the Box");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(800,650);

        myTimer = new Timer(10, this);	 // trigger every 10 ms

        game = new GamePanel(this);
        add(game);
        pack();
        //setResizable(false);
        setVisible(true);
    }

    public void start(){
        myTimer.start();
    }

    public void actionPerformed(ActionEvent evt){
        game.move();
        game.repaint();
    }

    public static void main(String[] arguments) {
        TronLightCycleGame frame = new TronLightCycleGame();
    }
}