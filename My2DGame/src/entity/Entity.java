package entity;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Entity {
    public int worldX, worldY;
    public int speed;
    
    GamePanel gp;
    KeyHandler keyH;
    // describes image with accessible buffer image data (use this to store image files)
    public BufferedImage up1, up2, up3, up4, up5;
    public BufferedImage down1, down2, down3, down4, down5, down6;
    public BufferedImage left1, left2, left3, left4, left5;
    public BufferedImage right1, right2, right3, right4, right5; 
    
    public String direction;
    public int spriteCounter = 0; // number of frames for sprite
    public int idleCounter = 0; // when to not move
    public int spriteNum = 3; // what image of sprite to use
    
    public Rectangle solidArea = new Rectangle(0,0, 48,48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public boolean standBy = false;
    
    // DIALOGUE
    String dialogues[][] = new String[5][20];
    int dialogueSet = 0;
    int dialogueIndex = 0; 
    
    
    public Entity(GamePanel gp) {
    	this.gp = gp;
    }
    
    public void setAction() {}
    public void updateDialogue() {}
    public void speak() {
    	
    	// DIALOGUE
    	dialogueIndex = 0; // start over
    	sayDialogue();
    	
    	// TURN NPC TOWARDS CHARACTER 
		switch(gp.player.direction) {
		
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}	
    }
    public void sayDialogue() {
    	if(dialogueIndex < 5 && dialogues[dialogueSet][dialogueIndex + 1] != null) {
    		gp.ui.moreDialogue = true;
    	}
    	else if(dialogueIndex < 5 && dialogues[dialogueSet][dialogueIndex + 1] == null) {
    		gp.ui.moreDialogue = false;
    	}
    	
    	// IF MESSAGE NOT NULL
    	if(dialogues[dialogueSet][dialogueIndex] != null) {
    		gp.ui.currentDialogue = dialogues[dialogueSet][dialogueIndex];
    	}
    	dialogueIndex++;
    	
    }
    public void updateSit() { // after dialogueStatus is initiated, cats become idle, aka sit down after some time
    	idleCounter++;
    	if(idleCounter > 10) {
    		spriteNum = 4;
    	}
    }
    
    public void update() {
    	setAction();
    	
    	collisionOn = false;
    	gp.cChecker.checkTile(this);
    	gp.cChecker.checkObject(this,  false);
    	gp.cChecker.checkPlayer(this);
    	
    	// IF COLLISION IS FALSE, PLAYER CAN MOVE
		if(collisionOn == false && standBy != true) {
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
        if(spriteCounter > 10) { // after every 1/60
        	if(standBy == true) {
        		spriteNum = 3;
        	}
        	else {
        		if(spriteNum == 1 ) {
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
        	}
        	spriteCounter = 0;
        }
    }
    public void draw(Graphics2D g2) {
    	
    	BufferedImage image = null;
    	int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
	    // STOP MOVING CAMERA
	    if(gp.player.worldX < gp.player.screenX) {
	    	screenX = worldX;
	    }
	    if(gp.player.worldY < gp.player.screenY) {
	    	screenY = worldY;
	    }   
	    int rightOffset = gp.screenWidth - gp.player.screenX;      
	    if(rightOffset > gp.worldWidth - gp.player.worldX) {
	    	screenX = gp.screenWidth - (gp.worldWidth - worldX);
	    } 
	    int bottomOffset = gp.screenHeight - gp.player.screenY;
	    if(bottomOffset > gp.worldHeight - gp.player.worldY) {
	    	screenY = gp.screenHeight - (gp.worldHeight - worldY);
	  }
	  ///////////////////
		  
		
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
		   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
		   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
		   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			switch(direction){
	        case "up":
	        	if(standBy == true) {
	        		image = up4;
	        	}
	        	else {
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
	        	}
	            break;
	        case "down":
	        	if(standBy == true) {
	        		image = down5;
	        	}
	        	else {
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
	        	}
	            break;
	        case "left":
	        	if(standBy == true) {
	        		image = left4;
	        	}
	        	else {
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
	        	}
	             break;
	        case "right":
	        	if(standBy == true) {
	        		image = right4;
	        	}
	        	else {
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
	        	}
	             break;
	        }
			
			
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
		       worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {    
				
				g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
			// If player is around the edge, draw everything
			else if(gp.player.worldX < gp.player.screenX ||
	                gp.player.worldY < gp.player.screenY ||
				    rightOffset > gp.worldWidth - gp.player.worldX ||
				    bottomOffset > gp.worldHeight - gp.player.worldY) {
				
				g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); 
			}
		}
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
    public BufferedImage setup(String imagePath) {
    	UtilityTool uTool = new UtilityTool();
    	BufferedImage image = null;
    	
    	try {
    		image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
    		image = uTool.scaleImage(image,  gp.tileSize,  gp.tileSize);
    		
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    	return image;
    }
    
}
