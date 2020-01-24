/*
 * File: Direction.java
 * Author: David Hui
 * Description: Directions in the game
 */
public enum Direction {
    NORTH(0,-1),
    EAST(1,0),
    SOUTH(0,1),
    WEST(-1,0);

    /**
     * Returns the X increment of the Direction
     * @return the X increment of the Direction
     */
    public int getIncX() {
        return incX;
    }

    /**
     * Returns the Y increment of the Direction
     * @return the Y increment of the Direction
     */
    public int getIncY() {
        return incY;
    }

    /**
     * Returns the opposite Direction of the given Direction
     * @param dir the Direction of which you are looking for the opposite
     * @return the opposite Direction of dir
     */
    public Direction getOpposite(Direction dir){
        if(dir == Direction.NORTH){
            return Direction.SOUTH;
        }
        else if(dir == Direction.SOUTH){
            return Direction.NORTH;
        }
        else if(dir == EAST){
            return Direction.WEST;
        }
        else{
            return Direction.EAST;
        }
    }

    private final int incX; // Amount to increase X when travelling in the Direction
    private final int incY; // Amount to increase T when travelling in the Direction

    Direction(int incX, int incY){
        this.incX = incX;
        this.incY = incY;
    }

}
