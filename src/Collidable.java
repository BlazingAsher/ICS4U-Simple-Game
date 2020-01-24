/*
 * File: Collidable.java
 * Author: David Hui
 * Description: Container for objects made of multiple rects. Allows for checking whether a Rectangle collides with a Collidable.
 */
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Collidable {
    ArrayList<Rectangle> bodyParts = new ArrayList<Rectangle>(); // Stores the Rectangles of the Collidable

    /**
     * Checks whether a Rectangle will collide with a Collidable
     * @param opp the Collidable
     * @param checkRect the Rectangle
     * @return whether the Rectangle will collide with the Collidable
     */
    public static boolean checkCollide(Collidable opp, Rectangle checkRect){
        Iterator it = opp.bodyParts.iterator(); // Iterator for all the parts of the Collidable

        // Iterate through all the parts and see if one of the Rectangles collides with the checkRect
        while(it.hasNext()){
            Rectangle otherPart = (Rectangle) it.next();
            if(otherPart != checkRect && checkRect.intersects(otherPart)){ // make sure that we are not checking collision of the head against itself (when we are checking if the head of a player is colliding with itself
                return true;
            }
        }
        return false;
    }

    /**
     * Add a part to the Collidable
     * @param part the Rectangle to add to the Collidable
     */
    public void addPart(Rectangle part){
        this.bodyParts.add(part);
    }

    @Override
    public String toString(){
        return String.format("Collidable with parts: %s", bodyParts);
    }
}
