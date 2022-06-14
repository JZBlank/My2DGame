package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Bag extends SuperObject {
	public OBJ_Bag(GamePanel gp) {
		super(gp);
		name = "bag";
		canMove = false;
		interactOptions();
		setDialogue();
		
		solidArea.x = 8; // 8
        solidArea.y = 10;  // 16
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30; // 32
        solidArea.height = 25; //32
        
        try {
        	image1 = ImageIO.read(getClass().getResourceAsStream("/objects/red_backpack1.png"));
        	image2 = ImageIO.read(getClass().getResourceAsStream("/objects/red_backpack2.png"));
        	image3 = ImageIO.read(getClass().getResourceAsStream("/objects/red_backpack3.png"));
        	image4 = ImageIO.read(getClass().getResourceAsStream("/objects/red_backpack4.png"));
        	image5 = ImageIO.read(getClass().getResourceAsStream("/objects/red_backpack5.png"));
        	image6 = ImageIO.read(getClass().getResourceAsStream("/objects/red_backpack6.png"));
        	image7 = ImageIO.read(getClass().getResourceAsStream("/objects/red_backpack7.png"));
        	image8 = ImageIO.read(getClass().getResourceAsStream("/objects/red_backpack8.png"));
        	
    		image1 = uTool.scaleImage(image1,  gp.tileSize,  gp.tileSize);
    		image2 = uTool.scaleImage(image2,  gp.tileSize,  gp.tileSize);
    		image3 = uTool.scaleImage(image3,  gp.tileSize,  gp.tileSize);
    		image4 = uTool.scaleImage(image4,  gp.tileSize,  gp.tileSize);
    		image5 = uTool.scaleImage(image5,  gp.tileSize,  gp.tileSize);
    		image6 = uTool.scaleImage(image6,  gp.tileSize,  gp.tileSize);
    		image7 = uTool.scaleImage(image7,  gp.tileSize,  gp.tileSize);
    		image8 = uTool.scaleImage(image8,  gp.tileSize,  gp.tileSize);
    		
    		
    		
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
		options[3] = "Put On";
	}
	
	public void setDialogue() {
		dialogues[0] = "...";
	}
}
