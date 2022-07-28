package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_WaterBottle extends SuperObject {
	public OBJ_WaterBottle(GamePanel gp) {
		super(gp);
		name = "waterBottle";
		canMove = false;
		interactOptions();
		setDialogue();
		
		solidArea.x = 8; // 8
        solidArea.y = 10;  // 16
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 20; // 32
        solidArea.height = 25; //32
        
        try {
        	image1 = ImageIO.read(getClass().getResourceAsStream("/objects/waterBottle.png"));
    		image1 = uTool.scaleImage(image1,  gp.tileSize,  gp.tileSize);
 	
        } catch(IOException e) {
			e.printStackTrace();
		}
        
        collisionOn = true;
	}
	
	public void interact(GamePanel gp) {}
	
	public void interactOptions() {
		options[0] = "Do nothing";
		options[1] = "Inspect";
		options[2] = "Pick Up";
		options[3] = "...";
	}
	
	public void setDialogue() {
		dialogues[0] = "...";
	}
}
