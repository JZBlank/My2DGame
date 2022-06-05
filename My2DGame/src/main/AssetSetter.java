package main;

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
	}
}
