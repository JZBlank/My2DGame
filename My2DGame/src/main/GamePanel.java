package main;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // Screen settings 
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize  * scale; // 48x48 tile // when you want to access from other packages, needs to be public
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    
    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    
    // FPS
    int FPS = 60;
    
    // SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound sound = new Sound();
    
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;
    
    // ENTITY AND OBJECT
    public Player player  = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // drawing graphics on off-screen , so when scrolled can see it later
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
    	
    	aSetter.setObject();
    	
    	playMusic(0); // play background music
    }
    
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS; // 1 second (1 billion nanoseconds) 0.01666 seconds
        
        // delta method:
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;


        while(gameThread != null){

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update(); // 1 UPDATE: update information such as character positions
                repaint(); // 2 DRAW: draw the screen with the updated information
                delta--;
                drawCount++;
            }
            
            if(timer >= 1000000000){
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
        
    }

    public void update(){
        player.update();

    }

    public void paintComponent(Graphics g){ // Graphics allows for drawing (paintbrush/pen)
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;  // Graphics2D class extends the Graphics class to provide more sophisticated control
        
        // TILE
        tileM.draw(g2); // draw tile first or covers player
        
        // OBJECT
        for(int i = 0; i < obj.length; i++) {
        	if(obj[i] != null) {
        		obj[i].draw(g2, this);
        	}
        }
        // PLAYER
        player.draw(g2);

        g2.dispose(); // dispose of this graphics context and release any system resources that it is using
    }
    
    public void playMusic(int i) {
    	sound.setFile(i);
    	sound.play();
    	sound.loop();
    }
    public void stopMusic() {
    	sound.stop();
    }
    public void playSE(int i) {
    	sound.setFile(i);;
    	sound.play();
    }
}
