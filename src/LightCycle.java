import javafx.scene.effect.Light;

import java.awt.*;
import java.awt.image.*;
import java.util.Iterator;
import java.util.ArrayList;

public class LightCycle extends Collideable{
    public final int WIDTH = 8;
    public final int HEIGHT = 8;

    private int headX;
    private int headY;
    private int tailX;
    private int tailY;
    private Rectangle head;
    private Direction dir;
    private Color color;
    private BufferedImage icon;

    public LightCycle(int startX, int startY, Direction dir, Color color, BufferedImage icon) {
        this.tailX = startX;
        this.headX = startX;
        this.tailY = startY;
        this.headY = startY;
        this.dir = dir;
        this.color = color;
        this.icon = icon;
        this.head = new Rectangle(headX, headY, WIDTH, HEIGHT);
    }

    public Color getColor() {
        return color;
    }

    public Rectangle getHead(){
        return this.head;
    }

    public BufferedImage getIcon() {
        return icon;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir){
//        System.out.println("Current " + this.dir);
//        System.out.println("Opposite " + this.dir.getOpposite(this.dir));
//        System.out.println("Proposed "+ dir);
        this.dir = this.dir.getOpposite(this.dir) != dir ? dir : this.dir;
    }

    public void addPart(){
        this.head = new Rectangle(headX, headY, this.WIDTH,this.HEIGHT);
        bodyParts.add(this.head);
        headX+=this.WIDTH*dir.getIncX();
        headY+=this.HEIGHT*dir.getIncY();
    }

//    public boolean checkCollide(LightCycle opp){
//        Rectangle head = this.head;
//
//        Iterator it = opp.bodyParts.iterator();
//        while(it.hasNext()){
//            Rectangle otherPart = (Rectangle) it.next();
//            if(otherPart != head && head.intersects(otherPart)){ // make sure that we are not checking collision of the head against itself
//                return true;
//            }
//        }
//        return false;
//    }
}
