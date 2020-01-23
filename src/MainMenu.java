import javax.swing.*;
import java.awt.event.*;

public class MainMenu extends JFrame{
    private JLayeredPane layeredPane=new JLayeredPane();
    private TronLightCycleGame gameFrame;
    private JComboBox player1Colour;
    private JComboBox player2Colour;
    boolean[] finished = new boolean[] { false };
    Timer myTimer;

    public MainMenu() {
        super("Tron Lightcycles");
        setSize(800,600);

        ImageIcon backPic = new ImageIcon("menu.jpg");
        JLabel back = new JLabel(backPic);
        back.setBounds(0, 0,backPic.getIconWidth(),backPic.getIconHeight());
        layeredPane.add(back,1);


        player1Colour = new JComboBox(GameSettings.getPlayerColours());
        player1Colour.setBounds(450, 400,100,50);
        player1Colour.setSelectedIndex(0);
        layeredPane.add(player1Colour, 2);

        player2Colour = new JComboBox(GameSettings.getPlayerColours());
        player2Colour.setBounds(450, 500,100,50);
        player2Colour.setSelectedIndex(1);
        layeredPane.add(player2Colour, 2);


        JButton startSingleBtn = new JButton("Single Player");
        startSingleBtn.addActionListener(new ClickStart("single,"));
        startSingleBtn.setBounds(300,400,100,50);
        layeredPane.add(startSingleBtn,2);

        JButton startMultiBtn = new JButton("Multi Player");
        startMultiBtn.addActionListener(new ClickStart("multi,"));
        startMultiBtn.setBounds(300,500,100,50);
        layeredPane.add(startMultiBtn,2);

        myTimer = new Timer(10, new StackInterceptor());

        setContentPane(layeredPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] arguments) {
        MainMenu frame = new MainMenu();
    }

    class StackInterceptor implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            if(finished[0]){
                setVisible(true);
                gameFrame.setVisible(false);
                gameFrame.dispose();
                gameFrame = null;
                myTimer.stop();
            }
        }
    }

    class ClickStart implements ActionListener{
        String passargs = "";
        ClickStart(String passargs) {
            this.passargs = passargs;
        }
        @Override
        public void actionPerformed(ActionEvent evt){
            finished[0] = false;
            gameFrame = new TronLightCycleGame(finished, passargs+player1Colour.getSelectedIndex()+","+player2Colour.getSelectedIndex());
            myTimer.start();
            setVisible(false);
        }
    }
}
