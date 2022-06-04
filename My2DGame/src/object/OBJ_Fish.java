package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Fish extends SuperObject {
	
	public OBJ_Fish() {
		name = "Fish";
		try {
			image1 = ImageIO.read(getClass().getResourceAsStream("/objects/defaultFish.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/objects/fish.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/objects/fish2.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}

}
