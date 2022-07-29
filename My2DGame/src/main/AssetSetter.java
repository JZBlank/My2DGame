package main;

import java.awt.Graphics2D;
import java.util.Random;

import entity.NPC_npc;
import object.OBJ_Bag;
import object.OBJ_Fish;
import object.OBJ_Garbage;

public class AssetSetter {
	GamePanel gp;
	Graphics2D g2;
	public int itemNum = 0;
	public int water[][] = new int[5][200];
	public int[][] coordinates = new int[5][4]; // coordinates to find fish in bodies of water
	public int numWater = 0;
	
	public AssetSetter(GamePanel gp){
		this.gp = gp;
	}
	
	public void setObject() {
		setFish();
		setSpecialFish();
		setBag();
		
		
		findWater();
		setGarbage();
	}
	
	public void setSpecialFish() {
		for(int i = 0; i < numWater; i++) {
			Random random = new Random();
			int q = random.nextInt(100)+1;
			int x = random.nextInt(coordinates[i][0] - coordinates[i][2]) + coordinates[i][2];
			int y = random.nextInt(coordinates[i][1]- coordinates[i][3]) + coordinates[i][3] ;
			
			if(q >= 90) {
				gp.obj[itemNum] = new OBJ_Fish(gp);
				gp.obj[itemNum].name = "blib";
				gp.obj[itemNum].worldX = gp.tileSize * x;
				gp.obj[itemNum].worldY = gp.tileSize * y;
				gp.obj[itemNum].id = itemNum;
				gp.obj[itemNum].speed = 2;
				gp.obj[itemNum].direction = "left";
				gp.obj[itemNum].collisionOn = false;
				itemNum +=1;
			}
			
			
		}
		
	}
	
	public void setGarbage() {
		for(int i = 0; i < numWater; i++) {
			
			Random random = new Random();
			int x = random.nextInt(coordinates[i][0] - coordinates[i][2]) + coordinates[i][2];
			int y = random.nextInt(coordinates[i][1]- coordinates[i][3]) + coordinates[i][3] ;
				
			gp.obj[itemNum] = new OBJ_Garbage(gp);
			gp.obj[itemNum].name_id = "waterBottle";
			gp.obj[itemNum].worldX = gp.tileSize * x;
			gp.obj[itemNum].worldY = gp.tileSize * y;
			gp.obj[itemNum].id = itemNum;
			gp.obj[itemNum].speed = -1;
			gp.obj[itemNum].collisionOn = true;
				
			System.out.println("GARBAGE LOCATIONS: " +  " " + gp.obj[itemNum].worldX/48 + " " + gp.obj[itemNum].worldY/48);
			itemNum += 1;
			
			gp.obj[itemNum] = new OBJ_Fish(gp);
			gp.obj[itemNum].name = "blib";
			gp.obj[itemNum].worldX = gp.tileSize * x;
			gp.obj[itemNum].worldY = gp.tileSize * y;
			gp.obj[itemNum].id = itemNum;
			gp.obj[itemNum].speed = 2;
			gp.obj[itemNum].direction = "left";
			gp.obj[itemNum].collisionOn = false;
			itemNum +=1;
		}
		
	}
	
	public void setBag() {
		// SET OTHER ITEMS
		gp.obj[itemNum] = new OBJ_Bag(gp);
		gp.obj[itemNum].worldX = gp.tileSize * 10 + 8;
		gp.obj[itemNum].worldY = gp.tileSize * 14;
		gp.obj[itemNum].id = itemNum;
		gp.obj[itemNum].speed = -1;
		gp.obj[itemNum].collisionOn = true;
		
		itemNum += 1;
	}
	
	
	public void findWater() {
		int[][] visited = new int[gp.maxWorldRow][gp.maxWorldCol];
		int reset = 0;
		
		
		// FIND TOTAL BODIES OF WATER ON MAP
		for(int i = 0; i < gp.maxWorldRow; i++) {
			for(int j = 0; j < gp.maxWorldCol; j++) {
				if(gp.tileM.mapTileNum[i][j] == 2 && visited[i][j] == 0) {
					numWater += 1;
					reset = 0;
					dfs(gp.tileM.mapTileNum, visited, i, j, numWater, reset);
				}
			}
		}
		
		int smallestX = 100;
		int smallestY = 100;
		
		int largestX = -1;
		int largestY = -1;
		
		for(int i = 0; i < water.length; i++) {
			for(int j = 0; j < water[i].length; j++) {
				if(water[i][j] != 0)  {
					if(j % 2 == 0) {
						if(smallestX > water[i][j]) {
							smallestX = water[i][j];
						}
						if(largestX < water[i][j]) {
							largestX = water[i][j];
						}
					}
					else if (j % 2 == 1) {
						if(smallestY > water[i][j]) {
							smallestY = water[i][j];
						}
						if(largestY < water[i][j]) {
							largestY = water[i][j];
						}
					}
				}
			}
			System.out.println("ROW " + i + "- LargestX: " + largestX + " LargestY: " + largestY + 
					" SmallestX: " + smallestX + " SmallestY: " + smallestY);
			
			coordinates[i][0] = largestX;
			coordinates[i][1] = largestY;
			coordinates[i][2] = smallestX;
			coordinates[i][3] = smallestY;
			
			smallestX = 100;
			smallestY = 100;
			
			largestX = -1;
			largestY = -1;
			
		}
	}
	
	
	public void dfs(int mapTileNum[][], int visited[][], int i, int j, int numWater, int reset) {
		if(i > gp.maxWorldRow || i < 0 || j > gp.maxWorldCol || j < 0) return;
		if(visited[i][j] == 1 || mapTileNum[i][j] != 2) return;
		
		visited[i][j] = 1;
		
		water[numWater-1][reset] = i;
		reset++;
		water[numWater-1][reset] = j;
		reset++;
		
		dfs(mapTileNum, visited, i-1, j, numWater, reset);
		dfs(mapTileNum, visited, i+1, j, numWater, reset);
		dfs(mapTileNum, visited, i, j-1, numWater, reset);
		dfs(mapTileNum, visited, i, j+1, numWater, reset);
		
	}
	

	
	public void setFish() {
		int landFish = 0;
		boolean fishCap = false;
		boolean special_fish = false;
		
		// Spawn fish in water
		for(int i = 0; i < gp.maxWorldRow; i++) {
			for(int j = 0; j < gp.maxWorldCol; j++) {
				Random random = new Random();
				int l = random.nextInt(100)+1;
				int m = random.nextInt(100)+1;
				int n = random.nextInt(100)+1;
		
				if(gp.tileM.mapTileNum[i][j] == 2) {
					//if(special_fish == false && n >= 50) {
						//special_fish = true;
					//}
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
		
		
		for(int s = 0; s < gp.obj.length; s++) {
			if(gp.obj[s] != null) {
				if(gp.obj[s].name == "blib") {
					System.out.println(gp.obj[s] + " " + gp.obj[s].worldX/48 + " " + gp.obj[s].worldY/48);
				}
			}
		}
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
