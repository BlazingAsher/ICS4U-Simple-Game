import java.awt.*;
import java.util.Random;

public class GameSettings {

    private static int ENEMY_VIEW_DISTANCE = 5;
    private static int PLAYER_WIDTH = 8;
    private static int PLAYER_HEIGHT = 8;
    private static int NUM_PLAYERS = 2;
    private static String[] playerColours = new String[] {"Blue", "Red", "Yellow", "Magenta"};
    private static Color[] playerColoursObj = new Color[] {Color.blue, Color.red, Color.yellow, Color.magenta};

    private static String[] speedLabels = new String[] {"Slow", "Normal", "Fast"};
    private static double[] speedMultipliers = new double[] {0.5,1.0,1.5};

    public static double[] getSpeedMultipliers() {
        return speedMultipliers;
    }

    //private static String[] iconLocations = new String[] {"cycles/cycle_blue.png", "cycles/cycle_red.png", "cycles/cycle_yellow.png", "cycles/cycle_orange.png"};
    public static final Random random = new Random();

    public static int getNumPlayers() {
        return NUM_PLAYERS;
    }

    public static void setNumPlayers(int numPlayers) {
        NUM_PLAYERS = numPlayers;
    }

    public static Color[] getPlayerColoursObj() {
        return playerColoursObj;
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

    public static String[] getPlayerColours() {
        return playerColours;
    }

    public static void setPlayerColours(String[] playerColours) {
        playerColours = playerColours;
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

    public static String[] getSpeedLabels() {
        return speedLabels;
    }
}
