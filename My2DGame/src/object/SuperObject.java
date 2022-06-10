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
	public boolean collisionOn = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	UtilityTool uTool = new UtilityTool();
	public int actionLockCounter;
	public int actionidleCounter;
	public int move = -1;
	
	public String[] options = new String[4];
	
	// DIALOGUE
    public String dialogues[] = new String[4];
    public int dialogueIndex = 0; 
	
	public boolean chooseEat = false;
	public boolean choosepickUp = false;
	
	public void setAction() {};
	public void update() {
		setAction();

	}
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
		   
			if(move == 1) {
				g2.drawImage(image2, screenX, screenY, gp.tileSize, gp.tileSize, null);
				g2.drawImage(image3, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
			else if(move == 2) {
				g2.drawImage(image3, screenX, screenY, gp.tileSize, gp.tileSize, null);
				g2.drawImage(image2, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
			else if(move == 3) {
				g2.drawImage(image3, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
		}
		// If player is around the edge, draw everything
		else if(gp.player.worldX < gp.player.screenX ||
		        gp.player.worldY < gp.player.screenY ||
		        rightOffset > gp.worldWidth - gp.player.worldX ||
		        bottomOffset > gp.worldHeight - gp.player.worldY) {
			
			g2.drawImage(image3, screenX, screenY, gp.tileSize, gp.tileSize, null); 
		}
	}
}
