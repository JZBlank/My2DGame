package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Fish extends SuperObject {
	
	GamePanel gp;
	
	public OBJ_Fish(GamePanel gp) {
		name = "Fish";
		try {
			image1 = ImageIO.read(getClass().getResourceAsStream("/objects/defaultFish.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/objects/fish.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/objects/fish2.png"));
			uTool.scaleImage(image1,  gp.tileSize,  gp.tileSize);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}

}
