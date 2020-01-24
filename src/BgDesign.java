/*
 * File: BgDesign.java
 * Author: David Hui
 * Description: Provides the menu background graphic
 */
import javax.swing.*;
import java.awt.*;

class BgDesign extends JComponent{
    // Get the screen size from the settings (reduce line lengths)
    private final static int DISPLAY_HEIGHT = GameSettings.getScreenHeight();
    private final static int DISPLAY_WIDTH = GameSettings.getScreenWidth();

    private Color[] chosenColours; // The colours that have been chosen

    // Stores the rects that make up a player graphic
    private Rectangle[] player1Rects;
    private Rectangle[] player2Rects;

    public BgDesign(Color[] chosenColours){
        this.chosenColours = chosenColours;

        this.player1Rects = new Rectangle[]{
                new Rectangle(500, DISPLAY_HEIGHT-200, 85, 250),
                new Rectangle(500-250, DISPLAY_HEIGHT-200, 250, 85),
                new Rectangle(500-250, DISPLAY_HEIGHT-200-225, 85, 225)
        }; // Rectangles that make up the player 1 graphic

        this.player2Rects = new Rectangle[]{
                new Rectangle(500-75, DISPLAY_HEIGHT-385, 500, 85)
        }; // Rectangles that make up the player 2 graphic

    }

    /**
     * Override the paintComponent method to draw the graphic
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        // Rotate the Graphics2D 45 degrees to make it look cooler
        g2d.rotate(Math.toRadians(45), DISPLAY_WIDTH/2.0, DISPLAY_HEIGHT/2.0);

        // Set to the colour of the first player
        g2d.setColor(this.chosenColours[0]);

        // Draw the first player
        for(Rectangle rect : this.player1Rects){
            g2d.draw(rect);
            g2d.fill(rect);
        }

        // Set to the colour of the second player
        g2d.setColor(this.chosenColours[1]);

        // Draw the second player
        for(Rectangle rect : this.player2Rects){
            g2d.draw(rect);
            g2d.fill(rect);
        }

    }
}