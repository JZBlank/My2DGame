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
	public int itemNum = 0;
	
	public AssetSetter(GamePanel gp){
		this.gp = gp;
	}
	
	public void setObject() {
		setFish(itemNum);
		setBag(itemNum);
		findWater();
	}
	
	public void findWater() {
		int numWater = 0;
		int[][] visited = new int[gp.maxWorldRow][gp.maxWorldCol];
		
		
		
		// FIND TOTAL BODIES OF WATER ON MAP
		for(int i = 0; i < gp.maxWorldRow; i++) {
			for(int j = 0; j < gp.maxWorldCol; j++) {
				if(gp.tileM.mapTileNum[i][j] == 2 && visited[i][j] == 0) {
					numWater += 1;
					dfs(gp.tileM.mapTileNum, visited, i, j);
					
				}
			}
		}
		
		System.out.println(numWater);
		
	}
	
	public void dfs(int mapTileNum[][], int visited[][], int i, int j) {
		if(i > gp.maxWorldRow || i < 0 || j > gp.maxWorldCol || j < 0) return;
		if(visited[i][j] == 1 || mapTileNum[i][j] != 2) return;
		
		visited[i][j] = 1;
		
		dfs(mapTileNum, visited, i-1, j);
		dfs(mapTileNum, visited, i+1, j);
		dfs(mapTileNum, visited, i, j-1);
		dfs(mapTileNum, visited, i, j+1);
		
	}
	
	public void setFish(int itemNum) {
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
	}
	
	public void setBag(int itemNum) {
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
