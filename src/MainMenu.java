import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame{
    private JLayeredPane layeredPane=new JLayeredPane();
    private TronLightCycleGame gameFrame;
    private JComboBox player1Colour;
    private JComboBox player2Colour;
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

        player1Colour = new JComboBox(GameSettings.getPlayerColours());
        player1Colour.addActionListener(new ColorUpdateListener(0));
        player1Colour.setBounds(550, 300,100,25);
        player1Colour.setSelectedIndex(0);
        layeredPane.add(player1Colour, 3);

        player2Colour = new JComboBox(GameSettings.getPlayerColours());
        player2Colour.addActionListener(new ColorUpdateListener(1));
        player2Colour.setBounds(550, 335,100,25);
        player2Colour.setSelectedIndex(1);
        layeredPane.add(player2Colour, 3);


        JButton startSingleBtn = new JButton("Single Player");
        startSingleBtn.addActionListener(new ClickStart("single,"));
        startSingleBtn.setBounds(450,200,200,25);
        layeredPane.add(startSingleBtn,2);

        JButton startMultiBtn = new JButton("Multi Player");
        startMultiBtn.addActionListener(new ClickStart("multi,"));
        startMultiBtn.setBounds(450,235,200,25);
        layeredPane.add(startMultiBtn,2);

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
            gameFrame = new TronLightCycleGame(finished, passargs+player1Colour.getSelectedIndex()+","+player2Colour.getSelectedIndex());
            stackTimer.start();
            setVisible(false);
        }
    }
}

class BgDesign extends JComponent implements ActionListener{
    public final static int DISPLAY_HEIGHT = 600;
    public final static int DISPLAY_WIDTH = 700;

    Timer myTimer;

    private Color[] chosenColours;
    public BgDesign(Color[] chosenColours){
        this.chosenColours = chosenColours;

        myTimer = new Timer(10, this);
        myTimer.start();
    }
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(this.chosenColours[0]);
        Rectangle rect2 = new Rectangle(500, DISPLAY_HEIGHT-200, 85, 250);

        Rectangle rect3 = new Rectangle(500-250, DISPLAY_HEIGHT-200, 250, 85);

        Rectangle rect4 = new Rectangle(500-250, DISPLAY_HEIGHT-200-225, 85, 225);

        g2d.rotate(Math.toRadians(45), DISPLAY_WIDTH/2.0, DISPLAY_HEIGHT/2.0);
        g2d.draw(rect2);
        g2d.fill(rect2);
        g2d.draw(rect3);
        g2d.fill(rect3);
        g2d.draw(rect4);
        g2d.fill(rect4);


        g2d.setColor(this.chosenColours[1]);

        Rectangle rect5 =new Rectangle(500-75, DISPLAY_HEIGHT-400, 500, 85);
        g2d.draw(rect5);
        g2d.fill(rect5);


    }
}
