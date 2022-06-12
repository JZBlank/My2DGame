package object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class OBJ_healthBar extends SuperObject {
	
	GamePanel gp;
	
	public OBJ_healthBar(GamePanel gp) {
		super(gp);
		
		image1 = setup("empty_1");
		image2 = setup("empty_2");
		image3 = setup("empty_3");
		image4 = setup("red_1");
		image5 = setup("red_2");
		image6 = setup("red_3");
		image7 = setup("red_4");
		image8 = setup("blue_1");
		image9 = setup("blue_2");
		image10 =setup("blue_3");
		image11 = setup("blue_4");
		
	}
	
	public BufferedImage setup(String imagePath) {
		UtilityTool uTool = new UtilityTool();
    	BufferedImage image = null;
    	
    	try {
    		image = ImageIO.read(getClass().getResourceAsStream("/healthBar/" + imagePath + ".png"));
    		image = uTool.scaleImage(image,  48,  48);
    		
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    	return image;
	}
	
}
