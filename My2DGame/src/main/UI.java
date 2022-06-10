package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.TextField;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import entity.Entity;
import entity.Player;
import object.OBJ_Fish;
import object.OBJ_healthBar;
import object.SuperObject;

public class UI {
	GamePanel gp;
	Graphics2D g2;
//	JTextField jT = new JTextField(30);
//	JLabel jL = new JLabel();
	
	Font purisaB; // font for chat boxes
	Font Montserrat;
	
	
	Font arial_40;
	
	public int commandNum = 0;
	public int titleScreenState = 0; 
	
	// CHARACTER IMAGES:
	// PLAYER IMAGE
	BufferedImage playerImage;
	boolean cropped = false;
	
	// NPC IMAGE
	BufferedImage npcImage;
	
	// OBJECT IMAGES:
	
	// FISH IMAGE
	BufferedImage fishImage;
	
	// HEALTH IMAGES
	BufferedImage healthImage, healthImage2, healthImage3, healthImage4, healthImage5, healthImage6, healthImage7,
				  healthImage8, healthImage9, healthImage10, healthImage11;

	// NAME
	public boolean nameBlank = true;
	
	public boolean enterName = false;
	
	// MESSAGES / DIALOGUE
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public boolean moreDialogue = false;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		InputStream is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
		InputStream is2 = getClass().getResourceAsStream("/font/Montserrat-VariableFont_wght.ttf");
		try {
			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
			Montserrat = Font.createFont(Font.TRUETYPE_FONT, is2);
			
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// FISH
		OBJ_Fish fish = new OBJ_Fish(gp);
		fishImage = fish.image1;
		
		// HEART
		SuperObject heart = new OBJ_healthBar(gp);
		healthImage = heart.image1;
		healthImage2 = heart.image2;
		healthImage3 = heart.image3;
		healthImage4 = heart.image4;
		healthImage5 = heart.image5;
		healthImage6 = heart.image6;
		healthImage7 = heart.image7;
		healthImage8 = heart.image8;
		healthImage9 = heart.image9;
		healthImage10 = heart.image10;
		healthImage11 = heart.image11;
		
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		// DISPLAY ITEMS AT UPPER LEFT HAND CORNER
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		this.g2 = g2;
		g2.setFont(purisaB);
	    g2.setFont(new Font("Purisa", Font.TRUETYPE_FONT, 25));
		g2.setColor(Color.white);
		
		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		
		// PLAY STATE 
		if(gp.gameState == gp.playState) {
			
			drawPlayerImage();
			drawPlayerLife();
			drawObjectImages();
			
		}
		
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPlayerImage();
			drawPlayerLife();
			drawPauseScreen();
		}
		
		
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawPlayerImage();
			drawObjectImages();
			drawPlayerLife();
			drawDialogueScreen();
			drawDialogue();
		}
		
		if(gp.gameState == gp.interactOBJState){
			drawPlayerImage();
			drawPlayerLife();
			drawObjectImages();
			drawOptionBox();
			drawOptions();
		}
	
	}
	public void drawOptionBox() {
		// DRAW TEXT BOX
		int x = gp.tileSize * 6 + 130;
		int y = gp.tileSize * 4 + 20;
		int width = gp.tileSize * 2 + 30;
		int height = gp.tileSize * 2 + 10;

		Color c = new Color(0,0,0,150); //fourth parameter is transparency of window
		g2.setColor(c);
		g2.fillRoundRect(x, y , width, height, 35,35);
	}
	
	public void drawOptions() {
		int x = gp.tileSize * 6 + 150;
		int y = gp.tileSize * 5;
		
		String[] a = gp.npc[gp.player.targetIndex].options;
		String[] b = gp.obj[gp.player.targetIndex].options;
		
		g2.setFont(new Font("Montserrat", Font.TRUETYPE_FONT, 15));
		g2.setColor(Color.white);
		
		if(gp.player.whoInteract == 1){ // show options for npc
			for(int j = 0; j < a.length; j++) {
				g2.drawString(a[j], x, y);
				if(commandNum == j) {
					g2.drawString(">",  x - 10, y);
				}
				y += 20;
			}
		}
		else if (gp.player.whoInteract == 2) { //show options for obj
			for(int j = 0; j < b.length; j++) {
				g2.drawString(b[j], x, y);
				if(commandNum == j) {
					g2.drawString(">",  x - 10, y);
				}
				y += 20;
			}
		}
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
	}
	
	public void drawObjectImages() {
		g2.drawImage(fishImage, gp.tileSize/2, gp.tileSize/2 + 40, gp.tileSize, gp.tileSize, null);
		g2.drawString("x " + gp.player.fishCount, gp.tileSize/2 + 50, gp.tileSize + 55);
	}
	
	public void drawPlayerImage() {
		
		if(cropped == false) {
			// PLAYER
			playerImage = gp.player.cropImage(gp.player.down5);
		}
		cropped = true;
		
		// transparent color
		Color c = new Color(0,0,0,160); //fourth parameter is transparency of window
		g2.setColor(c);

		// DRAW DESIGN OF HEALTHBAR
		int points[][] = {{gp.tileSize/2 - 5, gp.tileSize},{gp.tileSize * 5,gp.tileSize},
				{gp.tileSize * 5 + 15, 80},{gp.tileSize/2 - 5, 80}};
		
		GeneralPath design = new GeneralPath();
		design.moveTo(points[0][0],points[0][0]);
		
		for(int j = 0; j < points.length; j++) {
			design.lineTo(points[j][0], points[j][1]);
		}
		

		design.closePath();
		g2.fill(design);
		g2.setColor(Color.white);
		
		//DRAW PLAYER PIC
		g2.drawImage(playerImage, gp.tileSize/2 - 10, gp.tileSize - 20, gp.tileSize, gp.tileSize - 20, null);
		
		// DRAW PLAYER NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
		g2.drawString(gp.player.name, gp.tileSize/2 + 40, gp.tileSize + 8);
		
		
	}

	
	public void drawPlayerLife() {
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/2 + 30;
		int i = 0;
		
		// DRAW MAX HEALTH
		
		while(i < gp.player.maxHealth) {
			if(i+1 == gp.player.maxHealth) {
				g2.drawImage(healthImage3, x, y, gp.tileSize/2, gp.tileSize/2, null);
			}
			else{
				g2.drawImage(healthImage2, x, y, gp.tileSize/2, gp.tileSize/2, null);
			}
			x += 24;
			i++;
		}
		
		x = gp.tileSize/2;
		y = gp.tileSize/2 + 30;
		i = 0;
		
		// DRAW CURRENT HEALTH
		if(gp.player.currentHealth != 0) {
			while(i < gp.player.currentHealth) {
				if(i+1 == gp.player.maxHealth) {
					g2.drawImage(healthImage6, x, y, gp.tileSize/2, gp.tileSize/2, null);
				}
				else{
					g2.drawImage(healthImage5, x, y, gp.tileSize/2, gp.tileSize/2, null); // change to #7 if want lines
				}
				x += 24;
				i++;
			}
		}
		
		x = gp.tileSize/2;
		y = gp.tileSize/2 + 35;
		i = 0;
		
		// DRAW MAX HEALTH
		//g2.drawImage(healthImage, x, y, gp.tileSize/2, gp.tileSize/2, null);
				
		while(i < gp.player.maxHealth) {
			if(i+1 == gp.player.maxHealth) {
				g2.drawImage(healthImage3, x, y, gp.tileSize/2, gp.tileSize/2, null);
			}
			else{
				g2.drawImage(healthImage2, x, y, gp.tileSize/2, gp.tileSize/2, null);
			}
			x += 24;
			i++;
		}
		
		x = gp.tileSize/2;
		y = gp.tileSize/2 + 35;
		i = 0;
				
		
		
		// DEHYDRATION BAR
					
		while(i < gp.player.dehydrationBar) {
			if(i+1 == 9) {
				g2.drawImage(healthImage10, x, y, gp.tileSize/2, gp.tileSize/2, null);
			}
			else{
				g2.drawImage(healthImage9, x, y, gp.tileSize/2, gp.tileSize/2, null);
			}
			x += 24;
			i++;
		}
	}
	
	public void drawTitleScreen() {
		
		if(titleScreenState == 0) {
			
			// TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
			String text = "Cat Wars";
			int x = getXforCenteredText(text);
			int y = gp.tileSize * 3;
			
			// SHADOW
			g2.setColor(Color.gray);
			g2.drawString(text, x+5, y+5);		
			
			// MAIN COLOR
			g2.setColor(Color.white);
			g2.drawString(text,  x,  y);
			
			// CAT IMAGE
			x = gp.screenWidth/2 - (gp.tileSize*2)/2;
			y += gp.tileSize * 2;
			g2.drawImage(gp.player.down3, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
			
			// MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
			
			text = "SINGLE PLAYER";
			x = getXforCenteredText(text);
			y += gp.tileSize * 4;
			g2.drawString(text,  x, y);
			if(commandNum == 0) {
				g2.drawString(">", x - gp.tileSize, y);
			}
			
			text = "MULTIPLAYER";
			x = getXforCenteredText(text);
			y += 35;
			g2.drawString(text,  x, y);
			if(commandNum == 1) {
				g2.drawString(">", x - gp.tileSize, y);
			}
			
			text = "LOAD GAME";
			x = getXforCenteredText(text);
			y += 35;
			g2.drawString(text,  x, y);
			if(commandNum == 2) {
				g2.drawString(">", x - gp.tileSize, y);
			}
			
			text = "QUIT GAME";
			x = getXforCenteredText(text);
			y += 35;
			g2.drawString(text,  x, y);
			if(commandNum == 3) {
				g2.drawString(">", x - gp.tileSize, y);
			}
		}
		
		else if(titleScreenState == 1) {
			// SET NAME  (figure out how to save later on)
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(20F));
			
			String text = "Enter a name for your cat:";
			int x = getXforCenteredText(text);
			int y = gp.tileSize;
			g2.drawString(text,  x,  y);	
			
			if(gp.jtf.getText().isEmpty()) {
				g2.setColor(Color.red);
				text = "Please enter a name";
				x = getXforCenteredText(text);
				y = gp.tileSize - 30;
				g2.drawString(text,  x,  y);	
			}
			
		}
		
		else if(titleScreenState == 2) {
			// CLASS SELECTION SCREEN
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(20F));
			
			String text = "Select your class!";
			int x = getXforCenteredText(text);
			int y = gp.tileSize;
			g2.drawString(text,  x,  y);
		
			
			text = "Fighter";
			x = getXforCenteredText(text);
			y += 35;
			g2.drawString(text,  x, y);
			if(commandNum == 0) {
				g2.drawString(">",  x - gp.tileSize, y);
			}
			
			text = "Back";
			x = getXforCenteredText(text);
			y += 35;
			g2.drawString(text,  x, y);
			if(commandNum == 1) {
				g2.drawString(">",  x - gp.tileSize, y);
			}
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
		// CREATE WINDOW
		int x = gp.tileSize/2;
		int y = gp.tileSize * 8 - 20;
		int width = gp.tileSize + 70;
		int height = gp.tileSize - 20 + 40;
		

		// NPC IMAGE
		npcImage = gp.npc[gp.player.targetIndex].cropImage(gp.npc[gp.player.targetIndex].down5);
		g2.drawImage(npcImage, x, y,  width, height, null);
		
		
		drawSubWindow();
		
	}
	
	public void drawDialogue() {
		int x = 30;
		int y = gp.tileSize * 8 + 10;
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
		
		// DRAW NAME OF NPC
		g2.drawString(gp.npc[gp.player.targetIndex].name, x, y);
		
		x = gp.tileSize/2 * 2;
		y = gp.tileSize * 9;
		
		y += gp.tileSize;
		
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
		
		x += gp.screenWidth - (gp.tileSize * 3);
		y -= 10;
		
		if(moreDialogue == true) {
			g2.drawString(">", x, y);
		}
	}
	
	public void drawSubWindow() {
		
		// DRAW TEXT BOX
		int x = 0;
		int y = gp.tileSize * 9;
		int width = gp.screenWidth;
		int height = gp.tileSize * 3;

		Color c = new Color(0,0,0,150); //fourth parameter is transparency of window
		g2.setColor(c);
		g2.fillRoundRect(x, y , width, height, 35,35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+2, y+2, width-10, height-10, 35, 35);
	}
}
