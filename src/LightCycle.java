/*
 * File: LightCycle.java
 * Author: David Hui
 * Description: Represents a LightCycle. Allows parts of the game to retrieve information about it and modify its properties.
 */
import java.awt.*;

public class LightCycle extends Collidable{
    private int headX; // x co-ordinate of the head
    private int headY; // y co-ordinate of the head
    private Rectangle head; // the head Rectangle of the LightCycle
    private Direction dir; // the direction that the LightCycle is going
    private Color color; // the colour of the LightCycle
    private int boostVal; // the boost level of the LightCycle
    private int boostCooldown; // the boost cooldown of the LightCycle

    public LightCycle(int startX, int startY, Direction dir, Color color) {
        this.headX = startX;
        this.headY = startY;
        this.dir = dir;
        this.color = color;
        this.head = new Rectangle(headX, headY, GameSettings.getPlayerWidth(), GameSettings.getPlayerHeight());
        this.boostVal = 0;
        this.boostCooldown = GameSettings.getBoostCooldownTicks();
    }

    /**
     * Returns the colour of the LightCycle
     * @return the colour of the LightCycle
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the head Rectangle of the LightCycle
     * @return the head Rectangle of the LightCycle
     */
    public Rectangle getHead(){
        return this.head;
    }

    /**
     * Returns the Direction that the LightCycle is going
     * @return the Direction that the LightCycle is going
     */
    public Direction getDir() {
        return dir;
    }

    /**
     * Sets the Direction of the LightCycle
     * @param dir the new Direction of the LightCycle
     */
    public void setDir(Direction dir){
        // Do not allow the LightCycle to turn on itself
        this.dir = this.dir.getOpposite(this.dir) != dir ? dir : this.dir;
    }

    /**
     * Add another Rectangle to the LightCycle
     */
    public void addPart(){
        this.head = new Rectangle(headX, headY, GameSettings.getPlayerWidth(),GameSettings.getPlayerHeight());
        bodyParts.add(this.head);
        // Update the head location
        headX+=GameSettings.getPlayerWidth()*dir.getIncX();
        headY+=GameSettings.getPlayerHeight()*dir.getIncY();
    }

    /**
     * Returns the boost level of the LightCycle
     * @return the boost level of the LightCycle
     */
    public int getBoostVal() {
        return boostVal;
    }

    /**
     * Sets the boost level of the LightCycle
     * @param boostVal the new boost level of the LightCycle
     */
    public void setBoostVal(int boostVal) {
        this.boostVal = boostVal;
    }

    /**
     * Decrement the boost level of the LightCycle
     */
    public void decBoostVal() {
        this.boostVal-=1;
    }

    /**
     * Returns the boost cooldown of the LightCycle
     * @return the boost cooldown of the LightCycle
     */
    public int getBoostCooldown() {
        return boostCooldown;
    }

    /**
     * Sets the boost cooldown of the LightCycle
     * @param boostCooldown the new boost cooldown of the LightCycle
     */
    public void setBoostCooldown(int boostCooldown) {
        LevelLogger.log("someone modified me!");
        this.boostCooldown = boostCooldown;
    }

    /**
     * Increment the boost cooldown of the LightCycle
     */
    public void rechargeBoost() {
        this.boostCooldown++;
    }

    public String toString(){
        return String.format("LightCycle with head at (%d, %d) heading %s with color %s. Boosting %d with cooldown %d", headX, headY, dir, color, boostVal, boostCooldown);
    }
}
