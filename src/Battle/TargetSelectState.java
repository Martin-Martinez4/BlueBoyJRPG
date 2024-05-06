package Battle;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TargetSelectState implements BattleState{
    GamePanel gamePanel;
    BattleManager battleManager;

    String[] skills = {"Fire", "Water", "Force", "Thunder", "Light", "Light", "Light"};
    int currentSkill = 0;

    int currentTarget = 0;

    public TargetSelectState(BattleManager battleManager, GamePanel gamePanel){
        this.battleManager = battleManager;
        this.gamePanel = gamePanel;
    }

    @Override
    public void draw(Graphics2D g2) {
        // window
        int width = gamePanel.screenWidth - (gamePanel.tileSize);
        int height = gamePanel.screenHeight - (gamePanel.tileSize);
        int x = gamePanel.screenWidth - width - gamePanel.tileSize/2;
        int y = gamePanel.screenHeight - height  - gamePanel.tileSize/2;
        UtilityTool.drawSubWindow(x, y, width, height, g2);

//        String text = "Fight";
        int innnerWindowX = x + (width - ((int)(gamePanel.screenWidth * .85)))/2;
        int innerWindowY = (int)(gamePanel.screenHeight * .45);
        UtilityTool.drawSubWindow( innnerWindowX, innerWindowY, (int)(gamePanel.screenWidth * .12), (int)(gamePanel.screenHeight * .45), g2);
        UtilityTool.drawSubWindow( (int)(gamePanel.screenWidth * .85) - gamePanel.tileSize/2, innerWindowY, (int)(gamePanel.screenWidth * .12), (int)(gamePanel.screenHeight * .45), g2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));
        int stringX;


        // iterate over enums using for loop
        int i = 0;

        int textX = (int)(gamePanel.screenWidth * .12) + innnerWindowX + gamePanel.tileSize/2;

        String name;
        int health;
        int magicPower;
        int gapBetweenText = (int)(gamePanel.tileSize *.60);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));

        for(i = 0; i < this.battleManager.turnOrderManager.getPlayerTeam().size(); i++){
            if(this.battleManager.turnOrderManager.getPlayerTeam().get(i) != null){

                name = this.battleManager.turnOrderManager.getPlayerTeam().get(i).name;
                health = this.battleManager.turnOrderManager.getPlayerTeam().get(i).health;
                magicPower = this.battleManager.turnOrderManager.getPlayerTeam().get(i).magicPower;

                UtilityTool.drawSubWindow(textX, (int)(gamePanel.screenHeight * .70), (gamePanel.tileSize*3), (gamePanel.tileSize*2), g2);
                g2.drawString(name, (int)(textX+gamePanel.tileSize*.15), (int)(gamePanel.screenHeight * .70) + gapBetweenText);

                g2.drawString("HP: "+ health, (int)(textX+gamePanel.tileSize*.15), (int)(gamePanel.screenHeight * .70) + gapBetweenText*2);

                g2.drawString("MP: "+ magicPower, (int)(textX+gamePanel.tileSize*.15), (int)(gamePanel.screenHeight * .70) + gapBetweenText*3);

                textX += (int)(gamePanel.tileSize * 3.5);

            }
        }

        textX = (int)(gamePanel.screenWidth * .12) + innnerWindowX + gamePanel.tileSize/2;
        for(i = 0; i < this.battleManager.turnOrderManager.getEnemyTeam().size(); i++){
            if(this.battleManager.turnOrderManager.getEnemyTeam().get(i) != null){
                name = this.battleManager.turnOrderManager.getEnemyTeam().get(i).name;
                health = this.battleManager.turnOrderManager.getEnemyTeam().get(i).health;
                magicPower = this.battleManager.turnOrderManager.getEnemyTeam().get(i).magicPower;


                if(currentTarget == i){

                    // Create colors on top
                    // Should not instantiate things inside the draw and/or update methods
                    UtilityTool.drawSubWindow(textX, (int)(gamePanel.screenHeight * .10), (gamePanel.tileSize*3), (gamePanel.tileSize*2), new Color(0, 0, 0, 100),new Color(128, 0, 0), g2);
                }
                else{

                    UtilityTool.drawSubWindow(textX, (int)(gamePanel.screenHeight * .10), (gamePanel.tileSize*3), (gamePanel.tileSize*2), g2);
                }
                g2.drawString(name, (int)(textX+gamePanel.tileSize*.15),  (int)(gamePanel.screenHeight * .10)+ gapBetweenText);

                g2.drawString("HP: "+ health, (int)(textX+gamePanel.tileSize*.15),  (int)(gamePanel.screenHeight * .10) + gapBetweenText*2);

                g2.drawString("MP: "+ magicPower, (int)(textX+gamePanel.tileSize*.15),  (int)(gamePanel.screenHeight * .10) + gapBetweenText*3);

                textX += (int)(gamePanel.tileSize * 3.5);
            }
        }



        // Draw Skills
        // parent margin + (parentWidth - (childWidth/2)) to center the window
        for(i = 0; i < skills.length && i < 5; i++){

            if(i == currentSkill){
                stringX = (int)(gamePanel.screenWidth * .12) - innnerWindowX/2 + gamePanel.tileSize/3;

            }else{

                stringX = (int)(gamePanel.screenWidth * .12) - innnerWindowX/2;
            }

            if(i == currentSkill ){
                g2.drawString(">", (int)(gamePanel.screenWidth * .12) - innnerWindowX/2, innerWindowY + gamePanel.tileSize * (i+1));
            }

            g2.drawString(skills[i], stringX, innerWindowY + gamePanel.tileSize * (i+1));

        }

    }



    @Override
    public void handleInputs(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code){

            case KeyEvent.VK_RIGHT:
                currentTarget++;
                if(currentTarget > battleManager.turnOrderManager.enemyTeam.size()-1){
                    currentTarget = 0;
                }
                        break;
            case KeyEvent.VK_LEFT:
                currentTarget--;
                if(currentTarget < 0){
                    currentTarget = battleManager.turnOrderManager.enemyTeam.size()-1;
                }
                        break;
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_BACK_SPACE:
                battleManager.popState();
                break;

        }
    }
}
