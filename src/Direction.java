public enum Direction {
    NORTH(0,-1, 270),
    EAST(1,0, 0),
    SOUTH(0,1, 90),
    WEST(-1,0, 180);

    public int getIncX() {
        return incX;
    }

    public int getIncY() {
        return incY;
    }

    public int getDeg() {
        return deg;
    }

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

    private final int incX;
    private final int incY;
    private final int deg;

    Direction(int incX, int incY, int deg){
        this.incX = incX;
        this.incY = incY;
        this.deg = deg;
    }

}
