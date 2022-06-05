package main;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
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
    KeyHandler keyH = new KeyHandler(this);
    Sound sound = new Sound();
    
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
    
    // ENTITY AND OBJECT
    public Player player  = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];

    // GAME STATES
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // drawing graphics on off-screen , so when scrolled can see it later
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
    	
    	aSetter.setObject();
    	aSetter.setNPC();
    	gameState = playState;
    	//playMusic(0); // play background music
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


        while(gameThread != null){

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                update(); // 1 UPDATE: update information such as character positions
                play_meow(); // if player presses m, play a meow
                repaint(); // 2 DRAW: draw the screen with the updated information
                delta--;
            }
        }
        
    }

    public void update(){
        // PLAYER
    	if(gameState == playState) {
    		player.update();
            
        	// NPC
        	for(int i = 0; i < npc.length; i++) {
        		if(npc[i] != null) {
        			npc[i].update();
        		}
        	}
    	}
    	else if(gameState == pauseState) {
    		// nothing
    	}
    	else if(gameState == dialogueState) {
    		player.sitSoon();
    		for(int i = 0; i < npc.length; i++) {
        		if(npc[i] != null) {
        			npc[i].sitSoon();
        		}
    		}
    	}
    }
    
    public void play_meow() {
    	player.talk();
    	if(player.meow_now == true) {
    		System.out.println("m pressed");
    		playSE(0);
    	}
    	player.meow_now = false;
    }

    public void paintComponent(Graphics g){ // Graphics allows for drawing (paintbrush/pen)
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;  // Graphics2D class extends the Graphics class to provide more sophisticated control
        
        // TILE
        tileM.draw(g2); // draw tile first or else it covers player
        
        
        // OBJECT
        for(int i = 0; i < obj.length; i++) {
        	if(obj[i] != null) {
        		obj[i].draw(g2, this);
        	}
        }
        
        //NPC
        for(int i = 0; i < npc.length; i++) {
        	if(npc[i] != null) {
        		npc[i].draw(g2);
        	}
        }
        // PLAYER
        player.draw(g2);
        
        // UI
        ui.draw(g2);
        

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
