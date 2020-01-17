import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.imageio.*;
import java.util.Iterator;

public class GamePanel extends JPanel implements KeyListener {
    private boolean []keys;
    private Image back;
    private TronLightCycleGame mainFrame;
    private LightCycle[] players;
    private int[] losses;
    private Collidable playArea;
    private ArtificialPlayer ai;

    public final static int DISPLAY_HEIGHT = 600;
    public final static int DISPLAY_WIDTH = 700;

    private Timer myTimer;
    private Timer scoreTimer;
   /* private int currScore;
    private int topScore;*/

    private JLabel currScoreLabel = new JLabel("Score: 0");
    private JLabel topScoreLabel = new JLabel("Top Score: 0");

    public GamePanel(TronLightCycleGame m){
        keys = new boolean[KeyEvent.KEY_LAST+1];
        //back = new ImageIcon("OuterSpace.jpg").getImage();
        setPreferredSize( new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));

        myTimer = new Timer(50, new TickLoop());
        scoreTimer = new Timer(100, new ScoreTickLoop());

        //topScore = 0;
        losses = new int[GameSettings.getNumPlayers()];

        mainFrame = m;
        addKeyListener(this);
        init();
    }

    private void init() {
        playArea = new Collidable();
        playArea.addPart(new Rectangle(10, 150, DISPLAY_WIDTH-(10+10), DISPLAY_HEIGHT-(10+150)));

        players = new LightCycle[GameSettings.getNumPlayers()];

        Direction[] directions = new Direction[]{Direction.WEST, Direction.NORTH, Direction.EAST, Direction.SOUTH};

        Color[] playerColors = new Color[] {Color.blue, Color.red};
        BufferedImage[] playerIcons;
        try{
            playerIcons = new BufferedImage[]{ImageIO.read(new File("cycles/cycle_blue.png")), ImageIO.read(new File("cycles/cycle_red.png")), ImageIO.read(new File("cycles/cycle_blue.png")), ImageIO.read(new File("cycles/cycle_blue.png"))};

            //players[0] = new LightCycle(300,400, Direction.WEST, playerColors[0], playerIcons[0]);
            for(int i=0;i<GameSettings.getNumPlayers();i++){
                players[i] = new LightCycle((int)(100+Math.random()*300), (int)(200+Math.random()*300), directions[(int) (Math.random()*4)], playerColors[i], playerIcons[i]);
            }
        }
        catch (IOException e){
            System.out.println(e);
        }

        ai = new ArtificialPlayer(players[1], players[0], playArea);

        //currScore = 0;

        currScoreLabel.setLocation(10,100);
        currScoreLabel.setSize(200,15);
        currScoreLabel.setText("Wins Player 0: " + losses[1]);
        add(currScoreLabel);

        topScoreLabel.setLocation(10,120);
        topScoreLabel.setSize(200,15);
        topScoreLabel.setText("Wins Player 1: " + losses[0]);
        add(topScoreLabel);

    }

    public void reset() {
        removeAll();
        //topScore = Math.max(currScore, topScore);
        init();
    }

    public void start(){
        myTimer.start();
        scoreTimer.start();
    }

    public void addNotify() {
        super.addNotify();
        setFocusable(true);
        requestFocus();
        mainFrame.start();
        start();
    }

    public void move(){
        if(keys[KeyEvent.VK_RIGHT] ){
            players[0].setDir(Direction.EAST);
        }
        if(keys[KeyEvent.VK_LEFT] ){
            players[0].setDir(Direction.WEST);
            //System.exit(0);
        }
        if(keys[KeyEvent.VK_UP] ){
            players[0].setDir(Direction.NORTH);
        }
        if(keys[KeyEvent.VK_DOWN] ){
            players[0].setDir(Direction.SOUTH);
        }

        ai.performAction();

        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Point offset = getLocationOnScreen();
        //System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
    }

    class TickLoop implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            //System.out.println("Hello");
            for(int i=0;i<players.length;i++){
                players[i].addPart();
                if(!Collidable.checkCollide(playArea, players[i].getHead())){
                    losses[i]+=1;
                    System.out.println("OOB");
                    reset();
                    break;
                }
                for(int j=0;j<players.length;j++){
                    if(Collidable.checkCollide(players[j], players[i].getHead())){
                        losses[i]+=1;
                        System.out.println(i + " u ded collide with "+j);
                        reset();
                        break;
                    }
                }
            }

        }
    }

    class ScoreTickLoop implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            //System.out.println("Hello");
            //currScore += 10;
            //currScoreLabel.setText("Score: " + currScore);
        }
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(playArea.bodyParts.get(0).x, playArea.bodyParts.get(0).y, playArea.bodyParts.get(0).width, playArea.bodyParts.get(0).height);

        for(LightCycle player: players){
            g.setColor(player.getColor());

            Iterator it = player.bodyParts.iterator();
            while(it.hasNext()){
                Rectangle t = (Rectangle) it.next();
                if(t == player.getHead()){
                    g.setColor(Color.green);
                }
                g.fillRect(t.x, t.y, t.width, t.height);
            }

            AffineTransform rot = new AffineTransform();
            rot.rotate(Math.toRadians(player.getDir().getDeg()),0,0);

            AffineTransformOp rotOp = new AffineTransformOp(rot, AffineTransformOp.TYPE_BILINEAR); 	// The options are: TYPE_BICUBIC, TYPE_BILINEAR, TYPE_NEAREST_NEIGHBOR
            Graphics2D g2D = (Graphics2D)g;

            int drawX, drawY;
            drawX = player.getHead().x + player.getIconXOffset();
            drawY = player.getHead().y + player.getIconYOffset();
            //g2D.drawImage(player.getIcon(),rotOp,drawX,drawY);

            g2D.drawImage(player.getIcon(),rotOp,drawX,drawY);
            //g.drawImage(player.getIcon(), player.getHead().x, player.getHead().y, null);
        }
    }
}