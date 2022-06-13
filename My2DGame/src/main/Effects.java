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
	
	
	public Effects(GamePanel gp) {
		this.gp = gp;

		// TILE TEXTURES
		try {
			grassTexture = ImageIO.read(this.getClass().getResource("/tile_effects/grass1.png"));
			} catch (IOException e) {
			}
	}
	
	public void update() {	
		if(gp.tileM.mapTileNum[gp.player.worldX/48][gp.player.worldY/48] == 0 ||
		   gp.tileM.mapTileNum[gp.player.worldX/48][gp.player.worldY/48] == 9 ){
			System.out.println("PLAYER IS STANDING ON GRASS");
			gp.ui.g2.drawImage(grassTexture, gp.player.worldX/48, gp.player.worldY/48, gp.tileSize - 16, gp.tileSize - 16, null);
		}
		
		for(int i = 0; i < gp.npc.length; i++) {
			if(gp.npc[i] != null) {
				
				if(gp.tileM.mapTileNum[gp.npc[i].worldX/48][gp.npc[i].worldY/48] == 0 ||
				   gp.tileM.mapTileNum[gp.npc[i].worldX/48][gp.npc[i].worldY/48] == 9 ){
					//System.out.println("AN NPC IS STANDING ON GRASS");
					gp.ui.g2.drawImage(grassTexture, gp.npc[i].worldX/48, gp.npc[i].worldY/48, gp.tileSize - 16, gp.tileSize - 16, null);
				}
			}
			
		}
	}
}
