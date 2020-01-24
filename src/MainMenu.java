/*
 * File: MainMenu.java
 * Author: David Hui
 * Description: Main menu of the game. Allows player to configure some options and start the game.
 */
import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MainMenu extends JFrame{
    private JLayeredPane layeredPane=new JLayeredPane(); // Organize the JComponents into layers
    private static Sequencer midiPlayer; // MIDI music playback

    private TronLightCycleGame gameFrame; // JFrame of the game

    // Combo boxes to choose game options
    private JComboBox player1Colour;
    private JComboBox player2Colour;
    private JComboBox gameSpeed;

    private BgDesign bgDesign; // The background graphic

    boolean[] finished = new boolean[] { false }; // Array used by GamePanel to notify that game is finished
    Timer stackTimer; // Checks for updates to finished array

    private Font fontLocal; // Arcade font

    private Color[] chosenColours; // Colours of the players

    public MainMenu(){
        super("Tron Lightcycles");
        setSize(GameSettings.getScreenWidth(), GameSettings.getScreenHeight());
        setResizable(false);
        setBackground(Color.black);

        chosenColours = new Color[] {Color.blue, Color.red}; // Set default player colours

        // Load the Arcade Classic font
        InputStream is = GamePanel.class.getResourceAsStream("assets/ARCADECLASSIC.TTF");
        try{
            fontLocal = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(65f);
        }
        catch(IOException ex){
            LevelLogger.log(ex, LevelLogger.WARN);
        }
        catch(FontFormatException ex){
            LevelLogger.log(ex, LevelLogger.CRIT);
        }

        // Add the background graphic
        bgDesign = new BgDesign(chosenColours);
        bgDesign.setBounds(-125,0,900, 600);
        layeredPane.add(bgDesign, 1);

        // Add all the components
        addTitle();
        addLabels();
        addStartButtons();
        addComboBoxes();

        // Start the music
        startMidi("assets/AttheLake.mid");

        // Start the timer
        stackTimer = new Timer(10, new StackInterceptor());

        setContentPane(layeredPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] arguments) {
        MainMenu frame = new MainMenu();
        LevelLogger.setLogLevel(LevelLogger.CRIT);
    }

    /**
     * Starts playing MIDI music
     * @param midFilename the filename of the MIDI file
     */
    public static void startMidi(String midFilename) {
        try {
            File midiFile = new File(midFilename);
            Sequence song = MidiSystem.getSequence(midiFile);
            midiPlayer = MidiSystem.getSequencer();
            midiPlayer.open();
            midiPlayer.setSequence(song);
            midiPlayer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY); // repeat forever
            midiPlayer.start();

        } catch (MidiUnavailableException | InvalidMidiDataException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the "Tron Lightcycles" title
     */
    private void addTitle(){
        JLabel titleLabel = new JLabel("Tron     Lightcycles");
        titleLabel.setFont(fontLocal);
        titleLabel.setForeground(Color.white);
        titleLabel.setBounds(50, 10, 700, 125);
        layeredPane.add(titleLabel, 2);
    }

    /**
     * Adds the option labels
     */
    private void addLabels() {
        JLabel gameSpeedLabel = new JLabel("Game speed: ");
        gameSpeedLabel.setForeground(Color.white);
        gameSpeedLabel.setBounds(415, 260, 700, 20);
        layeredPane.add(gameSpeedLabel, 2);

        JLabel player1ColourLabel = new JLabel("Player 1 Colour: ");
        player1ColourLabel.setForeground(Color.white);
        player1ColourLabel.setBounds(450, 305, 700, 20);
        layeredPane.add(player1ColourLabel, 2);

        JLabel player2ColourLabel = new JLabel("Player 2 Colour: ");
        player2ColourLabel.setForeground(Color.white);
        player2ColourLabel.setBounds(450, 340, 700, 20);
        layeredPane.add(player2ColourLabel, 2);
    }

    /**
     * Adds the start buttons
     */
    private void addStartButtons() {
        JButton startSingleBtn = new JButton("Single Player");
        startSingleBtn.addActionListener(new ClickStart("single,"));
        startSingleBtn.setBounds(450,150,200,25);
        layeredPane.add(startSingleBtn,2);

        JButton startMultiBtn = new JButton("Multi Player");
        startMultiBtn.addActionListener(new ClickStart("multi,"));
        startMultiBtn.setBounds(450,185,200,25);
        layeredPane.add(startMultiBtn,2);
    }

    /**
     * Adds the option combo boxes
     */
    private void addComboBoxes() {
        gameSpeed = new JComboBox(GameSettings.getSpeedLabels());
        gameSpeed.setBounds(500,260,150,25);
        gameSpeed.setSelectedIndex(1);
        layeredPane.add(gameSpeed, 2);

        player1Colour = new JComboBox(GameSettings.getPlayerColours());
        player1Colour.addActionListener(new ColorUpdateListener(0));
        player1Colour.setBounds(550, 305,100,25);
        player1Colour.setSelectedIndex(0);
        layeredPane.add(player1Colour, 3);

        player2Colour = new JComboBox(GameSettings.getPlayerColours());
        player2Colour.addActionListener(new ColorUpdateListener(1));
        player2Colour.setBounds(550, 340,100,25);
        player2Colour.setSelectedIndex(1);
        layeredPane.add(player2Colour, 3);
    }

    /**
     * Fires when player changes a LightCycle colour
     */
    class ColorUpdateListener implements ActionListener {
        private int tronInd;
        public ColorUpdateListener(int tronInd){
            this.tronInd = tronInd;
        }
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox)e.getSource();
            int ind = cb.getSelectedIndex();
            chosenColours[tronInd] = GameSettings.getPlayerColoursObj()[ind];
            bgDesign.repaint();
        }
    }

    /**
     * Checks if the GamePanel has signalled that the game is done
     */
    class StackInterceptor implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            if(finished[0]){
                setVisible(true);
                gameFrame.setVisible(false);
                // Clean up the frame
                gameFrame.dispose();
                gameFrame = null;
                // Stop the check timer
                stackTimer.stop();
            }
        }
    }

    /**
     * Fires when player clicks a start button
     */
    class ClickStart implements ActionListener{
        String passargs; // Configuration string for the game
        ClickStart(String passargs) {
            this.passargs = passargs;
        }
        @Override
        public void actionPerformed(ActionEvent evt){
            finished[0] = false;
            // Initialize the game
            gameFrame = new TronLightCycleGame(finished, passargs+player1Colour.getSelectedIndex()+","+player2Colour.getSelectedIndex()+","+gameSpeed.getSelectedIndex());
            // Start the game end timer
            stackTimer.start();
            setVisible(false);
        }
    }

}
