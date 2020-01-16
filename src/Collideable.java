import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Collideable {
    ArrayList<Rectangle> bodyParts = new ArrayList<Rectangle>();

    public boolean checkCollide(Collideable opp, Rectangle checkRect){
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
}
