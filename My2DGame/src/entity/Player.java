package entity;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;
import object.SuperObject;

import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Player extends Entity {
    KeyHandler keyH;
    Entity[] npc;
    
    public final int screenX;
    public final int screenY;
    public int fishCount = 0;
    public boolean meow_now = false;
    int standCounter = 0;
    
    //when player has not moved after certain amount of time, character sits down (standby mode)
    public boolean standBy = false;
    public boolean sit = false;
    public boolean ableToChat = false;
    public int whoConvo = -1;
    public int objInteract = -1;
    
    public boolean canInteract = false;
    

    public Player(GamePanel gp, KeyHandler keyH){
    	
    	super(gp);
    	
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
        
        
        // PLAYER NAME
        name = "";
        // PLAYER STATUS
        maxHealth = 9;
        currentHealth = maxHealth;
        
    }
    
    public BufferedImage cropImage(BufferedImage image) {
    	//down5 = setup("/player/cat_down_sit");
    	return image.getSubimage(0, 0, 48, 48/2);
    }

    public void getPlayerImage(){
        
        up1 = setup("/player/cat_up");
        up2 = setup("/player/cat_up2");
        up3 = setup("/player/cat_up_standby");
        up4 = setup("/player/cat_up_sit");
        up5 = setup("/player/cat_up_sit2");
        
        down1 = setup("/player/cat_down");
        down2 = setup("/player/cat_down2");
        down3 = setup("/player/cat_down_standby");
        //down4 = setup("/player/cat_down_down");
        down5 = setup("/player/cat_down_sit");
        down6 = setup("/player/cat_down_sit2");
        
        left1 = setup("/player/cat_left");
        left2 = setup("/player/cat_left2");
        left3 = setup("/player/cat_left_standby");
        left4 = setup("/player/cat_left_sit");
        left5 = setup("/player/cat_left_sit2");
        
        right1 = setup("/player/cat_right");
        right2 = setup("/player/cat_right2");
        right3 = setup("/player/cat_right_standby");
        right4 = setup("/player/cat_right_sit");
        right5 = setup("/player/cat_right_sit2");
         
    }
    
    
    public void talk() {
    	if(keyH.meow == true) {
    		gp.playSE(0);
    	}
    	meow_now = false;
    }
    
    public void updateSit() {
    	super.updateSit();
    }
    
    public void updateDialogue() {
    	if(gp.gameState == gp.dialogueState && keyH.changeDialogue == true) {
    		gp.npc[whoConvo].sayDialogue();
    	}
    	keyH.changeDialogue = false;
    }
    
    public void update(){
    	
    	
    	System.out.println(canInteract);
    	
    	// CHECK IF M IS PRESSED
    	talk();
    	
    	
    	// CHECK IF NEXT TO A NPC + IF ABLE TO INTERACT
    	whoConvo = gp.cChecker.nextToNPC(this, gp.npc);
    	chatNPC(whoConvo);
    	abletoInteract(whoConvo, null, gp.npc);
    	
    	// CHECK IF NEXT TO AN OBJECT + IF ABLE TO INTERACT
    	objInteract = gp.cChecker.nextToOBJ(this, gp.obj);
    	interactOBJ(objInteract);
    	abletoInteract(objInteract, gp.obj, null);
    	
    	canShowOptions(whoConvo, objInteract, gp.obj, gp.npc);
    	
//    	// CHECK IF NEXT TO A NPC + IF ABLE TO INTERACT
//    	whoConvo = gp.cChecker.nextToNPC(this, gp.npc);
//    	chatNPC(whoConvo);
//    	abletoInteract(whoConvo, null, gp.npc);
    	
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
    		
    		// CHECK OBJECT COLLISION
    		int objIndex = gp.cChecker.checkObject(this, true);
    		pickUpObject(objIndex);
    	
    		// CHECK NPC COLLISION
    		whoConvo = gp.cChecker.checkEntity(this, gp.npc);
    		interactNPC(whoConvo);
    		
    		
    		
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
    	if(idleCounter == 10) {
    		if(keyH.upPressed == false && keyH.downPressed == false && keyH.leftPressed == false &&
        			keyH.rightPressed == false) {
    			standBy = true;
    			sit = false;
    		}
    	}
    	else if(idleCounter == 120) {
    		if(keyH.upPressed == false && keyH.downPressed == false && keyH.leftPressed == false &&
        			keyH.rightPressed == false) {
    			sit = true;
    			standBy = false;
    		}
    	}
    }
    
    public void pickUpObject(int i) {
    	if(i != 999) {
    		String objectName = gp.obj[i].name;
    		switch(objectName) {
    		case "Fish":
    			fishCount++;
    			gp.ui.showMessage("You got a fish!");
    			break;
    		}
    	}
    }
    
    public void canShowOptions(int i, int j, SuperObject[] obj, Entity[] npc) {
    	if(abletoInteract(i, obj, npc) == true || abletoInteract(j, obj, npc) == true) {
    		canInteract = true;
    	}
    	else {
    		canInteract = false;
    	}
    }
    
    public boolean abletoInteract(int i, SuperObject[] obj, Entity[] npc) {
    	if(i != 999) {
    		if(obj != null) { // if can interact with object
    			return true;

    		}
    		if(npc != null) { // if can interact with npc
    			return true;
    		}
    	}
    	return false;
    }
    
    public void interactOBJ(int i) {
    	if(i != 999) {
    		if(gp.keyH.ePressed == true) {
    			gp.gameState = gp.interactOBJState; // show a screen with options 
        		//gp.obj[i].interact();
    		}
    	}
    }
    
    public void interactNPC(int i) {
    	if(i != 999) {
    		if(gp.keyH.ePressed == true) {
    			gp.gameState = gp.dialogueState;
        		gp.npc[i].speak();
    		}
    	}
    	gp.keyH.ePressed = false;
    }
    
    public void chatNPC(int nextToNPC) {
    	if(nextToNPC != 999) {
    		ableToChat = true;
    		if(gp.keyH.ePressed == true) {
    			gp.gameState = gp.dialogueState;
    			gp.npc[nextToNPC].speak();
    			gp.playSE(0);
    		}
    	}
    	else if(nextToNPC == 999) {
    		ableToChat = false;
    	}
    	gp.keyH.ePressed = false;
    }

    
    public void draw(Graphics2D g2) {

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
        
        int x = screenX;
        int y = screenY;
        
        if(screenX > worldX) {
        	x = worldX;
        }
        
        if(screenY > worldY) {
        	y = worldY;
        }
        int rightOffset = gp.screenWidth - screenX;
		if(rightOffset > gp.worldWidth - worldX) {
			x = gp.screenWidth - (gp.worldWidth - worldX);
		}
		int bottomOffset = gp.screenHeight - screenY;
		if(bottomOffset > gp.worldHeight - worldY) {
			y = gp.screenHeight - (gp.worldHeight - worldY);
		}
        
        if(image == down4) {
        	g2.drawImage(down5, x, y, null);
        }
        else {
        	g2.drawImage(image, x, y, null);
        }
        
    }
}
