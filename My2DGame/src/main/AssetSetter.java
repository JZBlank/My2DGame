package main;

import entity.NPC_npc;
import object.OBJ_Fish;

public class AssetSetter {
	GamePanel gp;
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
		gp.npc[0].worldX = gp.tileSize * 21;
		gp.npc[0].worldY = gp.tileSize * 21;
		
		gp.npc[1] = new NPC_npc(gp);
		gp.npc[1].worldX = gp.tileSize * 26;
		gp.npc[1].worldY = gp.tileSize * 25;
	}
}
