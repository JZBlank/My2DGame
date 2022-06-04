package entity;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;
    
    // describes image with accessible buffer image data (use this to store image files)
    public BufferedImage up1, up2, up3, up4, up5;
    public BufferedImage down1, down2, down3, down4, down5;
    public BufferedImage left1, left2, left3, left4, left5;
    public BufferedImage right1, right2, right3, right4, right5; 
    
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    
    
}
