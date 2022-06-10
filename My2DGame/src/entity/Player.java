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
    public SuperObject inventory[] = new SuperObject[1];
    
    public final int screenX;
    public final int screenY;
    public int fishCount = 0;
    public boolean meow_now = false;
    int standCounter = 0;
    
    // DEHYDRATION
    public int dehydration = 0;
    public int waterLevel = 9;
    
    //when player has not moved after certain amount of time, character sits down (standby mode)
    public boolean standBy = false;
    public boolean sit = false;
    
    // INTERACTIONS W/ NPC OR OBJECTS
    public boolean canInteract = false;
    public int whoInteract = -1; // CHECK IF NPC OR OBJECT
    public int targetIndex = -1; 
    
    
    // CHOOSING INTERACTIONS
    public boolean eat = false;
    public boolean drink = false;
    public boolean pickUp = false;
    public boolean talk = false;
    
    public boolean addHealth = false;
    public boolean hasBackPack = false;
    public boolean canPickUp = true;
    public int itemCounter = 0;
    
    public int waterCounter = 0;
    public int healthCounter = 0;
    
    public boolean notification = false;
    
    
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
        
        needWater = 0;
        dehydrationBar = 9 - needWater;
        
        alive = true;  
    }
    
    public BufferedImage cropImage(BufferedImage image) {
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
    		gp.npc[gp.player.targetIndex].sayDialogue();
    	}
    	keyH.changeDialogue = false;
    }
    
    public void update(){
    	survival();
    	
    	// CHECK IF M IS PRESSED
    	talk();
    	
    	// INTERACTIONS WITH OTHER WORLD OBJECTS/NPCS
    	interact();
    	showOptions();
    	chooseOption();
    	
    	
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
    		gp.cChecker.checkObject(this, true);
    	
    		// CHECK NPC COLLISION
    		gp.cChecker.checkEntity(this, gp.npc);
    		
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
    
    public void survival() {
    	
    	if(gp.survivalMode == true) {
    		if(dehydrationBar != -1) {
        		waterCounter++;
            	if(waterCounter == 180) {
            		waterCounter = 0;
            		dehydrationBar -= 1;
            	}
            	if(dehydrationBar == 0) {
            		loseHealth = true;
            		
            	}
        	}

        	if(loseHealth == true) {
        		healthCounter++;
        		if(healthCounter == 180) {
        			healthCounter = 0;
        			currentHealth -=1;
        		}	
        		if(currentHealth == 0) {
        			alive = false;
        		}
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
    
    public void pickUpObject() {
    	if(canInteract == true) {
    		String objectName = gp.obj[targetIndex].name;
    		switch(objectName) {
    		case "Fish":
    			fishCount++;
    			gp.ui.showMessage("You got a fish!");
    			break;
    		}
    	}
    }
    
    public void chooseOption() {
    	
    	if(gp.player.eat == true) {
    		gp.obj[targetIndex] = null;
    		if(gp.player.currentHealth < 9 && gp.player.addHealth == true) {
    			gp.player.currentHealth += 1;
    			gp.player.addHealth = false;
    			gp.player.eat = false;
    		}
    	}
    	else if(gp.player.pickUp == true) {
    		switch(gp.obj[targetIndex].name) {
    		case "Fish":
    			if(inventory[0] == null) {
    				inventory[itemCounter] = gp.obj[targetIndex]; //might give problems
    				gp.obj[targetIndex] = null;
    				itemCounter++;
    				fishCount++;
    				gp.player.canPickUp = false;
    				break;
    			}
    			else {
    				gp.player.notification = true;
    			}
    		}
    		gp.player.pickUp = false;
    		//gp.player.notification = false;
    	}
    }
    
    public void interactOptions() {
		options[0] = "Do nothing";
		options[1] = "Eat";
		options[2] = "Pick up";
		options[3] = "Talk";
	}
    
    
    
    
    public void interact() {
    	int checker = gp.cChecker.nextToSomething(this, gp.npc, gp.obj);
    	if(checker == 1 || checker ==  2) {
    		canInteract = true;
    		whoInteract = checker;
    	}
    	if(checker == 999) {
    		canInteract = false;
    		whoInteract = -1;
    	}
    }
    
    public void showOptions() {
    	if(canInteract == false) {
    		gp.gameState = gp.playState;
    	}
    	else {
    		if(whoInteract == 1) {
        		if(gp.keyH.ePressed == true) {
        			gp.npc[targetIndex].speak();
        			gp.gameState = gp.interactOBJState;
        		}
        	}
        	else if(whoInteract == 2) {
        		if(gp.keyH.ePressed == true) {
        			gp.gameState = gp.interactOBJState;
        		}
        	}
    		gp.keyH.enterPressed = false;
    	}
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
