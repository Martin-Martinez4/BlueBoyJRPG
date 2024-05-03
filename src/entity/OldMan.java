package entity;

import main.GamePanel;

import java.util.Random;

public class OldMan extends  Entity{

    public OldMan(GamePanel gamePanel){
        super(gamePanel);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage(){


        up1 = setup("/NPCs/oldman_up_1.png");
        up2 = setup("/NPCs/oldman_up_2.png");
        down1 = setup("/NPCs/oldman_down_1.png");
        down2 = setup("/NPCs/oldman_down_2.png");
        left1 = setup("/NPCs/oldman_left_1.png");
        left2 = setup("/NPCs/oldman_left_2.png");
        right1 = setup("/NPCs/oldman_right_1.png");
        right2 = setup("/NPCs/oldman_right_2.png");
    }

    public void setAction(){
        changeDirectionCountdown++;

        if(changeDirectionCountdown >= changeDirectionThreshold){

            Random random = new Random();
            // Pick a number from 1 to 100
            int i = random.nextInt(100)+1;

            if(i <= 25){
                direction = "up";
            } else if (i <= 50) {
                direction = "down";

            } else if (i <= 75 ) {
                direction = "left";

            }
            else{
                direction = "right";
            }

            changeDirectionCountdown = 0;
        }
    }

    public void setDialogue(){
        super.setDialogue();
    }

    public void speak(){
        super.speak();
    }


}
