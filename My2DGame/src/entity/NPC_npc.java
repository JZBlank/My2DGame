package entity;

import java.util.Random;

import main.GamePanel;


public class NPC_npc extends Entity {
	public NPC_npc(GamePanel gp) {
		super(gp);
		
		direction = "left";
		speed = 1;
		getImage();
		setDialogue();
		
	}
	public void getImage(){
	        
		up1 = setup("/npc/cat_up");
        up2 = setup("/npc/cat_up2");
        up3 = setup("/npc/cat_up_standby");
        up4 = setup("/npc/cat_up_sit");
        up5 = setup("/npc/cat_up_sit2");
        
        down1 = setup("/npc/cat_down");
        down2 = setup("/npc/cat_down2");
        down3 = setup("/npc/cat_down_standby");
        //down4 = setup("/npc/cat_down_down");
        down5 = setup("/npc/cat_down_sit");
        down6 = setup("/npc/cat_down_sit2");
        
        left1 = setup("/npc/cat_left");
        left2 = setup("/npc/cat_left2");
        left3 = setup("/npc/cat_left_standby");
        left4 = setup("/npc/cat_left_sit");
        left5 = setup("/npc/cat_left_sit2");
        
        right1 = setup("/npc/cat_right");
        right2 = setup("/npc/cat_right2");
        right3 = setup("/npc/cat_right_standby");
        right4 = setup("/npc/cat_right_sit");
        right5 = setup("/npc/cat_right_sit2");
	}
	
	public void setDialogue() {
		dialogues[0][0] = "Oh, hello there! I haven't seen you \n around here.";
		dialogues[0][1] = "If you need me to show you around, \n let me know!";
		dialogues[0][2] = "I should go get some food...";
		
	}
	public void updateDialogue() {
		super.updateDialogue();
	}
	
	public void setAction() {
		
		actionLockCounter++;
		
		if(actionLockCounter == 180) { // every 3 seconds, a random move is chosen, can also choose standby mode
			Random random = new Random();
			int i = random.nextInt(100)+1; // picks a number from 1 to 100
			int j = random.nextInt(10)+ 1; // picks a number from 1 to 10 /
			
			if(j > 5) {
				standBy = false;
			}
			else {
				standBy = true;
			}
			
			if(i <= 25) {
				direction = "up";
			}
			if(i > 25 && i <= 50) {
				direction = "down";
			}
			if(i > 50 && i<= 75) {
				direction = "left";
			}
			if(i > 75 && i <= 100) {
				direction = "right";
			}
			
			actionLockCounter = 0;
		}
	}
	public void speak() {
		super.speak();
	}
}
