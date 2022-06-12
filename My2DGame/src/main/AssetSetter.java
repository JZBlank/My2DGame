package main;

import java.awt.Graphics2D;

import entity.NPC_npc;
import object.OBJ_Fish;

public class AssetSetter {
	GamePanel gp;
	Graphics2D g2;
	
	public AssetSetter(GamePanel gp){
		this.gp = gp;
	}
	
	public void setObject() {
		
		// FISH (NO SWIM)
		gp.obj[0] = new OBJ_Fish(gp);
		gp.obj[0].worldX = 23 * gp.tileSize;
		gp.obj[0].worldY = 23 * gp.tileSize;
		gp.obj[0].id = 0;
		gp.obj[0].direction = "left";
		gp.obj[0].speed = 1;
		gp.obj[0].collisionOn = false;
		
		gp.obj[1] = new OBJ_Fish(gp);
		gp.obj[1].worldX = 12 * gp.tileSize;
		gp.obj[1].worldY = 32 * gp.tileSize;
		gp.obj[1].id = 1;
		gp.obj[1].direction = "left";
		gp.obj[1].speed = 1;
		gp.obj[1].collisionOn = false;
		
		// FISH (SWIMMING)
		gp.obj[2] = new OBJ_Fish(gp);
		gp.obj[2].worldX = 12 * gp.tileSize;
		gp.obj[2].worldY = 30 * gp.tileSize;
		gp.obj[2].id = 2;
		gp.obj[2].speed = 1;
		gp.obj[2].direction = "left";
		gp.obj[2].collisionOn = false;
		
		
	}
	public void setNPC() {
		gp.npc[0] = new NPC_npc(gp);
		gp.npc[0].collisionOn = true;
		gp.npc[0].worldX = gp.tileSize * 21;
		gp.npc[0].worldY = gp.tileSize * 21;
			
		
		gp.npc[1] = new NPC_npc(gp);
		gp.npc[1].collisionOn = true;
		gp.npc[1].worldX = gp.tileSize * 25;
		gp.npc[1].worldY = gp.tileSize * 30;
	}
	
}
