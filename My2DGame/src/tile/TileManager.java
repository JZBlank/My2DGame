package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	public int[] mapArray;
	
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[100];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		
		// MODIFY MAP DATA
		//modifyMapData("/maps/world01.txt");
		
		
		// CREATING MAP
		getTileImage();
		loadMap("/maps/newWorld01.txt");
		modifyMap();
	}
	
	
	public void getTileImage() {

			setup("00", "grass_empty", false, true);
			setup("01", "metal_horizontal", true, true);
			setup("02", "water", true, false);
			setup("03", "wood_horizontal", false, true);
			setup("04", "green_tree", true, true);
			setup("05", "dirt_empty", false,true);
			setup("06", "sand", false, true);
			setup("07", "metal_vertical", true, true);
			setup("08", "wood_vertical", true, true);
			setup("09", "grass_full", false, true);
			
			setup("10", "dirt_horizontal1", false, true);
			setup("11", "dirt_horizontal2", false, true);
			setup("12", "dirt_vertical1", false, true);
			setup("13", "dirt_vertical2", false, true);
			
			setup("14", "dirt_corner_upper_left", false, true);
			setup("15", "dirt_corner_upper_right", false, true);
			setup("16", "dirt_corner_bottom_left", false, true);
			setup("17", "dirt_corner_bottom_right", false, true);
			
			setup("18", "dirt_top1", false, true);
			setup("19", "dirt_bottom1", false, true);
			
			setup("20", "dirt_top_sides1", false, true);

	}
	
	public void setup(String str, String imageName, boolean collision, boolean fishCollision) { 
		UtilityTool uTool = new UtilityTool();
		try {
			// CONVERT STRING TO INDEX		
			int num =  convertStrIndex(str);
			tile[num] = new Tile();
			tile[num].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[num].image = uTool.scaleImage(tile[num].image, gp.tileSize, gp.tileSize);
			tile[num].collision = collision;
			tile[num].fishCollision = fishCollision;
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public int convertStrIndex(String str) {
		int result = 0;
		String num;
		
		if(str.charAt(0) == '0') {
			num = String.valueOf(str.charAt(1));
			result = Integer.parseInt(num);
		}
		else {
			result = Integer.parseInt(str);
		}
		return result;
	}
	
	public String convertNums(String line) {
		String s = "";
		String t = "";

		//EX: "00 00 00 00 00 00 00 00 00 00 .."
		
		for(int i = 0; i < line.length(); i++) {
			if(line.charAt(i) == ' ') {
				if(t.charAt(0) == '0') {
					t = String.valueOf(t.charAt(1));
				}
				s+= t;
				if(i != line.length() - 1) {
					s += " ";
				}
				t = "";
			}
			else {
				t += String.valueOf(line.charAt(i));
			}
		}
		return s;
	}
	
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
		
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				
				
				while(col < gp.maxWorldCol) {
					String[] numbers = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					
					num = randomizeGrass(num);
					
					mapTileNum[col][row] = num;
					col += 1;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		} catch(Exception e){
			
		}
	}
	
	public void modifyMap() {
		
		// i = left and right j = up and down
		for(int i = 0; i < 50; i++) {
			for(int j = 0; j < 50; j++) {
				if(mapTileNum[i][j] == 5) {
					// IF LEFT AND RIGHT TILES ARE TREES
					if((mapTileNum[i-1][j] == 4 || mapTileNum[i-1][j] == 0) && (mapTileNum[i+1][j] == 4 || mapTileNum[i+1][j] == 0)) {
						// IF UPPER TILE IS A TREE
						if(mapTileNum[i][j-1] == 4 || mapTileNum[i][j-1] == 0) {
							mapTileNum[i][j] = 20;
						}
						else {
							mapTileNum[i][j] = 12;
						}
					}
					
					// IF BOTTOM IS TREE, LEFT, RIGHT AND TOP ARE NOT TREES OR GRASS **BOTTOM PATH**
					else if((mapTileNum[i-1][j] == 5 || mapTileNum[i-1][j] == 10 || mapTileNum[i-1][j] == 19 || mapTileNum[i-1][j] == 16) &&
							(mapTileNum[i+1][j] == 5 || mapTileNum[i+1][j] == 10 || mapTileNum[i+1][j] == 17 || mapTileNum[i+1][j] == 19) && 
							(mapTileNum[i][j-1] == 5 || mapTileNum[i][j-1] == 18) &&
							(mapTileNum[i][j+1] == 4 || mapTileNum[i][j+1] == 9 || mapTileNum[i][j+1] == 0)) {
						mapTileNum[i][j] = 19;
					}
					
					// IF UPPER IS TREE, LEFT, RIGHT AND BOTTOM ARE NOT TREES OR GRASS **TOP PATH**
					else if((mapTileNum[i-1][j] == 5 || mapTileNum[i-1][j] == 10 || mapTileNum[i-1][j] == 18 || mapTileNum[i-1][j] == 14) &&
							(mapTileNum[i+1][j] == 5 || mapTileNum[i+1][j] == 10 || mapTileNum[i+1][j] == 15)  && 
							(mapTileNum[i][j-1] == 4 || mapTileNum[i][j-1] == 9 || mapTileNum[i][j-1] == 0) && mapTileNum[i][j+1] == 5) {
						mapTileNum[i][j] = 18;
					}
					
					
					// IF UP AND RIGHT ARE TREES, LEFT AND DOWN NOT GRASS OR TREES (PATH CORNER **UPPER RIGHT**)
					else if((mapTileNum[i-1][j] != 4 && mapTileNum[i][j+1] != 4) && (mapTileNum[i][j-1] == 4 && mapTileNum[i+1][j] == 4)){
						mapTileNum[i][j] = 15;
					}
					
					// IF UP AND LEFT ARE TREES, RIGHT AND DOWN NOT GRASS OR TREES  (PATH CORNER ** UPPER LEFT**)
					else if((mapTileNum[i+1][j] != 4 && mapTileNum[i][j+1] != 4) && ((mapTileNum[i][j-1] == 4 || mapTileNum[i][j-1] == 0) && (mapTileNum[i-1][j] == 4 || mapTileNum[i-1][j] == 0))){
						mapTileNum[i][j] = 14;
					}
							
					// IF UPPER TILE IS A TREE AND LEFT AND RIGHT TILES ARE NOT TREES/GRASS **HORIZONTAL PATH*
					else if((mapTileNum[i][j-1] == 4 || mapTileNum[i][j-1] == 0 || mapTileNum[i][j-1] == 9) && (mapTileNum[i-1][j] != 5 || mapTileNum[i+1][j] != 4)){
						// IF BOTTOM TILE IS A TREE OR GRASS
						if(mapTileNum[i][j+1] == 4 || mapTileNum[i][j+1] == 0 || mapTileNum[i][j+1] == 9) {
							mapTileNum[i][j] = 10;
						}
					}
					// IF BOTTOM AND RIGHT TILE IS TREE, LEFT AND UP IS NOT TREE ** (PATH CORNER **BOTTOM RIGHT**)
					else if((mapTileNum[i+1][j] == 4 || mapTileNum[i+1][j] == 0) && mapTileNum[i-1][j] != 4 && mapTileNum[i][j-1] != 4 && (mapTileNum[i][j+1] == 4 || mapTileNum[i][j+1] == 0)) {
						mapTileNum[i][j] = 17;
					}
					
					// IF BOTTOM AND LEFT TILE IS TREE, LEFT AND UP IS NOT TREE ** (PATH CORNER **BOTTOM LEFT**)
					else if(mapTileNum[i+1][j] != 4 && mapTileNum[i-1][j] == 4 && mapTileNum[i][j-1] != 4 && mapTileNum[i][j+1] == 4) {
						mapTileNum[i][j] = 16;
					}
					
					// ONLY UPPER HAS TREE/GRASS
					else if(mapTileNum[i+1][j] != 4 && mapTileNum[i-1][j] != 4 && mapTileNum[i][j-1] == 4 && mapTileNum[i][j+1] != 4) {
						mapTileNum[i][j] = 4;
					}
					
				}
			}
		}
	}
	
	public int randomizeGrass(int num) {
		// RANDOMIZE GRASS TEXTURE
		if(num == 0) {
			// CHOOSE RANDOM NUMBER FOR GRASS 
			Random random = new Random();
			int i = random.nextInt(100)+1;
			if(i >= 10) {
				num = 0;
			}
			else {
				num = 9;
			}
		}
		return num;
	}

	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			// Stop moving camera at the edge
			if(gp.player.screenX > gp.player.worldX) {
				screenX = worldX;
			}
			
			if(gp.player.screenY > gp.player.worldY) {
				screenY = worldY;
			}
			
			int rightOffset = gp.screenWidth - gp.player.screenX;
			if(rightOffset > gp.worldWidth - gp.player.worldX) {
				screenX = gp.screenWidth - (gp.worldWidth - worldX);
			}
			int bottomOffset = gp.screenHeight - gp.player.screenY;
			if(bottomOffset > gp.worldHeight - gp.player.worldY) {
				screenY = gp.screenHeight - (gp.worldHeight - worldY);
			}
			
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			else if(gp.player.screenX > gp.player.worldX ||
					gp.player.screenY > gp.player.worldY ||
					rightOffset > gp.worldWidth - gp.player.worldX ||
					bottomOffset > gp.worldHeight - gp.player.worldY) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			
			worldCol++;
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
	
	public void modifyMapData(String filePath) {
		 try {
			 	InputStream is = getClass().getResourceAsStream(filePath);
				BufferedReader file = new BufferedReader(new InputStreamReader(is));
			 
		        // input the file content to the StringBuffer "input"
		        StringBuffer inputBuffer = new StringBuffer();
		        String line = "hello there";
		        
		        int row = 0;
		        int col = 0; 

		        while (file.readLine() != null) {
		        	String s = file.readLine();
		        	String numbers[] = s.split(" ");
		        	
		        	//System.out.println(s);
		        	
		        	String new_s = "";
		        	
		        	for(int i = 0; i < s.length(); i++) {
		        		if(s.charAt(i) != ' ') {
		        			new_s += '0';
		        			new_s += s.charAt(i);
		        			new_s += " ";
		        		}
		        	}
		        
		        	System.out.println(new_s);
		        	System.out.println("");
		        }
		        
		        	
		    } catch (Exception e) {
		        System.out.println("Problem reading file.");
		    }
		}
	
}
