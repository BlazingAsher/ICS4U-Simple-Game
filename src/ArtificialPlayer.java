/*
 * File: ArtificialPlayer.java
 * Author: David Hui
 * Description: Provides an avoidance AI that controls a LightCycle
 */
import java.awt.*;
import java.util.ArrayList;

public class ArtificialPlayer {
    private final LightCycle cycle; // Stores the AI's LightCycle
    private final LightCycle opp; // Stores the opponent's LightCycle
    private final Collidable restrictedArea; // Stores the restricted area that cannot be touched

    public ArtificialPlayer(LightCycle cycle, LightCycle opp, Collidable restrictedArea) {
        this.cycle = cycle;
        this.opp = opp;
        this.restrictedArea = restrictedArea;
    }

    /**
     * Checks whether the AI will collide with something it shouldn't
     * @return whether the AI will collide with something it shouldn't
     */
    private boolean willCollide(){
        Rectangle currentHead = this.cycle.getHead();
        // Shoot a ray up to the enemy's view distance and see if the AI will collide with itself, the opponent, or a restricted area
        for(int i = 1;i <= GameSettings.getEnemyViewDistance(); i++){
            Rectangle finalPosition = new Rectangle(currentHead.x + (this.cycle.getDir().getIncX() * GameSettings.getPlayerWidth() * i), currentHead.y + (this.cycle.getDir().getIncY() * GameSettings.getPlayerHeight() * i), 1, 1);
            if(Collidable.checkCollide(this.cycle, finalPosition) || Collidable.checkCollide(this.opp, finalPosition) || Collidable.checkCollide(this.restrictedArea, finalPosition)){
                return true;
            }
        }
        // Doesn't collide with anything
        return false;
    }

    /**
     * Controls a LightCycle
     */
    public void performAction() {
        // ArrayList of directions
        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(Direction.EAST);
        directions.add(Direction.NORTH);
        directions.add(Direction.SOUTH);
        directions.add(Direction.WEST);

        // If the AI will collide, randomly choose a direction so that it won't collide
        while(willCollide() && directions.size() > 0){
            int ind = (int) (Math.random()*directions.size());
            this.cycle.setDir(directions.get(ind));
            directions.remove(ind);
        }

    }

    @Override
    public String toString() {
        return String.format("AI player with LightCycle: %s, opponent: %s", cycle, opp);
    }

}
