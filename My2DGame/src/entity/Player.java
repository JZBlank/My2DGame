package entity;
import java.awt.image.BufferedImage;

import main.AssetSetter;
import main.GamePanel;
import main.KeyHandler;
import object.SuperObject;

import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Player extends Entity {
    KeyHandler keyH;
    Entity[] npc;
    AssetSetter aSetter;
    public SuperObject inventory[] = new SuperObject[1];
    public SuperObject backpack[] = new SuperObject[5];
    
    public BufferedImage image = null;     
    
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
    
    public boolean holdItem = false;
    public boolean putItemDown = false; // has not been implemented yet
    
    public String itemDirection;
    
    // CHOOSING INTERACTIONS
    public boolean eat = false;
    public boolean drink = false;
    public boolean pickUp = false;
    public boolean talk = false;
    public boolean putOn = false;
    
    public boolean addHealth = false;
    public boolean hasBackPack = false;
    public boolean wearBackPack = false;
    public boolean canPickUp = true;
    public int holdingWhat = -1;
    
    // INVENTORY
    public boolean showInventory = false;
    
    // ITEMS MANAGER
    public int itemCounter = 0;
    public int backpackItemCounter = 0;
    
    public int waterCounter = 0;
    public int healthCounter = 0;
    
    public boolean canpressE = false;
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
    	//

    	//
    	survival();
    	
    	// IF PLAYER PRESSES M (MEOW)
    	talk();
    	
    	
    	canPickUpItem();
    	// IF PLAYER PRESSES I (INVENTORY)
    	showInventory();
    	
    	// IF PLAYER HOLDING AN ITEM AND WANTS TO PUT IT DOWN
    	putdownItem();
    	
    	// INTERACTIONS WITH OTHER WORLD OBJECTS/NPCS
    	interact();
    	showOptions();
    	updateOptions(); // delete later
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
    
    private void canPickUpItem() {
    	if((gp.player.pickUp == true || holdItem == true) && gp.player.hasBackPack == false) {
    		holdItem = true;
			holdingWhat = gp.obj[targetIndex].id;
    	}
    	else if((gp.player.pickUp == true || holdItem == true) && gp.player.backpack[4] != null){
    		holdItem = true;
    		holdingWhat = gp.obj[targetIndex].id;
    	}
    	else if(gp.player.wearBackPack == true && gp.player.backpack[4] == null) {
    		holdItem = false;
    	}
    	
    	
		if(gp.player.hasBackPack == false && gp.player.inventory[0] != null) {
			gp.player.canPickUp = false;
		}
		else if(gp.player.hasBackPack == false && gp.player.inventory[0] == null) {
			gp.player.canPickUp = true;
		}
		else if(gp.player.hasBackPack == true && gp.player.backpack[4] != null && gp.player.inventory[0] != null) {
			gp.player.canPickUp = false;
		}
		else if(gp.player.hasBackPack == true && gp.player.backpack[4] != null && gp.player.inventory[0] == null) {
			gp.player.canPickUp = true;
		}
		else if(gp.player.hasBackPack == true && gp.player.backpack[4] == null && gp.player.inventory[0] == null) {
			gp.player.canPickUp = true;
		}
		
	}
	private void putdownItem() {
    	
    	// WITHOUT BACKPACK
    	if(holdingWhat != -1) {
    		if(holdItem == true && putItemDown == true) {
    			if(keyH.ePressed == true) {
    				if(inventory[0].name == "fish") {
    					holdItem = false;
        				putItemDown = false;
        				fishCount--;
        				
        				// ITEM LOCATION CHANGES DEPENDING ON DIRECTION OF PLAYER
        				if(gp.player.direction == "up") {
        					gp.player.inventory[0].worldX = gp.player.worldX;
            				gp.player.inventory[0].worldY = gp.player.worldY - 20;
        				}
        				else if(gp.player.direction == "down") {
            				gp.player.inventory[0].worldX = gp.player.worldX;
            				gp.player.inventory[0].worldY = gp.player.worldY + 40;
        				}
        				else if(gp.player.direction == "left") {
        					gp.player.inventory[0].worldX = gp.player.worldX  - 30;
            				gp.player.inventory[0].worldY = gp.player.worldY + 30;
        				}
        				else if(gp.player.direction == "right") {
        					gp.player.inventory[0].worldX = gp.player.worldX + 40;
            				gp.player.inventory[0].worldY = gp.player.worldY + 20;
        				}
    	
        				
        				gp.player.inventory[0] = null;
        				itemCounter--;
        			}
    				else if(inventory[0].name == "bag") {
    					holdItem = false;
        				putItemDown = false;
        				
        				// ITEM LOCATION CHANGES DEPENDING ON DIRECTION OF PLAYER
        				if(gp.player.direction == "up") {
        					itemDirection = "none";
        					gp.player.inventory[0].worldX = gp.player.worldX;
            				gp.player.inventory[0].worldY = gp.player.worldY - 20;
        				}
        				else if(gp.player.direction == "down") {
        					itemDirection = "none";
            				gp.player.inventory[0].worldX = gp.player.worldX + 15;
            				gp.player.inventory[0].worldY = gp.player.worldY + 40;
        				}
        				else if(gp.player.direction == "left") {
        					itemDirection = "left";
        					gp.player.inventory[0].worldX = gp.player.worldX  - 20;
            				gp.player.inventory[0].worldY = gp.player.worldY + 30;
        				}
        				else if(gp.player.direction == "right") {
        					itemDirection = "right";
        					gp.player.inventory[0].worldX = gp.player.worldX  + 40;
            				gp.player.inventory[0].worldY = gp.player.worldY + 30;
        				}
    	
        				gp.player.hasBackPack = false;
        				gp.player.inventory[0] = null;
        				itemCounter--;
    				}
    			}	
    			keyH.ePressed = false;
    			holdingWhat = -1;
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
    
    public void chooseOption() {
    	if(gp.player.eat == true) {
    		gp.obj[targetIndex] = null;
    		if(gp.player.currentHealth < 9 && gp.player.addHealth == true) {
    			gp.player.currentHealth += 1;
    			gp.player.addHealth = false;
    			gp.player.eat = false;
    		}
    		gp.player.eat = false;
    	}
    	else if(gp.player.pickUp == true) {
    		pickUpLogic();
    		gp.player.pickUp = false;
    	}
    	else if(gp.player.putOn == true) {
    		gp.player.wearBackPack = true;
    		
    		// 0 as placeholder
			gp.obj[targetIndex].worldX = 0;
			gp.obj[targetIndex].worldY = 0;
			gp.player.putOn = false;
    	}
    }
    
    public void pickUpLogic() {
    	switch(gp.obj[targetIndex].name) {
		case "fish":
				if(canPickUp = false) {
					gp.player.notification = true;
				}
				
				else if(inventory[0] == null && wearBackPack == false) {
					System.out.println("TEST");
    				inventory[itemCounter] = gp.obj[targetIndex];
    				
    				// 0 as placeholder
    				gp.obj[targetIndex].worldX = 0;
    				gp.obj[targetIndex].worldY = 0;
    				
    				itemCounter++;
    				fishCount++;
    				break;
    			}
    			else if(wearBackPack == true) {
    				if(backpack[4] == null) {
    					backpack[backpackItemCounter] = gp.obj[targetIndex];
    					backpackItemCounter++;
    					
    					
    					// 0 as placeholder
        				gp.obj[targetIndex].worldX = 0;
        				gp.obj[targetIndex].worldY = 0;
        				
    				}
    				else if(backpack[4] != null) {
    					// 0 as placeholder
    					inventory[itemCounter] = gp.obj[targetIndex];
    					holdItem = true;
        				holdingWhat = gp.obj[targetIndex].id;
        				
        				gp.obj[targetIndex].worldX = 0;
        				gp.obj[targetIndex].worldY = 0;
        				
        				itemCounter++;
        				fishCount++;
        				gp.player.notification = true;
    				}
    			}
		case "bag":
			if(canPickUp = false) {
				gp.player.notification = true;
				break;
			}
			else if(inventory[0] == null) {
				inventory[itemCounter] = gp.obj[targetIndex];
				
				
				// 0 as placeholder
				gp.obj[targetIndex].worldX = 0;
				gp.obj[targetIndex].worldY = 0;
				hasBackPack = true;
				
				itemCounter++;
				break;
			}
		}
    }
    
    public void updateOptions() {
    	
    	if(canInteract == false) {
    		options[0] = "Put " + gp.player.inventory[gp.player.inventory.length - 1] + " down";
    		options[1] = null;
    		options[2] = null;
    		options[3] = null;
    	}
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
    
    public void showInventory() {
    	if(gp.keyH.iPressed == true && showInventory == true) {
    		showInventory = false;
    	}
    	else if(gp.keyH.iPressed == true && showInventory == false) {
    		showInventory = true;
    	}
    	gp.keyH.iPressed = false;
    }
    
    public void showOptions() {	
    	if(canInteract == false && holdItem == false) {
    		gp.gameState = gp.playState;
    	}
    	else if(canInteract == false && holdItem == true) {
    		canpressE = true;
    		if(gp.keyH.ePressed == true) {
    			putItemDown = true;
    		}
    	}
    	else {
    		canpressE = true;
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

        //BufferedImage image = null;       
        
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
