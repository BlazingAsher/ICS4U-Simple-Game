import java.awt.*;
import java.awt.image.*;

public class LightCycle extends Collidable{
    //public final int WIDTH = 8;
    //public final int HEIGHT = 8;

    private int headX;
    private int headY;
    private int tailX;
    private int tailY;
    private Rectangle head;
    private Direction dir;
    private Color color;
    private int boostVal;
    private int boostCooldown;
    //private BufferedImage icon;

    public LightCycle(int startX, int startY, Direction dir, Color color/*, BufferedImage icon*/) {
        this.tailX = startX;
        this.headX = startX;
        this.tailY = startY;
        this.headY = startY;
        this.dir = dir;
        this.color = color;
        //this.icon = icon;
        this.head = new Rectangle(headX, headY, GameSettings.getPlayerWidth(), GameSettings.getPlayerHeight());
        this.boostVal = 0;
        this.boostCooldown = 500;
    }

    public Color getColor() {
        return color;
    }

    public Rectangle getHead(){
        return this.head;
    }
/*
    public BufferedImage getIcon() {
        return icon;
    }*/

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
        this.head = new Rectangle(headX, headY, GameSettings.getPlayerWidth(),GameSettings.getPlayerHeight());
        bodyParts.add(this.head);
        headX+=GameSettings.getPlayerWidth()*dir.getIncX();
        headY+=GameSettings.getPlayerHeight()*dir.getIncY();
    }

    public int getBoostVal() {
        return boostVal;
    }

    public void setBoostVal(int boostVal) {
        this.boostVal = boostVal;
    }

    public void decBoostVal() {
        this.boostVal-=1;
    }

    public int getBoostCooldown() {
        return boostCooldown;
    }

    public void setBoostCooldown(int boostCooldown) {
        this.boostCooldown = boostCooldown;
    }

    public void rechargeBoost() {
        this.boostCooldown++;
    }
    /*

    public int getIconXOffset(){
        int icoW, icoH;
        icoW = this.getIcon().getWidth();
        icoH = this.getIcon().getHeight();

        int extrapx;
        switch(this.getDir()){
            case NORTH:
                extrapx = (icoH - GameSettings.getPlayerWidth())/-2;
                break;
            case EAST:
                extrapx = -(icoW - GameSettings.getPlayerWidth() -1);
                break;
            case SOUTH:
                extrapx = (icoH - GameSettings.getPlayerWidth())/2+ GameSettings.getPlayerWidth();
                break;
            default:
                extrapx =  icoW - GameSettings.getPlayerWidth() -1;
        }
        return extrapx;
    }

    public int getIconYOffset(){
        int icoW, icoH;
        icoW = this.getIcon().getWidth();
        icoH = this.getIcon().getHeight();

        int extrapx;
        switch(this.getDir()){
            case NORTH:
                extrapx = icoW - GameSettings.getPlayerHeight() -1;
                break;
            case EAST:
                extrapx = (icoH - GameSettings.getPlayerHeight())/-2;
                break;
            case SOUTH:
                extrapx = -(icoW - GameSettings.getPlayerHeight() -1);
                break;
            default:
                extrapx = (icoH - GameSettings.getPlayerHeight())/2 + GameSettings.getPlayerHeight();
        }
        return extrapx;
    }
*/
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
