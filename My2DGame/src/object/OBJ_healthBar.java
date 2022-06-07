package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_healthBar extends SuperObject {
	
	GamePanel gp;
	
	public OBJ_healthBar(GamePanel gp) {
		
		try {
			image1 = ImageIO.read(getClass().getResourceAsStream("/healthBar/empty_1.png"));
			image2 =  ImageIO.read(getClass().getResourceAsStream("/healthBar/empty_2.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/healthBar/empty_3.png"));
			image4 = ImageIO.read(getClass().getResourceAsStream("/healthBar/full_1.png"));
			image5 = ImageIO.read(getClass().getResourceAsStream("/healthBar/full_2.png"));
			image6 = ImageIO.read(getClass().getResourceAsStream("/healthBar/full_3.png"));
			image7 = ImageIO.read(getClass().getResourceAsStream("/healthBar/bar_1.png"));
			image8 = ImageIO.read(getClass().getResourceAsStream("/healthBar/bar_2.png"));
			image9 = ImageIO.read(getClass().getResourceAsStream("/healthBar/bar_3.png"));
			image10 = ImageIO.read(getClass().getResourceAsStream("/healthBar/bar_4.png"));
			image11 = ImageIO.read(getClass().getResourceAsStream("/healthBar/bar_5.png"));
			

			image1 = uTool.scaleImage(image1,  gp.tileSize,  gp.tileSize);
			image2 = uTool.scaleImage(image2,  gp.tileSize,  gp.tileSize);
			image3 = uTool.scaleImage(image3,  gp.tileSize,  gp.tileSize);
			image4 = uTool.scaleImage(image4,  gp.tileSize,  gp.tileSize);
			image5 = uTool.scaleImage(image5,  gp.tileSize,  gp.tileSize);
			image6 = uTool.scaleImage(image6,  gp.tileSize,  gp.tileSize);
			image7 = uTool.scaleImage(image7,  gp.tileSize,  gp.tileSize);
			image8 = uTool.scaleImage(image8,  gp.tileSize,  gp.tileSize);
			image9 = uTool.scaleImage(image9,  gp.tileSize,  gp.tileSize);
			image10 = uTool.scaleImage(image10,  gp.tileSize,  gp.tileSize);
			image11 = uTool.scaleImage(image11,  gp.tileSize,  gp.tileSize);
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
