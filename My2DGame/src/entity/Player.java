package entity;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    
    public final int screenX;
    public final int screenY;
    public int fishCount = 0;
    public boolean meow_now = false;
    int standCounter = 0;
    
    //when player has not moved after certain amount of time, character sits down (standby mode)
    public boolean standBy = false;
    public boolean sit = false;
    int idleCounter = 0;
    
    

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {
    	// worldX and worldY = player's position on world map
        worldX = gp.tileSize * 23; 
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/cat_up.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/cat_up2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/player/cat_up_standby.png"));
            up4 = ImageIO.read(getClass().getResourceAsStream("/player/cat_up_sit.png"));
            up5 = ImageIO.read(getClass().getResourceAsStream("/player/cat_up_sit2.png"));
            
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/cat_down.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/cat_down2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/player/cat_down_standby.png"));
            down4 = ImageIO.read(getClass().getResourceAsStream("/player/cat_down_down.png"));
            down5 = ImageIO.read(getClass().getResourceAsStream("/player/cat_down_sit.png"));
            down6 = ImageIO.read(getClass().getResourceAsStream("/player/cat_down_sit2.png"));
            
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/cat_left.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/cat_left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/cat_left_standby.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/player/cat_left_sit.png"));
            left5 = ImageIO.read(getClass().getResourceAsStream("/player/cat_left_sit2.png"));
            
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/cat_right.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/cat_right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player/cat_right_standby.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/player/cat_right_sit.png"));
            right5 = ImageIO.read(getClass().getResourceAsStream("/player/cat_right_sit2.png"));
            

        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void talk() {
    	if(keyH.meow == true) {
    		meow_now = true;
    	}
    }
    public void update(){
    	
    	if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true ||
    			keyH.rightPressed == true) {
    		
    		idleCounter = 0;
    		standBy = false;
    		sit = false;
    		
    		if(keyH.upPressed == true){
                direction = "up";
            }
            else if(keyH.downPressed == true){
                direction = "down";
            }
            else if(keyH.leftPressed == true){
                direction = "left";
            }
            else if(keyH.rightPressed == true){
                direction = "right";
            }
    		
    		// CHECK TILE COLLISION
    		collisionOn = false;
    		gp.cChecker.checkTile(this);
    		
    		//CHECK OBJECT COLLISION
    		int objIndex = gp.cChecker.checkObject(this, true);
    		pickUpObject(objIndex);
    	
    		
    		// IF COLLISION IS FALSE, PLAYER CAN MOVE
    		if(collisionOn == false) {
    			switch(direction) {
    			case "up":  
    				worldY -= speed; 
    				break;
    			case "down":  
    				worldY += speed; 
    				break;
    			case "left":  
    				worldX -= speed; 
    				break;
    			case "right":  
    				worldX += speed; 
    				break;
    			}
 
    		}
            
            spriteCounter++;
            if(spriteCounter > 10) {
            	if(spriteNum == 1) {
            		spriteNum = 2;
            	}
            	else if(spriteNum == 2) {
            		spriteNum = 1;
            	}
            	else if(spriteNum == 3) {
            		spriteNum = 1;
            	}
            	else if(spriteNum == 4) {
            		spriteNum = 1;
            	}
            	spriteCounter = 0;
            }
    		
    	}
    	else {
    		standCounter++;
    		idleCounter++;
    		idle();
    		if(standBy == true) {
    			spriteNum = 3;
    		}
    		else if(sit == true) {
    			spriteNum = 4;
    		}
    		
    		
    
    	}
    	
    }
    
    public void idle() {
    	if(idleCounter == 60) {
    		if(keyH.upPressed == false && keyH.downPressed == false && keyH.leftPressed == false &&
        			keyH.rightPressed == false) {
    			standBy = true;
    			sit = false;
    			System.out.println("standBy?: " + standBy + " " + direction);
    		}
    	}
    	else if(idleCounter == 180) {
    		if(keyH.upPressed == false && keyH.downPressed == false && keyH.leftPressed == false &&
        			keyH.rightPressed == false) {
    			sit = true;
    			standBy = false;
    			System.out.println("sit?: " + sit + " " + direction);
    		}
    	}
    }
    
    public void pickUpObject(int i) {
    	if(i != 999) {
    		String objectName = gp.obj[i].name;
    		switch(objectName) {
    		case "Fish":
    			fishCount++;
    			gp.obj[i] = null;
    			gp.ui.showMessage("You got a fish!");
    			System.out.println("Fish:" + fishCount);
    			break;
    		}
    	}
    }

    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white);
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;
        
        

        switch(direction){
        case "up":
        	if(spriteNum == 1) {
        		image = up1;
        	}
        	if(spriteNum == 2) {
        		image = up2;
        	}
        	if(spriteNum == 3) {
        		image = up3;
        	}
        	if(spriteNum == 4) {
        		image = up4;
        	}
            break;
        case "down":
        	if(spriteNum == 1) {
        		image = down1;
        	}
        	if(spriteNum == 2) {
        		image = down2;
        	}
        	if(spriteNum == 3) {
        		image = down3;
        	}
        	if(spriteNum == 4) {
        		image = down4;
        	}
            break;
        case "left":
        	if(spriteNum == 1) {
        		image = left1;
        	}
        	if(spriteNum == 2) {
        		image = left2;
        	}
        	if(spriteNum == 3) {
        		image = left3;
        	}
        	if(spriteNum == 4) {
        		image = left4;
        	}
             break;
        case "right":
        	if(spriteNum == 1) {
        		image = right1;
        	}
        	if(spriteNum == 2) {
        		image = right2;
        	}
        	if(spriteNum == 3) {
        		image = right3;
        	}
        	if(spriteNum == 4) {
        		image = right4;
        	}
             break;
        }
        if(image == down4) {
        	g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        	g2.drawImage(down5, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
        else {
        	g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
        
    }
}
