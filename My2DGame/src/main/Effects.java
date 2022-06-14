package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import tile.TileManager;

public class Effects {
	GamePanel gp;
	Graphics2D g2;
	TileManager tM;
	
	BufferedImage grassTexture;
	
	int tileX;
	int tileY;
	
	public int worldX;
	public int worldY;
	
	
	public Effects(GamePanel gp) {
		this.gp = gp;

		// TILE TEXTURES
		try {
			grassTexture = ImageIO.read(this.getClass().getResource("/tile_effects/grass1.png"));
			} catch (IOException e) {
			}
	}
	
	public void update() {	
	}
	
	public void draw(Graphics2D g2) {
		if(gp.tileM.mapTileNum[gp.player.worldX/48][gp.player.worldY/48] == 0 ||
		   gp.tileM.mapTileNum[gp.player.worldX/48][gp.player.worldY/48] == 9 ) {
			g2.drawImage(grassTexture, 360, 264, gp.tileSize, gp.tileSize, null);
		}
		
		
    }
}
