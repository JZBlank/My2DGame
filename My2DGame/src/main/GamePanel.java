package main;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;

public class GamePanel extends JPanel implements Runnable {
    // Screen settings 
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize  * scale; // 48x48 tile // when you want to access from other packages, needs to be public
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // FPS
    int FPS = 40;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player  = new Player(this, keyH);

    // Set player's default position
    int playerX = 100;
    int playerY = 100;
    int speed = 4; // how many pixels moved at each movement

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // drawing graphics on off-screen , so when scrolled can see it later
        this.addKeyListener(keyH);
        this.setFocusable(true);
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
        
        player.draw(g2);

        g2.dispose(); // dispose of this graphics context and release any system resources that it is using
    }
}
