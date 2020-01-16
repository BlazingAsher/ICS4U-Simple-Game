import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Collidable {
    ArrayList<Rectangle> bodyParts = new ArrayList<Rectangle>();

    public static boolean checkCollide(Collidable opp, Rectangle checkRect){
        Rectangle head = checkRect;

        Iterator it = opp.bodyParts.iterator();
        while(it.hasNext()){
            //System.out.println("test");
            Rectangle otherPart = (Rectangle) it.next();
            if(otherPart != head && head.intersects(otherPart)){ // make sure that we are not checking collision of the head against itself
                return true;
            }
        }
        return false;
    }

    public void addPart(Rectangle part){
        this.bodyParts.add(part);
    }
}
