/*
 * File: GameSettings.java
 * Author: David Hui
 * Description: Stores all the game settings
 */
import java.awt.*;

public class GameSettings {

    private static int enemyViewDistance = 5; // Number of game ticks the AI can look ahead
    // Dimensions of the player
    private static int playerWidth = 8;
    private static int playerHeight = 8;

    private static int numPlayers = 2; // Number of players

    // Screen dimensions
    private static int screenHeight = 600;
    private static int screenWidth = 700;

    // Available colours for the players
    private static String[] playerColours = new String[] {"Blue", "Red", "Gray", "Magenta"};
    private static Color[] playerColoursObj = new Color[] {Color.blue, Color.red, Color.gray, Color.magenta};

    // Available game speed settings
    private static String[] speedLabels = new String[] {"Slow", "Normal", "Fast"};
    private static double[] speedMultipliers = new double[] {0.5,1.0,1.5};

    private static int boostTicks = 50; // Number of game ticks a boost lasts for
    private static int boostCooldownTicks = 500; // Number of game ticks to recharge boost

    private static int gameWinPoints = 3; // Number of points needed to win a game

    /**
     * Returns the enemy AI view distance
     * @return the enemy AI view distance
     */
    public static int getEnemyViewDistance() {
        return enemyViewDistance;
    }

    /**
     * Returns the width of the player
     * @return the width of the player
     */
    public static int getPlayerWidth() {
        return playerWidth;
    }

    /**
     * Returns the height of the player
     * @return the height of the player
     */
    public static int getPlayerHeight() {
        return playerHeight;
    }

    /**
     * Returns the number of players in the game
     * @return the number of players in the game
     */
    public static int getNumPlayers() {
        return numPlayers;
    }

    /**
     * Returns the height of the screen
     * @return the height of the screen
     */
    public static int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Returns the width of the screen
     * @return the width of the screen
     */
    public static int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Returns the Color objects that are available to the player
     * @see this.getPlayerColours()
     * @return the Color objects that are available to the player
     */
    public static Color[] getPlayerColoursObj() {
        return playerColoursObj;
    }

    /**
     * Returns the names of the colours that are available to the player
     * @see this.getPlayerColoursObj()
     * @return the names of the colours that are available to the player
     */
    public static String[] getPlayerColours() {
        return playerColours;
    }

    /**
     * Returns the speed names that are available to the player
     * @see this.getSpeedMultipliers()
     * @return the speed names that are available to the player
     */
    public static String[] getSpeedLabels() {
        return speedLabels;
    }

    /**
     * Returns the available game speed multipliers
     * @see this.getSpeedLabels()
     * @return the available game speed multipliers
     */
    public static double[] getSpeedMultipliers() {
        return speedMultipliers;
    }

    /**
     * Returns the number of game ticks a boost lasts for
     * @return the number of game ticks a boost lasts for
     */
    public static int getBoostTicks() {
        return boostTicks;
    }

    /**
     * Returns the number of game ticks to recharge boost
     * @return the number of game ticks to recharge boost
     */
    public static int getBoostCooldownTicks() {
        return boostCooldownTicks;
    }

    /**
     * Returns the number of points needed to win a game
     * @return the number of points needed to win a game
     */
    public static int getGameWinPoints() {
        return gameWinPoints;
    }
}
