import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame{
    private JLayeredPane layeredPane=new JLayeredPane();
    private TronLightCycleGame gameFrame;
    private JComboBox player1Colour;
    private JComboBox player2Colour;
    private JComboBox gameSpeed;
    boolean[] finished = new boolean[] { false };
    Timer stackTimer;

    public final static int DISPLAY_HEIGHT = 600;
    public final static int DISPLAY_WIDTH = 700;

    private Color[] chosenColours;

    public MainMenu(){
        super("Tron Lightcycles");
        setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        setBackground(Color.black);

        chosenColours = new Color[] {Color.blue, Color.red};

        ImageIcon backPic = new ImageIcon("menu.jpg");
        JLabel back = new JLabel(backPic);
        back.setBounds(200, 0,backPic.getIconWidth(),backPic.getIconHeight());
        layeredPane.add(back,1);
/*
        menuPanel = new MenuPanel(this);
        menuPanel.setSize(700, 600);
        menuPanel.setLayout(null);
        layeredPane.add(menuPanel, 1);*/

        BgDesign bgDesign = new BgDesign(chosenColours);
        bgDesign.setBounds(-125,0,900, 600);
        layeredPane.add(bgDesign, 1);

        JButton startSingleBtn = new JButton("Single Player");
        startSingleBtn.addActionListener(new ClickStart("single,"));
        startSingleBtn.setBounds(450,200,200,25);
        layeredPane.add(startSingleBtn,2);

        JButton startMultiBtn = new JButton("Multi Player");
        startMultiBtn.addActionListener(new ClickStart("multi,"));
        startMultiBtn.setBounds(450,235,200,25);
        layeredPane.add(startMultiBtn,2);

        gameSpeed = new JComboBox(GameSettings.getSpeedLabels());
        gameSpeed.setBounds(500,280,150,25);
        gameSpeed.setSelectedIndex(1);
        layeredPane.add(gameSpeed, 2);

        player1Colour = new JComboBox(GameSettings.getPlayerColours());
        player1Colour.addActionListener(new ColorUpdateListener(0));
        player1Colour.setBounds(550, 330,100,25);
        player1Colour.setSelectedIndex(0);
        layeredPane.add(player1Colour, 3);

        player2Colour = new JComboBox(GameSettings.getPlayerColours());
        player2Colour.addActionListener(new ColorUpdateListener(1));
        player2Colour.setBounds(550, 365,100,25);
        player2Colour.setSelectedIndex(1);
        layeredPane.add(player2Colour, 3);

        stackTimer = new Timer(10, new StackInterceptor());

        setContentPane(layeredPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] arguments) {
        MainMenu frame = new MainMenu();
    }

    class ColorUpdateListener implements ActionListener {
        private int tronInd;
        public ColorUpdateListener(int tronInd){
            this.tronInd = tronInd;
        }
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox)e.getSource();
            int ind = cb.getSelectedIndex();
            chosenColours[tronInd] = GameSettings.getPlayerColoursObj()[ind];
        }
    }

    class StackInterceptor implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            if(finished[0]){
                setVisible(true);
                gameFrame.setVisible(false);
                gameFrame.dispose();
                gameFrame = null;
                stackTimer.stop();
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
            gameFrame = new TronLightCycleGame(finished, passargs+player1Colour.getSelectedIndex()+","+player2Colour.getSelectedIndex()+","+gameSpeed.getSelectedIndex());
            stackTimer.start();
            setVisible(false);
        }
    }
}
