package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


public class Player extends Entity{
    Random rand = new Random();
    KeyHandler keyHandler;

    final public int screenX;
    final public int screenY;

    int encounterRate = 50;
    final int defaultEncounterCoolDown = 100;
    int encounterCoolDown = defaultEncounterCoolDown;

    public int numberOfKeys = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler){
        super(gamePanel);

        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth/2 - (gamePanel.tileSize/2);
        screenY = gamePanel.screenHeight/2 - (gamePanel.tileSize/2);

        solidArea = new Rectangle(8, 16, (int) (gamePanel.tileSize * .65), (int) (gamePanel.tileSize * .65));
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        this.setDefaultValues();
        this.getPlayerImage();
    }

    public void setDefaultValues(){
        this.worldX = gamePanel.tileSize * 23;
        this.worldY = gamePanel.tileSize * 21;
        this.speed = gamePanel.scale + 1;
        this.direction = "down";
    }

    public void getPlayerImage(){

            up1 = setup("/player/boy_up_1.png");
            up2 = setup("/player/boy_up_2.png");
            down1 = setup("/player/boy_down_1.png");
            down2 = setup("/player/boy_down_2.png");
            left1 = setup("/player/boy_left_1.png");
            left2 = setup("/player/boy_left_2.png");
            right1 = setup("/player/boy_right_1.png");
            right2 = setup("/player/boy_right_2.png");

    }

    public void update(){

        if(keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed){
            if(this.keyHandler.upPressed){
                direction = "up";
            }
            else if(this.keyHandler.downPressed){
                direction = "down";
            }else if(this.keyHandler.leftPressed){
                direction = "left";
            }else if(this.keyHandler.rightPressed){
                direction = "right";
            }

            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);


            // Check NPC collision.
            int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
            interactWithNPC(npcIndex);

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
                this.encounterEnemy();
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


    }


    public void encounterEnemy(){
        if(encounterCoolDown <= 0){

            int encounter = rand.nextInt(100);

            if(encounter > encounterRate){
                // Change to battle State here.
                gamePanel.gameState = GamePanel.gameStates.battleState;
                System.out.println("Battle Would have Happened");
            }

            encounterCoolDown = defaultEncounterCoolDown;
        }
        else{
            encounterCoolDown--;
//            System.out.println(encounterCoolDown);
        }
    }

    public void interactWithNPC(int index){
        if(index != 999){
            if(gamePanel.keyHandler.enterPressed){
                gamePanel.gameState = GamePanel.gameStates.dialogueState;
                gamePanel.npc[index].speak();
                gamePanel.keyHandler.enterPressed = false;
            }
        }
    }

    public void draw(Graphics2D g2){
//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);
        BufferedImage image = null;

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
        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
