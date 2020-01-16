import java.awt.*;
import java.util.ArrayList;

public class ArtificialPlayer {
    private final LightCycle cycle;
    private final LightCycle opp;
    private final Collidable playArea;

    public ArtificialPlayer(LightCycle cycle, LightCycle opp, Collidable playArea) {
        this.cycle = cycle;
        this.opp = opp;
        this.playArea = playArea;
    }

    private boolean willCollide(){
        Rectangle currentHead = this.cycle.getHead();
        for(int i = 1;i <= GameSettings.ENEMRY_VIEW_DISTANCE; i++){
            Rectangle finalPosition = new Rectangle(currentHead.x + (this.cycle.getDir().getIncX() * GameSettings.PLAYER_WIDTH * i), currentHead.y + (this.cycle.getDir().getIncY() * GameSettings.PLAYER_HEIGHT * i), 1, 1);
            if(Collidable.checkCollide(this.cycle, finalPosition) || Collidable.checkCollide(this.opp, finalPosition) || !Collidable.checkCollide(this.playArea, finalPosition)){
                return true;
            }
        }

        return false;
    }

    public void performAction() {
        int m = 0;
        ArrayList<Direction> directions = new ArrayList<Direction>();
        directions.add(Direction.EAST);
        directions.add(Direction.NORTH);
        directions.add(Direction.SOUTH);
        directions.add(Direction.WEST);

        while(willCollide() && directions.size() > 0){
            int ind = (int) (Math.random()*directions.size());
            this.cycle.setDir(directions.get(ind));
            directions.remove(ind);
        }

        // move closer to the player
    }

}
