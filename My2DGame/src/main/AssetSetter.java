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
		
		int fishNum = 9;
		int landFish = 0;
		boolean fishCap = false;
		
		// Spawn fish in water
		for(int i = 0; i < gp.maxWorldRow; i++) {
			for(int j = 0; j < gp.maxWorldCol; j++) {
				Random random = new Random();
				int l = random.nextInt(100)+1;
				int m = random.nextInt(100)+1;
				
		
				if(gp.tileM.mapTileNum[i][j] == 2) {
					if(fishNum < 30) {
						if(l >= 50 && m >= 50) {
							gp.obj[fishNum] = new OBJ_Fish(gp);
							gp.obj[fishNum].worldX = i * gp.tileSize;
							gp.obj[fishNum].worldY = j * gp.tileSize;
							gp.obj[fishNum].id = fishNum;
							gp.obj[fishNum].speed = 1;
							gp.obj[fishNum].direction = "left";
							gp.obj[fishNum].collisionOn = false;
							fishNum +=1;
						}
					}
				}
			}
		}
		
		System.out.println(gp.obj[9].worldX + " " +  gp.obj[9].worldY);
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
