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
    private int[] wins;
    private Collidable playArea;
    private ArtificialPlayer ai;
    private boolean multi;
    private boolean gameDone;

    private String[] passArgs;

    public final static int DISPLAY_HEIGHT = 600;
    public final static int DISPLAY_WIDTH = 700;

    private Timer myTimer;
    private Timer scoreTimer;
   /* private int currScore;
    private int topScore;*/

    private JLabel currScoreLabel = new JLabel("");
    private JLabel topScoreLabel = new JLabel("");

    public GamePanel(TronLightCycleGame m, String passargs){
        System.out.println(passargs);
        this.passArgs = passargs.split(",");

        for(int i=0;i<this.passArgs.length;i++){
            if(i == 0){
                this.multi = this.passArgs[i].equals("multi");
            }
        }

        keys = new boolean[KeyEvent.KEY_LAST+1];
        //back = new ImageIcon("OuterSpace.jpg").getImage();
        setPreferredSize( new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));

        myTimer = new Timer(50, new TickLoop());
        scoreTimer = new Timer(100, new ScoreTickLoop());

        gameDone = false;

        //topScore = 0;
        wins = new int[GameSettings.getNumPlayers()];

        mainFrame = m;
        addKeyListener(this);
        init();
    }

    private void init() {
        playArea = new Collidable();
        //playArea.addPart(new Rectangle(10, 40, DISPLAY_WIDTH-(10+10), DISPLAY_HEIGHT-(10+40)));
        playArea.addPart(new Rectangle(0,0,DISPLAY_WIDTH, 10));
        playArea.addPart(new Rectangle(0,DISPLAY_HEIGHT-10,DISPLAY_WIDTH, 10));
        playArea.addPart(new Rectangle(0,0,10, DISPLAY_HEIGHT));
        playArea.addPart(new Rectangle(DISPLAY_WIDTH-10,0,10, DISPLAY_HEIGHT));

        players = new LightCycle[GameSettings.getNumPlayers()];

        Direction[] directions = new Direction[]{Direction.WEST, Direction.NORTH, Direction.EAST, Direction.SOUTH};


        //BufferedImage[] playerIcons;
        /*
        try{
            playerIcons = new BufferedImage[]{ImageIO.read(new File("cycles/cycle_blue.png")), ImageIO.read(new File("cycles/cycle_red.png")), ImageIO.read(new File("cycles/cycle_yellow.png")), ImageIO.read(new File("cycles/cycle_orange.png"))};

            //players[0] = new LightCycle(300,400, Direction.WEST, playerColors[0], playerIcons[0]);*/
            for(int i=0;i<GameSettings.getNumPlayers();i++){
                players[i] = new LightCycle((int)(100+Math.random()*300+150*i), (int)(200+Math.random()*300), directions[(int) (Math.random()*4)],GameSettings.getPlayerColoursObj()[Integer.parseInt(this.passArgs[i+1])]/*, playerIcons[i]*/);
            }/*
        }
        catch (IOException e){
            System.out.println(e);
        }*/

        ai = new ArtificialPlayer(players[1], players[0], playArea);

        //currScore = 0;

        currScoreLabel.setLocation(10,100);
        currScoreLabel.setSize(200,15);
        currScoreLabel.setText("Wins Player 1: " + wins[0]);
        add(currScoreLabel);

        topScoreLabel.setLocation(10,120);
        topScoreLabel.setSize(200,15);
        topScoreLabel.setText("Wins Player 2: " + wins[1]);
        add(topScoreLabel);

    }

    public void reset() {
        removeAll();
        if(Math.max(wins[0], wins[1]) == 3){
            gameDone = true;
            repaint();
            int dialogResult = JOptionPane.showConfirmDialog (null, "Player " + (wins[0] > wins[1] ? "1" : "2") + " wins! Play again?","Tron Lightcycles",JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                // Saving code here
                wins = new int[GameSettings.getNumPlayers()];
                gameDone = false;
            }
            else {
                mainFrame.finished[0] = true;
                myTimer.stop();
                scoreTimer.stop();
            }
        }

        if(!gameDone){
            init();
        }
        //topScore = Math.max(currScore, topScore);
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
        }
        if(keys[KeyEvent.VK_UP] ){
            players[0].setDir(Direction.NORTH);
        }
        if(keys[KeyEvent.VK_DOWN] ){
            players[0].setDir(Direction.SOUTH);
        }

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
        }
        else{
            ai.performAction();
        }


        //Point mouse = MouseInfo.getPointerInfo().getLocation();
        //Point offset = getLocationOnScreen();
        //System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
    }

    class TickLoop implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            //System.out.println("Hello");
            for(int i=0;i<players.length;i++){
                players[i].addPart();
                if(Collidable.checkCollide(playArea, players[i].getHead())[0]){
                    wins[i^1]+=1;
                    System.out.println("OOB");
                    reset();
                    break;
                }
                for(int j=0;j<players.length;j++){
                    boolean[] collisionResult = Collidable.checkCollide(players[j], players[i].getHead());
                    if(collisionResult[0]){
                        if(!collisionResult[1]){
                            wins[i^1]++;
                        }
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
        if(gameDone){
            return;
        }
        g.setColor(Color.black);
        for(Rectangle area : playArea.bodyParts){
            g.fillRect(area.x, area.y, area.width, area.height);
        }


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
            /*

            AffineTransform rot = new AffineTransform();
            rot.rotate(Math.toRadians(player.getDir().getDeg()),0,0);

            AffineTransformOp rotOp = new AffineTransformOp(rot, AffineTransformOp.TYPE_BILINEAR); 	// The options are: TYPE_BICUBIC, TYPE_BILINEAR, TYPE_NEAREST_NEIGHBOR
            Graphics2D g2D = (Graphics2D)g;

            int drawX, drawY;
            drawX = player.getHead().x + player.getIconXOffset();
            drawY = player.getHead().y + player.getIconYOffset();
            //g2D.drawImage(player.getIcon(),rotOp,drawX,drawY);

            g2D.drawImage(player.getIcon(),rotOp,drawX,drawY);*/
            //g.drawImage(player.getIcon(), player.getHead().x, player.getHead().y, null);
        }
    }
}