package main;

import java.awt.Graphics2D;

import entity.NPC_npc;
import object.OBJ_Fish;
import object.SuperObject;

public class AssetSetter {
	GamePanel gp;
	Graphics2D g2;
	
	public AssetSetter(GamePanel gp){
		this.gp = gp;
	}
	
	public void setObject() {
		gp.obj[0] = new OBJ_Fish(gp);
		gp.obj[0].worldX = 12 * gp.tileSize;
		gp.obj[0].worldY = 32 * gp.tileSize;
		
		gp.obj[1] = new OBJ_Fish(gp);
		gp.obj[1].worldX = 23 * gp.tileSize;
		gp.obj[1].worldY = 23 * gp.tileSize;
	}
	public void setNPC() {
		gp.npc[0] = new NPC_npc(gp);
		gp.npc[0].collisionOn = true;
		gp.npc[0].worldX = gp.tileSize * 21;
		gp.npc[0].worldY = gp.tileSize * 21;
	}
	
	public void relocateObject(SuperObject obj) {
		obj.worldX = 12 * gp.tileSize;
		obj.worldY = 32 * gp.tileSize;
	}
}
