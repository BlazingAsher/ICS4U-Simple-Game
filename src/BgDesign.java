import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BgDesign extends JComponent implements ActionListener {
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