package main;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entity.Entity;
import entity.NPC_npc;
import entity.Player;
import object.OBJ_Fish;
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
    
    
    // GAME SETTINGS
    public boolean survivalMode = false;
    
    // ENTER NAME SCREEN VARIABLES
    boolean askName = true;
    boolean setName = true;
    boolean listenerCalled = false;
    
    
    // JTEXTFIELD + LABEL
    
    public JTextField jtf = new JTextField();
    
    // FPS
    int FPS = 60;
    
    // SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound sound = new Sound();
    
    
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
    
    // TILE EFFECTS 
    Effects effects = new Effects(this);
    
    // ENTITY AND OBJECT
    public Player player  = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];

    // GAME STATES
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int interactOBJState = 4;

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
    	
    	// STARTING SCREEN 
    	//gameState = titleState; 
    	// set up modes for titleState *** (Casual, survival mode, etc)
 
    	gameState = playState;
    	player.name = "Timmy";
    	
    	// ADD GAME MUSIC
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


        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                update(); // 1 UPDATE: update information depending on game state
                repaint(); // 2 DRAW: draw the screen with the updated information
                delta--;
            }
        }
        
    }

    public void update(){
    	// debug here

    	// 
    	
    	if(gameState == titleState) {
    		if(ui.titleScreenState == 1) { 
    			if(askName == true) { // HouseKeeping (SHORTEN THIS LATER / CREATE A NEW CLASS (?))
        	    	
        	    	// SET UP TEXTFIELD 
    				this.add(jtf);
        	    	int length = screenWidth/3;
        			int x = screenWidth/2 - length/2;
        	    	
        	    	jtf.setBounds(x, screenHeight/9, screenWidth/3, screenHeight/16);
        	    
        	    	
        	    	// ADD ACTION LISTENER
        	    	Action action = new AbstractAction()
        	    	{
        	    	    @Override
        	    	    public void actionPerformed(ActionEvent e)
        	    	    {
               	    	    	
        	    	    	if(jtf.getText().isEmpty()) {
        	    	    		ui.nameBlank = true;
        	    	    	}
        	    	    	else if(!jtf.getText().isEmpty()) {
        	    	    		ui.nameBlank = false;
            	    	        player.name =  jtf.getText();
            	    	        ui.titleScreenState = 2;
        	    	    	}
  
        	    	    }
        	    	};
        	    	
        	    	jtf.addActionListener(action);
        	    	listenerCalled = true;
        	    	
        	    	/////////

        	    	JTextField textField = new JTextField(10);
        	    	textField.addActionListener( action );
        	    	
        	    	jtf.setHorizontalAlignment(JTextField.CENTER);
        	    	jtf.setVisible(true);
    			}
    			askName = false;
    					
    		}
    		
    		else if(ui.titleScreenState == 2) {
    			jtf.setVisible(false);
    		}
    		
    	}
    	
    	if(gameState == playState) {
    		player.update();
    		player.updateOptions();
    		
    		for(int i = 0; i < obj.length; i++) {
        		if(obj[i] != null) {
        			obj[i].update();
        		}
        	}
    	
            
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
    		player.updateSit();
    		player.updateDialogue();
    		for(int i = 0; i < npc.length; i++) {
        		if(npc[i] != null) {
        			npc[i].updateSit();
        		}
    		}
    	}
    	else if(gameState == interactOBJState) {
    		player.update();
    		player.updateOptions();
    		
    		for(int i = 0; i < obj.length; i++) {
        		if(obj[i] != null) {
        			obj[i].update();
        		}
        	}
    		for(int i = 0; i < npc.length; i++) {
        		if(npc[i] != null) {
        			npc[i].update();
        		}
    		}
    	}
    }

    public void paintComponent(Graphics g){ // Graphics allows for drawing (paintbrush/pen)
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;  // Graphics2D class extends the Graphics class to provide more sophisticated control

        // TILE SCREEN
        if(gameState == titleState) {
        	ui.draw(g2);
        }
        // OTHERS
        else {
        	
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
            
            // TILE EFFECTS
            effects.draw(g2);
            
            // UI
            ui.draw(g2);
        }
        

        //g2.dispose(); // dispose of this graphics context and release any system resources that it is using
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
