package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// All characters extend this
public class Entity {

    public int worldX,worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    public boolean collisionOn = false;
    GamePanel gamePanel;
    public Rectangle solidArea;
    public boolean collision = true;
    public int changeDirectionCountdown = 0;
    public int changeDirectionThreshold = 100;
    String[] dialogues = new String[20];
    int dialogueIndex = 0;


    public Entity(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        this.solidArea = new Rectangle(0, 0, (int) (this.gamePanel.tileSize * .5), (int) (this.gamePanel.tileSize * .5));
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        // only render the objects that are seen
        if (screenX + gamePanel.tileSize > 0 &&
                screenY + gamePanel.tileSize > 0 &&
                screenX < gamePanel.screenWidth &&
                screenY < gamePanel.screenHeight) {
            switch(direction){
                case "up":
                    if(spriteNum == 1){
                        image = up1;

                    }else if(spriteNum == 2){
                        image = up2;
                    }
                    break;
                case "down":
                    if(spriteNum == 1){
                        image = down1;

                    }else if(spriteNum == 2){
                        image = down2;
                    }
                    break;
                case "left":
                    if(spriteNum == 1){
                        image = left1;

                    }else if(spriteNum == 2){
                        image = left2;
                    }
                    break;
                case "right":
                    if(spriteNum == 1){
                        image = right1;

                    }else if(spriteNum == 2){
                        image = right2;
                    }
                    break;
            }
            g2.drawImage(image, screenX, screenY, gamePanel.tileSize,
                    gamePanel.tileSize, null);
        }
    }

    public void setAction(){

    }

    public void speak(){
        gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }

        // Make the NPC face the player
        switch (gamePanel.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void setDialogue(){
        dialogues[0] = "Hello, here!";
        dialogues[1] = "I heard there is treasure on  \n this island.";
        dialogues[2] = "Good luck.  ";
    }


    public void  update(){
        setAction();

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkPlayer(this);

        // Check object collision
        if(!collisionOn){
            switch (direction){
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;

            }
        }

        spriteCounter++;
        if(spriteCounter > 15){
            spriteCounter = 0;
            if(spriteNum == 1){
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
        }
    }

    // Not that good
    public BufferedImage setup(String imagePath){
        BufferedImage scaledImage = null;

        try{
            scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
            scaledImage = UtilityTool.scaleImage(scaledImage, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scaledImage;
    }
}
