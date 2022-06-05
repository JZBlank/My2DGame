package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Fish;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font arial_40;
	BufferedImage fishImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		OBJ_Fish fish = new OBJ_Fish(gp);
		fishImage = fish.image1;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		// Have item(s) on screen *top left corner* 
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		g2.drawImage(fishImage, gp.tileSize/6, gp.tileSize/6, gp.tileSize + 10, gp.tileSize + 10, null);
		g2.drawString("x " + gp.player.fishCount, 70, 55);
		
		
		this.g2 = g2;
			
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		
		// PLAY STATE 
		if(gp.gameState == gp.playState) {
			
			if(messageOn == true) { // display that fish is picked up
				
				g2.setFont(g2.getFont().deriveFont(30F));
				g2.drawString(message, gp.tileSize/2, gp.tileSize *5);
				
				messageCounter++;
				
				if(messageCounter > 120) {
					messageCounter = 0;
					messageOn = false;
				}
			}
			
		}
		
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		
		
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}
	
	}
	
	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80));
		String text = "Paused";
		
		int x = getXforCenteredText(text);
		
		int y = gp.screenHeight/2;
		
		g2.drawString(text,  x,  y);
	}
	
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text,  g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	public void drawDialogueScreen() {
		// WINDOW
		int x = gp.tileSize * 2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize * 4);
		int height = gp.tileSize * 4;
		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
		x += gp.tileSize;
		y += gp.tileSize;
		g2.drawString(currentDialogue, x, y);
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		Color c = new Color(0,0,0,160); //fourth parameter is transparency of window
		g2.setColor(c);
		g2.fillRoundRect(x, y , width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
}
