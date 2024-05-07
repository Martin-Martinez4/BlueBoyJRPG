package Battle;

import entity.combatants.Combatant;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.util.ArrayList;

public class BattleUI {
    // Holds Battle UI elements that are shared between many battle states.

    static void drawMainWindow(GamePanel gamePanel, Graphics2D g2){
        // window
        int width = gamePanel.screenWidth - (gamePanel.tileSize);
        int height = gamePanel.screenHeight - (gamePanel.tileSize);
        int x = gamePanel.screenWidth - width - gamePanel.tileSize/2;
        int y = gamePanel.screenHeight - height  - gamePanel.tileSize/2;
        UtilityTool.drawSubWindow(x, y, width, height, g2);
    }

    static void drawSelectionMenu(GamePanel gamePanel, Graphics2D g2){
        int width = gamePanel.screenWidth - (gamePanel.tileSize);
        int x = gamePanel.screenWidth - width - gamePanel.tileSize/2;

        int innnerWindowX = x + (width - ((int)(gamePanel.screenWidth * .85)))/2;
        int innerWindowY = (int)(gamePanel.screenHeight * .45);
                UtilityTool.drawSubWindow( innnerWindowX, innerWindowY, (int)(gamePanel.screenWidth * .12), (int)(gamePanel.screenHeight * .45), g2);

    }

    static void drawSelectionOptions(ActionSelectState.options currentOption, GamePanel gamePanel, Graphics2D g2){
        int width = gamePanel.screenWidth - (gamePanel.tileSize);
        int x = gamePanel.screenWidth - width - gamePanel.tileSize/2;
        int innnerWindowX = x + (width - ((int)(gamePanel.screenWidth * .85)))/2;
        int innerWindowY = (int)(gamePanel.screenHeight * .45);

        int stringX;

        // iterate over enums using for loop
        int i = 0;
        for (ActionSelectState.options option : ActionSelectState.options.values()) {

            if(currentOption == option){
                stringX = (int)(gamePanel.screenWidth * .12) - innnerWindowX/2 + gamePanel.tileSize/3;

            }else{

                stringX = (int)(gamePanel.screenWidth * .12) - innnerWindowX/2;
            }

            if(currentOption == option){
                g2.drawString(">", (int)(gamePanel.screenWidth * .12) - innnerWindowX/2, innerWindowY + gamePanel.tileSize * (i+1));
            }

            g2.drawString(String.valueOf(option), stringX, innerWindowY + gamePanel.tileSize * (i+1));
            i++;
        }
    }

    static  void drawSelectionSkills(String[] skills, int currentSkill, GamePanel gamePanel, Graphics2D g2){

        int width = gamePanel.screenWidth - (gamePanel.tileSize);
        int x = gamePanel.screenWidth - width - gamePanel.tileSize/2;
        int innnerWindowX = x + (width - ((int)(gamePanel.screenWidth * .85)))/2;
        int innerWindowY = (int)(gamePanel.screenHeight * .45);

        int stringX;

        for(int i = 0; i < skills.length && i < 5; i++){

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

    static void drawPlayerTeam(ArrayList<Combatant> playerTeam, TurnOrderManager.team currentTeam, int currentIndex, GamePanel gamePanel, Graphics2D g2){

        int width = gamePanel.screenWidth - (gamePanel.tileSize);
        int x = gamePanel.screenWidth - width - gamePanel.tileSize/2;
        int innnerWindowX = x + (width - ((int)(gamePanel.screenWidth * .85)))/2;

        int textX = (int)(gamePanel.screenWidth * .12) + innnerWindowX + gamePanel.tileSize/2;

        String name;
        int health;
        int magicPower;
        int gapBetweenText = (int)(gamePanel.tileSize *.60);

        for(int i = 0; i < playerTeam.size(); i++){
            if(playerTeam.get(i) != null){

                name = playerTeam.get(i).name;
                health = playerTeam.get(i).health;
                magicPower = playerTeam.get(i).magicPower;

                if(currentTeam == TurnOrderManager.team.player &&  currentIndex == i){

                    UtilityTool.drawSubWindow(textX, (int)(gamePanel.screenHeight * .70), (gamePanel.tileSize*3), (gamePanel.tileSize*2), new Color(0, 0, 0),new Color(50, 50, 150), g2);
                }
                else{

                    UtilityTool.drawSubWindow(textX, (int)(gamePanel.screenHeight * .70), (gamePanel.tileSize*3), (gamePanel.tileSize*2), g2);
                }
                g2.drawString(name, (int)(textX+gamePanel.tileSize*.15), (int)(gamePanel.screenHeight * .70) + gapBetweenText);

                g2.drawString("HP: "+ health, (int)(textX+gamePanel.tileSize*.15), (int)(gamePanel.screenHeight * .70) + gapBetweenText*2);

                g2.drawString("MP: "+ magicPower, (int)(textX+gamePanel.tileSize*.15), (int)(gamePanel.screenHeight * .70) + gapBetweenText*3);

                textX += (int)(gamePanel.tileSize * 3.5);

            }
        }
    }

    static void drawEnemyTeam(ArrayList<Combatant> enemyTeam, TurnOrderManager.team currentTeam, int currentIndex, GamePanel gamePanel, Graphics2D g2){
        int width = gamePanel.screenWidth - (gamePanel.tileSize);
        int x = gamePanel.screenWidth - width - gamePanel.tileSize/2;
        int innnerWindowX = x + (width - ((int)(gamePanel.screenWidth * .85)))/2;

        int textX = (int)(gamePanel.screenWidth * .12) + innnerWindowX + gamePanel.tileSize/2;

        String name;
        int health;
        int magicPower;
        int gapBetweenText = (int)(gamePanel.tileSize *.60);

        for(int i = 0; i < enemyTeam.size(); i++){
            if(enemyTeam.get(i) != null){
                name = enemyTeam.get(i).name;
                health = enemyTeam.get(i).health;
                magicPower = enemyTeam.get(i).magicPower;

                if(currentTeam == TurnOrderManager.team.enemy &&  currentIndex == i){

                    UtilityTool.drawSubWindow(textX, (int)(gamePanel.screenHeight * .10), (gamePanel.tileSize*3), (gamePanel.tileSize*2), new Color(50, 10, 10),new Color(75, 60, 60), g2);
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
    }

    static  void drawTargetSelect(ArrayList<Combatant> enemyTeam, TurnOrderManager.team currentTeam, int currentIndex, GamePanel gamePanel, Graphics2D g2){
        int width = gamePanel.screenWidth - (gamePanel.tileSize);
        int x = gamePanel.screenWidth - width - gamePanel.tileSize/2;
        int innnerWindowX = x + (width - ((int)(gamePanel.screenWidth * .85)))/2;

        int textX = (int)(gamePanel.screenWidth * .12) + innnerWindowX + gamePanel.tileSize/2;

        String name;
        int health;
        int magicPower;
        int gapBetweenText = (int)(gamePanel.tileSize *.60);

        for(int i = 0; i < enemyTeam.size(); i++){
            if(enemyTeam.get(i) != null){
                name = enemyTeam.get(i).name;
                health = enemyTeam.get(i).health;
                magicPower = enemyTeam.get(i).magicPower;


                if(currentIndex == i){

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
    }

    static void drawTurnDisplay(GamePanel gamePanel, Graphics2D g2){
        int innerWindowY = (int)(gamePanel.screenHeight * .45);
        UtilityTool.drawSubWindow((int)(gamePanel.screenWidth * .85) - gamePanel.tileSize/2, innerWindowY, (int)(gamePanel.screenWidth * .12), (int)(gamePanel.screenHeight * .45), g2);

    }


}
