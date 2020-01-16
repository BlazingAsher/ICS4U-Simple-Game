import javafx.scene.effect.Light;

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
    private PlayArea playArea;

    public final static int DISPLAY_HEIGHT = 600;
    public final static int DISPLAY_WIDTH = 700;
    Timer myTimer;

    public GamePanel(TronLightCycleGame m){
        keys = new boolean[KeyEvent.KEY_LAST+1];
        //back = new ImageIcon("OuterSpace.jpg").getImage();

        playArea = new PlayArea(10,10,10,10, DISPLAY_WIDTH, DISPLAY_HEIGHT);

        int numPlayers = 2;
        players = new LightCycle[numPlayers];

        Direction[] directions = new Direction[]{Direction.WEST, Direction.NORTH, Direction.EAST, Direction.SOUTH};

        Color[] playerColors = new Color[] {Color.blue, Color.red};
        BufferedImage[] playerIcons;
        try{
            playerIcons = new BufferedImage[]{ImageIO.read(new File("cycles/cycle_blue.png")), ImageIO.read(new File("cycles/cycle_red.png")), ImageIO.read(new File("cycles/cycle_blue.png")), ImageIO.read(new File("cycles/cycle_blue.png"))};

            //players[0] = new LightCycle(300,400, Direction.WEST, playerColors[0], playerIcons[0]);
            for(int i=0;i<numPlayers;i++){
                players[i] = new LightCycle((int)(100+Math.random()*300), (int)(100+Math.random()*400), directions[(int) (Math.random()*4)], playerColors[i], playerIcons[i]);
            }
        }
        catch (IOException e){
            System.out.println(e);
        }

        mainFrame = m;

        setPreferredSize( new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));
        myTimer = new Timer(50, new TickLoop());
        addKeyListener(this);
    }

    public void start(){
        myTimer.start();
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



        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Point offset = getLocationOnScreen();
        //System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
    }

    class TickLoop implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            //System.out.println("Hello");
            for(int i=0;i<players.length;i++){
                players[i].addPart();
                if(!players[i].checkCollide(playArea, players[i].getHead())){
                    System.out.println("OOB");
                }
                for(int j=0;j<players.length;j++){
                    if(players[i].checkCollide(players[j], players[i].getHead())){
                        System.out.println(i + " u ded collide with "+j);
                    }
                }
            }

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
            //rot.scale(0.2, 0.2);
            //System.out.println(player.getDir());
            rot.rotate(Math.toRadians(player.getDir().getDeg()),0,0); 		// 75,84 is the center of my Image, this is the point of rotation.
            //rot.rotate(90,player.getIcon().getWidth(null)/2,player.getIcon().getHeight(null)/2); 		// 75,84 is the center of my Image, this is the point of rotation.

            AffineTransformOp rotOp = new AffineTransformOp(rot, AffineTransformOp.TYPE_BILINEAR); 	// The options are: TYPE_BICUBIC, TYPE_BILINEAR, TYPE_NEAREST_NEIGHBOR
            Graphics2D g2D = (Graphics2D)g;

            int drawX, drawY;
            drawX = player.getHead().x;
            drawY = player.getHead().y;
            //g2D.drawImage(player.getIcon(),rotOp,drawX,drawY);

            int icoW, icoH;
            icoW = player.getIcon().getWidth();
            icoH = player.getIcon().getHeight();
            //System.out.println(icoW + " " + icoH);

            int extrapx;
            switch(player.getDir()){
                case NORTH:
                    extrapx = icoH - player.WIDTH;
                    extrapx /= 2;
                    drawX -= extrapx;

                    drawY += icoW - player.HEIGHT -1;
                    break;
                case EAST:
                    extrapx = icoH - player.HEIGHT;
                    //System.out.println(extrapx);
                    extrapx /= 2;
                    //System.out.println(extrapx);
                    drawY -= extrapx;

                    drawX -= icoW - player.WIDTH -1;
                    break;
                case SOUTH:
                    extrapx = icoH - player.WIDTH;
                    extrapx /= 2;
                    drawX += extrapx + player.WIDTH;

                    drawY -= icoW - player.HEIGHT -1;
                    break;
                case WEST:
                    extrapx = icoH - player.HEIGHT;
                    //System.out.println(extrapx);
                    extrapx /= 2;
                    //System.out.println(extrapx);
                    drawY += extrapx + player.HEIGHT;

                    drawX += icoW - player.WIDTH -1;

            }

            g2D.drawImage(player.getIcon(),rotOp,drawX,drawY);
            //g.drawImage(player.getIcon(), player.getHead().x, player.getHead().y, null);
        }
    }
}