package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.Entity;

public class KeyHandler implements KeyListener{

	GamePanel gp;
    public boolean meow, upPressed, downPressed, leftPressed, rightPressed, ePressed;
    public boolean changeDialogue;
    
    
    public KeyHandler(GamePanel gp) {
    	this.gp = gp;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    
        int code = e.getKeyCode(); // returns the integer keyCode associated with the key in this event
        
        // PLAY STATE
        if(gp.gameState == gp.playState) {
        	
        	if(code == KeyEvent.VK_M) {
            	meow = true;
            }
        	
        	if(code == KeyEvent.VK_P){
        		gp.gameState = gp.pauseState;
            }
        	if(code == KeyEvent.VK_E){
        		ePressed = true;
            }
        	
            if(code == KeyEvent.VK_W){
                upPressed = true;

            }
            if(code == KeyEvent.VK_A){
                leftPressed = true;
                
            }
            if(code == KeyEvent.VK_S){
                downPressed = true;
            
            }
            if(code == KeyEvent.VK_D){
                rightPressed = true;
                
            }
        }
        
        else if(gp.gameState == gp.pauseState) {
        	if(code == KeyEvent.VK_P){
        		gp.gameState = gp.playState;
            }
        }

        // DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState) {
        	if(code == KeyEvent.VK_E) {
        		if(gp.ui.moreDialogue != true) {
        			gp.gameState = gp.playState;
        		}
        		else {
        			changeDialogue = true;
        		}
        	}
        	else if(code == KeyEvent.VK_ENTER) {
        		gp.gameState = gp.playState;
        	}
        }
  }
 

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        
        
        if(code == KeyEvent.VK_M) {
        	meow = false;
        }
        
        if(code == KeyEvent.VK_E){
            changeDialogue = false;
        }

        if(code == KeyEvent.VK_W){
            upPressed = false;

        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
            
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
            
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
    
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}