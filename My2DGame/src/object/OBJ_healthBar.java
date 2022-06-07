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

			image1 = uTool.scaleImage(image1,  gp.tileSize,  gp.tileSize);
			image2 = uTool.scaleImage(image2,  gp.tileSize,  gp.tileSize);
			image3 = uTool.scaleImage(image3,  gp.tileSize,  gp.tileSize);
			image4 = uTool.scaleImage(image4,  gp.tileSize,  gp.tileSize);
			image5 = uTool.scaleImage(image5,  gp.tileSize,  gp.tileSize);
			image6 = uTool.scaleImage(image6,  gp.tileSize,  gp.tileSize);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
