package main;

import java.awt.Graphics2D;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

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
		int itemNum = 0;
		int landFish = 0;
		boolean fishCap = false;
		
		// Spawn fish in water
		for(int i = 0; i < gp.maxWorldRow; i++) {
			for(int j = 0; j < gp.maxWorldCol; j++) {
				Random random = new Random();
				int l = random.nextInt(100)+1;
				int m = random.nextInt(100)+1;
				
		
				if(gp.tileM.mapTileNum[i][j] == 2) {
					if(itemNum < 30) {
						if(l >= 50 && m >= 50) {
							gp.obj[itemNum] = new OBJ_Fish(gp);
							gp.obj[itemNum].worldX = i * gp.tileSize;
							gp.obj[itemNum].worldY = j * gp.tileSize;
							gp.obj[itemNum].id = itemNum;
							gp.obj[itemNum].speed = 1;
							gp.obj[itemNum].direction = "left";
							gp.obj[itemNum].collisionOn = false;
							itemNum +=1;
						}
					}
				}
			}
		}
		
		// SET OTHER ITEMS
		gp.obj[itemNum] = new OBJ_Bag(gp);
		gp.obj[itemNum].worldX = gp.tileSize * 9 + 8;
		gp.obj[itemNum].worldY = gp.tileSize * 14;
		gp.obj[itemNum].id = 3;
		gp.obj[itemNum].speed = -1;
		gp.obj[itemNum].collisionOn = true;
		
		
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
