package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Fish;

public class UI {
	GamePanel gp;
	Font arial_40;
	BufferedImage fishImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
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
		if(gameFinished == true) {
			
			g2.setFont(arial_40);;
			g2.setColor(Color.white);
			
			String text;
			int textLength, x, y;
			
			text = "Congratulations!";
			
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.tileSize*2);
			g2.drawString(text,  x,  y);
			
			gp.gameThread = null;
		}
		else {
			g2.setFont(arial_40);
			g2.setColor(Color.white);
			g2.drawImage(fishImage, gp.tileSize/6, gp.tileSize/6, gp.tileSize + 10, gp.tileSize + 10, null);
			g2.drawString("x " + gp.player.fishCount, 70, 55);
			
			// TIME
			//playTime +=(double)1/60;
			//g2.drawString("Time: "+dFormat.format(playTime), gp.tileSize * 11, 65);
			
			// MESSAGE
			if(messageOn == true) {
				
				g2.setFont(g2.getFont().deriveFont(30F));
				g2.drawString(message, gp.tileSize/2, gp.tileSize *5);
				
				messageCounter++;
				
				if(messageCounter > 120) {
					messageCounter = 0;
					messageOn = false;
				}
			}
		}
	}
}
