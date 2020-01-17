import java.util.Random;

public class GameSettings {
    private static int ENEMY_VIEW_DISTANCE = 5;
    private static int PLAYER_WIDTH = 8;
    private static int PLAYER_HEIGHT = 8;
    private static int NUM_PLAYERS = 2;
    public static final Random random = new Random();

    public static int getNumPlayers() {
        return NUM_PLAYERS;
    }

    public static void setNumPlayers(int numPlayers) {
        NUM_PLAYERS = numPlayers;
    }

    public static int getEnemyViewDistance() {
        return ENEMY_VIEW_DISTANCE;
    }

    public static void setEnemyViewDistance(int enemyViewDistance) {
        ENEMY_VIEW_DISTANCE = enemyViewDistance;
    }

    public static int getPlayerWidth() {
        return PLAYER_WIDTH;
    }

    public static void setPlayerWidth(int playerWidth) {
        PLAYER_WIDTH = playerWidth;
    }

    public static int getPlayerHeight() {
        return PLAYER_HEIGHT;
    }

    public static void setPlayerHeight(int playerHeight) {
        PLAYER_HEIGHT = playerHeight;
    }
}
