import javax.swing.*;
import java.awt.event.*;

public class MainMenu extends JFrame{
    private JLayeredPane layeredPane=new JLayeredPane();

    public MainMenu() {
        super("Use LayeredPane to put things on top of one another");
        setSize(800,600);

        ImageIcon backPic = new ImageIcon("menu.jpg");
        JLabel back = new JLabel(backPic);
        back.setBounds(0, 0,backPic.getIconWidth(),backPic.getIconHeight());
        layeredPane.add(back,1);

        JButton startSingleBtn = new JButton("Single Player");
        startSingleBtn.addActionListener(new ClickStart("single"));
        startSingleBtn.setBounds(300,400,100,50);
        layeredPane.add(startSingleBtn,2);

        JButton startMultiBtn = new JButton("Multi Player");
        startMultiBtn.addActionListener(new ClickStart("multi"));
        startMultiBtn.setBounds(300,500,100,50);
        layeredPane.add(startMultiBtn,2);

        setContentPane(layeredPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] arguments) {
        MainMenu frame = new MainMenu();
    }

    class ClickStart implements ActionListener{
        String passargs = "";
        ClickStart(String passargs) {
            this.passargs = passargs;
        }
        @Override
        public void actionPerformed(ActionEvent evt){
            TronLightCycleGame gameFrame = new TronLightCycleGame(passargs);
            setVisible(false);
        }
    }
}
