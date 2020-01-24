/*
 * File: GamePanel.java
 * Author: David Hui
 * Description: Provides a space for two LightCycles to move and checks whether they collide for not. Provides scoring and game-control functionality.
 */
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Iterator;

public class GamePanel extends JPanel implements KeyListener {
    private boolean[] keys; // Store the keys that are pressed down
    private String[] passArgs; // Configuration that is passed by the menu

    // Get the screen size from the settings (reduce line lengths)
    private final static int DISPLAY_HEIGHT = GameSettings.getScreenHeight();
    private final static int DISPLAY_WIDTH = GameSettings.getScreenWidth();
    
    private TronLightCycleGame mainFrame; // Reference to the JFrame that contains the JPanel 
    private LightCycle[] players; // Stores the players on the board
    private int[] wins; // Stores the amount of wins a player has
    private Collidable restrictedArea; // Stores the restricted area
    private ArtificialPlayer ai; // Stores the AI
    private boolean multi; // Whether we are in multiplayer
    private boolean gameDone; // Whether the game is finished and we should exit

    private Timer gameTimer; // Game clock (grow player and check collision)
    private Timer boostTimer; // Boost clock (speeds up a single player's growth)

    private Font fontSys; // Consolas font

    private AudioClip startSound, deathSound; // Audio effects

    public GamePanel(TronLightCycleGame m, String passargs){
        LevelLogger.log(passargs);

        // Explode the passargs into an array
        this.passArgs = passargs.split(",");

        // Check whether this will be a multiplayer session
        this.multi = this.passArgs[0].equals("multi");

        mainFrame = m;

        keys = new boolean[KeyEvent.KEY_LAST+1];
        addKeyListener(this);

        setPreferredSize( new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));
        setBackground(Color.black);

        // Calculate the tickTime by multiplying the reciprocal of the multiplier
        // 50 - Base tick speed is 50ms
        // 3 - Index of passArgs that contains the multiplier
        int tickTime = (int) (50*(1.0/GameSettings.getSpeedMultipliers()[Integer.parseInt(passArgs[3])]));

        // Initialize the timers
        gameTimer = new Timer(tickTime, new TickLoop());
        boostTimer = new Timer(tickTime/2, new BoostLoop());

        fontSys = new Font("Consolas",Font.PLAIN,32); // Load the font (size 32pt)

        // Load the sounds
        File startSoundFile = new File("assets/start.wav");
        File deathSoundFile = new File("assets/fence_explosion.wav");

        try {
            startSound = Applet.newAudioClip(startSoundFile.toURL());
            deathSound = Applet.newAudioClip(deathSoundFile.toURL());
        }
        catch(Exception e){
            LevelLogger.log(e);
        }

        gameDone = false;

        wins = new int[GameSettings.getNumPlayers()];

        // Define the restricted area
        restrictedArea = new Collidable();
        restrictedArea.addPart(new Rectangle(0,0,DISPLAY_WIDTH, 10));
        restrictedArea.addPart(new Rectangle(0,75,DISPLAY_WIDTH, 10));
        restrictedArea.addPart(new Rectangle(0,DISPLAY_HEIGHT-10,DISPLAY_WIDTH, 10));
        restrictedArea.addPart(new Rectangle(0,0,10, DISPLAY_HEIGHT));
        restrictedArea.addPart(new Rectangle(DISPLAY_WIDTH-10,0,10, DISPLAY_HEIGHT));

        // Initialize all the non-reusable variables
        init();
    }

    /**
     * Initializes the GamePanel with everything needed for a game
     */
    private void init() {
        keys = new boolean[KeyEvent.KEY_LAST+1]; // Prevent dangling of key states between resets

        // Stores all possible directions (used in randomizing)
        Direction[] directions = new Direction[]{Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.WEST};

        // Generate the LightCycles
        players = new LightCycle[GameSettings.getNumPlayers()];
        // Randomly generate a starting location and Direction of the LightCycle
        // Player 1 will be towards the left, and will not choose West as the first Direction
        // Player 1 will be towards the right, and will not choose East as the first Direction
        players[0] = new LightCycle((int)(50+Math.random()*250), (int)(200+Math.random()*200), directions[(int) (Math.random()*3)],GameSettings.getPlayerColoursObj()[Integer.parseInt(this.passArgs[1])]);
        players[1] = new LightCycle((int)(350+Math.random()*200), (int)(200+Math.random()*200), directions[1 + (int) (Math.random()*3)],GameSettings.getPlayerColoursObj()[Integer.parseInt(this.passArgs[2])]);
        LevelLogger.log("NEW " + players[0].toString());
        LevelLogger.log("NEW" + players[1].toString());

        startSound.play(); // Play the round start sound

        ai = new ArtificialPlayer(players[1], players[0], restrictedArea); // Initialize the AI
    }

    /**
     * Cleans up the GamePanel after a game is ended
     */
    private void reset() {
        deathSound.play(); // Play the death sound

        // Sync changes between all threads (doesn't really work...)
        validate();
        repaint();

        // Stop all the timers for now
        gameDone = true;

        // Cool death freeze for 2s (2000ms)
        try{
            Thread.sleep(2000);
        } catch(InterruptedException e){

        }

        // Check if one of the players has won
        if(Math.max(wins[0], wins[1]) == GameSettings.getGameWinPoints()){
            // Ask whether player wants to play another game
            int dialogResult = JOptionPane.showConfirmDialog (null, "Player " + (wins[0] > wins[1] ? "1" : "2") + " wins! Play again?","Tron Lightcycles",JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                // Reset the wins, and prepare for another game
                wins = new int[GameSettings.getNumPlayers()];
                gameDone = false;
            }
            else {
                // Wants to end game, so cleanup
                gameDone = true;
                // Sent the signal up to the frame, which will pass it to the menu
                mainFrame.finished[0] = true;
                // Stop the timers
                gameTimer.stop();
                boostTimer.stop();
            }
        }
        else {
            gameDone = false;
        }

        if(!gameDone){
            // Continuing, clear the area and re-initialize
            removeAll();
            init();
        }
    }

    /**
     * Starts the Timers
     */
    public void start(){
        gameTimer.start();
        boostTimer.start();
    }

    public void addNotify() {
        super.addNotify();
        setFocusable(true);
        requestFocus();
        mainFrame.start();
        start();
    }

    /**
     * Change the LightCycle's direction based on the current pressed keys
     */
    public void move(){
        // Don't move if the game is done
        if(gameDone){
            return;
        }

        if(keys[KeyEvent.VK_RIGHT] ){
            players[0].setDir(Direction.EAST);
        }
        if(keys[KeyEvent.VK_LEFT] ){
            players[0].setDir(Direction.WEST);
        }
        if(keys[KeyEvent.VK_UP] ){
            players[0].setDir(Direction.NORTH);
        }
        if(keys[KeyEvent.VK_DOWN] ){
            players[0].setDir(Direction.SOUTH);
        }
        if(keys[KeyEvent.VK_SLASH] && players[0].getBoostCooldown() > GameSettings.getBoostCooldownTicks()){
            players[0].setBoostVal(GameSettings.getBoostTicks());
        }

        // Take keyboard input if in multiplayer mode, otherwise call the AI
        if(multi){
            if(keys[KeyEvent.VK_D] ){
                players[1].setDir(Direction.EAST);
            }
            if(keys[KeyEvent.VK_A] ){
                players[1].setDir(Direction.WEST);
            }
            if(keys[KeyEvent.VK_W] ){
                players[1].setDir(Direction.NORTH);
            }
            if(keys[KeyEvent.VK_S] ){
                players[1].setDir(Direction.SOUTH);
            }
            if(keys[KeyEvent.VK_F] && players[1].getBoostCooldown() > GameSettings.getBoostCooldownTicks()){
                players[1].setBoostVal(GameSettings.getBoostTicks());
            }
        }
        else{
            ai.performAction();
        }

    }

    /**
     * Check whether player is colliding with something that it shouldn't
     * @param i the index of the player to check
     */
    private void checkCollisions(int i) {
        // Check if player is out of bounds
        if(Collidable.checkCollide(restrictedArea, players[i].getHead())){
            wins[i^1]+=1; // Add a win to the opposing player
            LevelLogger.log("OOB");
            reset();
            //break;
        }

        // Check if player is colliding with itself or the other player
        for(int j=0;j<players.length;j++){
            if(Collidable.checkCollide(players[j], players[i].getHead())){
                wins[i^1]++; // Add a win to the opposing player
                LevelLogger.log(i + " u ded collide with "+j);
                reset();
                break;
            }
        }
    }

    /**
     * Handles ticks of gameTimer
     */
    class TickLoop implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            // Don't do anything if the game is done
            if(gameDone){
                return;
            }
            // Add parts to player and check if it collides
            for(int i=0;i<players.length;i++){
                players[i].addPart();
                checkCollisions(i);
            }

        }
    }

    /**
     * Handles ticks of boostTimer
     */
    class BoostLoop implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            // Don't do anything if the game is done
            if(gameDone){
                return;
            }
            for(int i=0;i<players.length;i++){
                // Only act if player is currently in boost stage
                if(players[i].getBoostVal() > 0){
                    LevelLogger.log("active boost for " + i + " " + players[i].getBoostVal());
                    players[i].addPart();
                    players[i].setBoostCooldown(0); // Set the player cooldown elapsed to 0
                    players[i].decBoostVal(); // Decrease the boost stage by 1
                    checkCollisions(i);
                }
                else {
                    // If player is not boosting, recharge their boost
                    players[i].rechargeBoost();
                }
            }
        }
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        try{
            keys[e.getKeyCode()] = true;
        }
        catch(ArrayIndexOutOfBoundsException x){
            LevelLogger.log("Modifier key detected");
        }
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    /**
     * Paints the game
     * @param g the Graphics object
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        // Stop drawing if the game is done
        if(gameDone){
            return;
        }

        // Draw the players
        for(LightCycle player: players){
            LevelLogger.log(player.toString());
            g.setColor(player.getColor());

            Iterator it = player.bodyParts.iterator();
            while(it.hasNext()){
                Rectangle t = (Rectangle) it.next();
                g.fillRect(t.x, t.y, t.width, t.height);
            }

        }

        // Draw the restricted areas
        g.setColor(Color.white);
        for(Rectangle area : restrictedArea.bodyParts){
            g.fillRect(area.x, area.y, area.width, area.height);
        }


        // Draw the boost bars
        // Player 1 boost bar
        g.setColor(players[0].getColor());
        // 60 - x co-ordinate to start the rectangle
        // 34 - y co-ordinate to start the rectangle
        // 100 - max width of the boost bar (multiplying that by the % recharge of the boost)
        // 20 - height of the boost bar
        g.fillRect(60, 34, (int) (100*((double)Math.min(players[0].getBoostCooldown(), GameSettings.getBoostCooldownTicks())/(double)GameSettings.getBoostCooldownTicks())), 20);

        // Player 2 boost bar
        g.setColor(players[1].getColor());

        // The width of the boost bar
        // 100 - max width of the boost bar (multiplying that by the % recharge of the boost)
        int player2BoostRectWidth = (int) (100*((double)Math.min(players[1].getBoostCooldown(), GameSettings.getBoostCooldownTicks())/(double)GameSettings.getBoostCooldownTicks()));
        // 60 - x co-ordinate offset from the right edge to start the rectangle
        // 34 - y co-ordinate to start the rectangle
        // 20 - height of the boost bar
        g.fillRect(GameSettings.getScreenWidth()-60-player2BoostRectWidth, 34,player2BoostRectWidth , 20);

        // Draw the number of wins
        g.setColor(Color.white);
        g.setFont(fontSys);

        // 30 - x co-ordinate to start the text
        // 54 - y co-ordinate to start the text
        g.drawString(Integer.toString(wins[0]),30,54);
        // 50 - x co-ordinate offset from the right edge to start the text
        // 54 - y co-ordinate to start the text
        g.drawString(Integer.toString(wins[1]),GameSettings.getScreenWidth()-50,54);

        // Draw the boost help text
        Font smallerText = fontSys.deriveFont(12f);

        g.setFont(smallerText);
        // 70 - x co-ordinate to start the text
        // 48 - y co-ordinate to start the text
        g.drawString("/ to BOOST!",70,48);
        // 47 - x co-ordinate offset from the right edge to start the text
        // 48 - y co-ordinate to start the text
        g.drawString("F to BOOST!",GameSettings.getScreenWidth()-47-player2BoostRectWidth,48);
    }
}