package object;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Fish extends SuperObject {
	
	public OBJ_Fish(GamePanel gp) {
		super(gp);
		
		name = "fish";
		setDialogue();
		interactOptions();
		
//		solidArea.x = 1; // 8
//        solidArea.y = 10;  // 16
//        solidAreaDefaultX = solidArea.x;
//        solidAreaDefaultY = solidArea.y;
//        solidArea.width = 30; // 32
//        solidArea.height = 25; //32
        
		
		try {
			
			image1 = ImageIO.read(getClass().getResourceAsStream("/objects/defaultFish.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/objects/water_fish.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/objects/water_fish2.png"));
			image4 = ImageIO.read(getClass().getResourceAsStream("/objects/water_fish3.png"));
			image5 = ImageIO.read(getClass().getResourceAsStream("/objects/water_fish4.png"));
			
			image1 = uTool.scaleImage(image1,  gp.tileSize,  gp.tileSize);
			image2 = uTool.scaleImage(image2,  gp.tileSize,  gp.tileSize);
			image3 = uTool.scaleImage(image3,  gp.tileSize,  gp.tileSize);
			image4 = uTool.scaleImage(image4,  gp.tileSize,  gp.tileSize);
			image5 = uTool.scaleImage(image5,  gp.tileSize,  gp.tileSize);
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		collisionOn = false;
	}
	
	public void fishTracker() {
		// NUM OF FISH 
		int countFish = 0;
		
		for(int a = 0; a < gp.obj.length; a++) {
			if(gp.obj[a] != null) {
				if(gp.obj[a].name == "fish") {
					for(int b = 0; b < gp.aSetter.numWater; b++) {
						if(gp.obj[a].worldX/48 <= gp.aSetter.coordinates[b][0] && gp.obj[a].worldX/48 >= gp.aSetter.coordinates[b][2] &&
							gp.obj[a].worldY/48 <= gp.aSetter.coordinates[b][1] && gp.obj[a].worldY/48 >= gp.aSetter.coordinates[b][3]){
							totalPop[b] += 1;
						}
					}
				}
			}
		}
		System.out.println(totalPop[0] + " " +  totalPop[1] + " " + totalPop[2] + " " + totalPop[3] + " " + totalPop[4]);
		countFish = 0;
		
		for(int c = 0; c < gp.aSetter.numWater; c++) {
			totalPop[c] = 0;
		}
	}

	
	public void interact(GamePanel gp) {}
	
	public void setAction() {	
		fishTracker();
		
		int tileX = (gp.obj[id].worldX + 16)/ 48;
		int tileY = (gp.obj[id].worldY + 16) / 48;
		
		// CHECK IF FISH IS ABLE TO MOVE
		if(gp.tileM.mapTileNum[tileX][tileY] != 2) { // IF SURROUNDING AREA IS NOT WATER
			canMove = false;
		}
		else if(gp.tileM.mapTileNum[tileX][tileY] == 2){
			canMove = true;
			collisionOn = false;
			gp.cChecker.checkTile(this, id);
			moveLockCounter++;
		}
		
		if(canMove == false) {
			actionLockCounter++;
			Random random = new Random();
			int i = random.nextInt(100)+1;
			
			if(actionLockCounter == 20) { 
				if(i >= 33) {
					move = -1;
				}
				if(i <= 66) {
					move = 2;
				}
				else if(i <= 100) {
					move = 3;
				}
				actionLockCounter = 0;
			}
		}
		else if(canMove == true) {
			if(moveLockCounter == 180) { // every 3 seconds, a random move is chosen, can also choose standby mode
				Random random = new Random();
				
				int i = random.nextInt(100)+1; // picks a number from 1 to 100
				
				if(i <= 25) {
					direction = "up";
				}
				if(i > 25 && i <= 50) {
					direction = "down";
				}
				if(i > 50 && i<= 75) {
					direction = "left";
				}
				if(i > 75 && i <= 100) {
					direction = "right";
				}
					
				moveLockCounter = 0;
			}
			
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
		}
	}
	
	
	public void interactOptions() {
		options[0] = "Do nothing";
		options[1] = "Talk";
		options[2] = "Pick up";
		options[3] = "Eat";
	}
	
	public void setDialogue() {
		dialogues[0] = "...";
	}
}
