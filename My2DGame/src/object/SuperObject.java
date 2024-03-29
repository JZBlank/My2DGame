package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

public class SuperObject {
	
	GamePanel gp;
	public BufferedImage image1, image2, image3, image4, image5, image6, image7, image8, image9, image10, image11;
    // public BufferedImage image7, image8, image9, image10, image11;
	public String name;
	public String name_id;
	public boolean collisionOn = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	UtilityTool uTool = new UtilityTool();
	
	
    // POPULATION
    public int[] totalPop = new int[5];
	
	// MOVEMENT
	public int actionLockCounter;
	public int moveLockCounter;
	public int actionidleCounter;
	public boolean standBy = false;
	public int spriteNum = 2; // what image of sprite to use
	public int spriteCounter = 0;
	public boolean collisonOn;
	public int speed = 0;
	
	
	public String direction;
	public int worldX1, worldY1;
	
	public int move = -1;
	public boolean canMove;
	
	public String[] options = new String[4];
	
	// DIALOGUE
    public String dialogues[] = new String[4];
    public int dialogueIndex = 0; 
	
	public boolean chooseEat = false;
	public boolean choosepickUp = false;
	
	public int id = -1;
	
	
	public SuperObject(GamePanel gp) {
		this.gp = gp;
	}
	public void setAction() {};
	public void update() {
		setAction();
    	
		
		 spriteCounter++;
		 if(spriteCounter > 10) { // after every 1/60
			 if(spriteNum == 1 ) {
				 spriteNum = 2;
	         }
	         else if(spriteNum == 2) {
	        	 spriteNum = 1;
	         }
	         else if(spriteNum == 4) {
	        	 spriteNum = 5;
	         }
	         else if(spriteNum == 5) {
	        	 spriteNum = 4;
	         }
			 spriteCounter = 0;
	     }
	}
	
	public void moveUpdate() {};
	public void interactOptions() {};
	public void interact(GamePanel gp) {}
	public void draw(Graphics2D g2, GamePanel gp) {
		
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
			
			// IF OBJECT IS FISH
			if(this.name == "fish" || this.name == "blib") {
				if(canMove == true) {
					if(this.name == "fish") {
						switch(direction){
				        case "up":
				        	if(spriteNum == 1) {
				        		image = image2;
				        	}
				        	if(spriteNum == 2) {
					        	image = image3;
					        }

				            break;
				        case "down":

				        	if(spriteNum == 1) {
					        	image = image2;
					        }
					        if(spriteNum == 2) {
					        	image = image3;;
					        }

				            break;
				        case "left":
				        	if(spriteNum == 1) {
					        	image = image2;
					        }
					        if(spriteNum == 2) {
					        	image = image3;
					        }
				             break;
				        case "right":
				        	if(spriteNum == 1) {
					        	image = image4;
					        }
					        if(spriteNum == 2) {
					        	image = image5;
					        }
				             break;
						}
					}
					else if(this.name == "blib") {
						switch(direction){
				        case "up":
				        	if(spriteNum == 1) {
				        		image = image7;
				        	}
				        	if(spriteNum == 2) {
					        	image = image8;
					        }

				            break;
				        case "down":

				        	if(spriteNum == 1) {
					        	image = image7;
					        }
					        if(spriteNum == 2) {
					        	image = image8;
					        }

				            break;
				        case "left":
				        	if(spriteNum == 1) {
					        	image = image7;
					        }
					        if(spriteNum == 2) {
					        	image = image8;
					        }
				             break;
				        case "right":
				        	if(spriteNum == 1) {
					        	image = image9;
					        }
					        if(spriteNum == 2) {
					        	image = image10;
					        }
				             break; 
						}
					}
				}
				
				// IF OBJECT IS FISH
				if(canMove == true) {
					g2.drawImage(image, screenX, screenY, gp.tileSize - 16, gp.tileSize - 16, null);
				}
				else {
					if(this.name == "fish") {
						if(move == 1 && canMove == false) {
							g2.drawImage(image2, screenX, screenY, gp.tileSize - 16, gp.tileSize - 16, null);
							g2.drawImage(image3, screenX, screenY, gp.tileSize - 16, gp.tileSize - 16, null);
						}
						else if(move == 2 && canMove == false) {
							g2.drawImage(image3, screenX, screenY, gp.tileSize - 16, gp.tileSize - 16, null);
							g2.drawImage(image2, screenX, screenY, gp.tileSize - 16, gp.tileSize - 16, null);
						}
						else if(move == 3 && canMove == false) {
							g2.drawImage(image3, screenX, screenY, gp.tileSize - 16, gp.tileSize - 16, null);
						}
					}
					else if(this.name == "blib") {
						if(move == 1 && canMove == false) {
							g2.drawImage(image7, screenX, screenY, gp.tileSize - 16, gp.tileSize - 16, null);
							g2.drawImage(image8, screenX, screenY, gp.tileSize - 16, gp.tileSize - 16, null);
						}
						else if(move == 2 && canMove == false) {
							g2.drawImage(image8, screenX, screenY, gp.tileSize - 16, gp.tileSize - 16, null);
							g2.drawImage(image7, screenX, screenY, gp.tileSize - 16, gp.tileSize - 16, null);
						}
						else if(move == 3 && canMove == false) {
							g2.drawImage(image8, screenX, screenY, gp.tileSize - 16, gp.tileSize - 16, null);
						}
					}
				}
			}
			
			else if(this.name == "garbage") {
				if(this.name_id == "waterBottle") {
					BufferedImage bottle = image1;
					g2.drawImage(bottle, screenX, screenY, gp.tileSize - 25, gp.tileSize - 25, null);
				}
				else if(this.name_id == "can") {
					BufferedImage can = image2;
					g2.drawImage(can, screenX, screenY, gp.tileSize - 25, gp.tileSize - 25, null);
				}
				
			}
			// IF OBJECT IS BAG
			else if(this.name == "bag") {
				BufferedImage bag = image1;
				if(gp.player.itemDirection == "left") {
					bag = image1;
				}
				if(gp.player.itemDirection == "right") {
					bag = image8;
				}
				else if(gp.player.direction == "none") {
					bag = image1;
				}

				g2.drawImage(bag, screenX, screenY, gp.tileSize - 25, gp.tileSize - 25, null);
			}
		}
		// If player is around the edge, draw everything
		else if(gp.player.worldX < gp.player.screenX ||
		        gp.player.worldY < gp.player.screenY ||
		        rightOffset > gp.worldWidth - gp.player.worldX ||
		        bottomOffset > gp.worldHeight - gp.player.worldY) {
			if(canMove == false) {
				g2.drawImage(image3, screenX, screenY, gp.tileSize - 16, gp.tileSize - 16, null); 
			}
		}
	}
}
