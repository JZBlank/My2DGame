package main;

import java.awt.Graphics2D;

import entity.NPC_npc;
import object.OBJ_Bag;
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
		gp.obj[0].worldX = 10 * gp.tileSize;
		gp.obj[0].worldY = 23 * gp.tileSize;
		gp.obj[0].id = 0;
		gp.obj[0].direction = "left";
		gp.obj[0].speed = 1;
		gp.obj[0].collisionOn = false;
		
		gp.obj[1] = new OBJ_Fish(gp);
		gp.obj[1].worldX = 3 * gp.tileSize;
		gp.obj[1].worldY = 32 * gp.tileSize;
		gp.obj[1].id = 1;
		gp.obj[1].direction = "left";
		gp.obj[1].speed = 1;
		gp.obj[1].collisionOn = false;
		
		// FISH (SWIMMING)
		gp.obj[2] = new OBJ_Fish(gp);
		gp.obj[2].worldX = 9 * gp.tileSize;
		gp.obj[2].worldY = 34 * gp.tileSize;
		gp.obj[2].id = 2;
		gp.obj[2].speed = 1;
		gp.obj[2].direction = "left";
		gp.obj[2].collisionOn = false;
		
		gp.obj[3] = new OBJ_Bag(gp);
		gp.obj[3].worldX = gp.tileSize * 9 + 8;
		gp.obj[3].worldY = gp.tileSize * 14;
		gp.obj[3].id = 3;
		gp.obj[3].speed = 1;
		gp.obj[3].collisionOn = true;
		
		gp.obj[4] = new OBJ_Fish(gp);
		gp.obj[4].worldX = 27 * gp.tileSize;
		gp.obj[4].worldY = 25 * gp.tileSize;
		gp.obj[4].id = 4;
		gp.obj[4].speed = 1;
		gp.obj[4].direction = "left";
		gp.obj[4].collisionOn = false;
		
		gp.obj[5] = new OBJ_Fish(gp);
		gp.obj[5].worldX = 15 * gp.tileSize;
		gp.obj[5].worldY = 25 * gp.tileSize;
		gp.obj[5].id = 5;
		gp.obj[5].speed = 1;
		gp.obj[5].direction = "left";
		gp.obj[5].collisionOn = false;
		
		gp.obj[6] = new OBJ_Fish(gp);
		gp.obj[6].worldX = 14 * gp.tileSize;
		gp.obj[6].worldY = 25 * gp.tileSize;
		gp.obj[6].id = 6;
		gp.obj[6].speed = 1;
		gp.obj[6].direction = "left";
		gp.obj[6].collisionOn = false;
		
		gp.obj[7] = new OBJ_Fish(gp);
		gp.obj[7].worldX = 16 * gp.tileSize;
		gp.obj[7].worldY = 25 * gp.tileSize;
		gp.obj[7].id = 7;
		gp.obj[7].speed = 1;
		gp.obj[7].direction = "left";
		gp.obj[7].collisionOn = false;

		gp.obj[8] = new OBJ_Fish(gp);
		gp.obj[8].worldX = 13 * gp.tileSize;
		gp.obj[8].worldY = 25 * gp.tileSize;
		gp.obj[8].id = 8;
		gp.obj[8].speed = 1;
		gp.obj[8].direction = "left";
		gp.obj[8].collisionOn = false;
		
		
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
