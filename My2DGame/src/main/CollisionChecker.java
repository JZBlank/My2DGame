package main;

import java.awt.Graphics2D;

import entity.Entity;
import object.SuperObject;

public class CollisionChecker {
	
	GamePanel gp;
	Graphics2D g2;
	
	public int chatCounter = 0;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
		
	}
	
	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
		
	}
	
	public void checkTile(SuperObject obj, int id) {
		int objectLeftWorldX = obj.worldX + obj.solidArea.x;
		int objectRightWorldX = obj.worldX + obj.solidArea.x + obj.solidArea.width;
		int objectTopWorldY = obj.worldY + obj.solidArea.y;
		int objectBottomWorldY = obj.worldY + obj.solidArea.y + obj.solidArea.height;
		
		
		int objectLeftCol = objectLeftWorldX/gp.tileSize;
		int objectRightCol = objectRightWorldX/gp.tileSize;
		int objectTopRow = objectTopWorldY/gp.tileSize;
		int objectBottomRow = objectBottomWorldY/gp.tileSize;
		
		
		int tileNum1, tileNum2;
	
		switch(obj.direction) {
		case "up":
			objectTopRow = (objectTopWorldY - obj.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[objectLeftCol][objectTopRow];
			tileNum2 = gp.tileM.mapTileNum[objectRightCol][objectTopRow];
			if(gp.tileM.tile[tileNum1].fishCollision == true || gp.tileM.tile[tileNum2].fishCollision == true) {
				obj.collisionOn = true;
			}
			break;
		case "down":
			objectBottomRow = (objectBottomWorldY + obj.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[objectLeftCol][objectBottomRow];
			tileNum2 = gp.tileM.mapTileNum[objectRightCol][objectBottomRow];
			if(gp.tileM.tile[tileNum1].fishCollision == true || gp.tileM.tile[tileNum2].fishCollision == true) {
				obj.collisionOn = true;
			}
			break;
		case "left":
			objectLeftCol = (objectLeftWorldX - obj.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[objectLeftCol][objectTopRow];
			tileNum2 = gp.tileM.mapTileNum[objectLeftCol][objectBottomRow];
			if(gp.tileM.tile[tileNum1].fishCollision == true || gp.tileM.tile[tileNum2].fishCollision == true) {
				obj.collisionOn = true;
			}
			break;
		case "right":
			objectRightCol = (objectRightWorldX + obj.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[objectRightCol][objectTopRow];
			tileNum2 = gp.tileM.mapTileNum[objectRightCol][objectBottomRow];
			if(gp.tileM.tile[tileNum1].fishCollision == true || gp.tileM.tile[tileNum2].fishCollision == true) {
				obj.collisionOn = true;
			}
			break;
		}
		
	}
	
	public int checkObject(Entity entity, boolean player) {
		int index = 999;
		
		for(int i = 0; i < gp.obj.length; i++) {
			if(gp.obj[i] != null) {
				if(gp.obj[i].name == "fish") {
					index = 999;
				}
				else {

					// Get entity's solid area position
					entity.solidArea.x = entity.worldX + entity.solidArea.x;
					entity.solidArea.y = entity.worldY + entity.solidArea.y;
					// Get the object's solid area position
					gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
					gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
					
					switch(entity.direction) {
					case "up":
						entity.solidArea.y -= entity.speed;
						if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
							if(gp.obj[i].collisionOn == true) {
								entity.collisionOn = true;
							}
							if(player == true) {
								index = i;
							}
						}
						break;
					case "down":
						entity.solidArea.y += entity.speed;
						if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
							if(gp.obj[i].collisionOn == true) {
								entity.collisionOn = true;
							}
							if(player == true) {
								index = i;
							}
						}
						break;
					case "left":
						entity.solidArea.x -= entity.speed;
						if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
							if(gp.obj[i].collisionOn == true) {
								entity.collisionOn = true;
							}
							if(player == true) {
								index = i;
							}
						}
						break;
					case "right":
						entity.solidArea.x += entity.speed;
						if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
							if(gp.obj[i].collisionOn == true) {
								entity.collisionOn = true;
							}
							if(player == true) {
								index = i;
							}
						break;
						}
					}
					entity.solidArea.x = entity.solidAreaDefaultX;
					entity.solidArea.y = entity.solidAreaDefaultY;
					gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
					gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;

				}
			}
		}
		
		return index;
	}
	
	// NPC OR MONSTER 
	public int checkEntity(Entity entity, Entity[] target) {
		int index = 999;
		
		for(int i = 0; i < target.length; i++) {
			
			if(target[i] != null) {
				// Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				// Get the object's solid area position
				target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;
				
				switch(entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) {
						entity.collisionOn = true;
							index = i;
					}
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) {
						entity.collisionOn = true;
						index = i;

					}
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) {
						entity.collisionOn = true;
						index = i;
					}
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) {
						entity.collisionOn = true;
						index = i;
					break;
					}
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;

			}
		}
		
		return index;
	}
	public void checkPlayer(Entity entity) {
		// Get entity's solid area position
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;
		
		// Get the object's solid area position
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		
		switch(entity.direction) {
		case "up":
			entity.solidArea.y -= entity.speed;
			if(entity.solidArea.intersects(gp.player.solidArea)) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entity.solidArea.y += entity.speed;
			if(entity.solidArea.intersects(gp.player.solidArea)) {
				entity.collisionOn = true;

			}
			break;
		case "left":
			entity.solidArea.x -= entity.speed;
			if(entity.solidArea.intersects(gp.player.solidArea)) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entity.solidArea.x += entity.speed;
			if(entity.solidArea.intersects(gp.player.solidArea)) {
				entity.collisionOn = true;
			break;
			}
		}
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
	}
	
	public int nextToSomething(Entity entity, Entity[] npc, SuperObject[] obj ) {   
		int npcTarget = 999;
		int objTarget = 999;
		int result = 999;
		
		// RESET targetIndex
		gp.player.targetIndex = 0;
		
		// IF PLAYER IS NEXT TO NPC AND NOT OBJ = 1
		// IF PLAYER IS NEXT TO OBJECT AND NOT NPC = 2	
		// IF PLAYER IS NOT NEXT TO NPC NOR OBJECT  = 999
		
		for(int i = 0; i < npc.length; i++) {
			if(npc[i] != null) {
				if(entity.worldX >= npc[i].worldX - 45 && entity.worldX <= npc[i].worldX + 45 &&
					entity.worldY <= npc[i].worldY + 45 && entity.worldY >= npc[i].worldY - 60){
					chatCounter++;
					npcTarget = i;
					gp.player.targetIndex = i;
				}
			}
		}
		
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				if(entity.worldX >= obj[i].worldX - 45 && entity.worldX <=  obj[i].worldX + 45 &&
					entity.worldY <= obj[i].worldY + 45 && entity.worldY >=  obj[i].worldY - 60){
					objTarget = i;
					gp.player.targetIndex = i;
				}
			}
		}
		
		if(npcTarget == 999 && objTarget == 999) {
			return result;
		}
		else if(npcTarget != 999 & objTarget != 999) {
			result = 999;
		}
		else if(npcTarget != 999 && objTarget == 999) {
			result = 1;
		}
		else if(npcTarget == 999 && objTarget != 999) {
			result = 2;
		}
		
		return result;
	}

				
}
