package Pause;

import StateManager.State;
import entity.combatants.Combatant;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Map;

public class ItemSelectState implements State {

    PauseMenuManager pauseMenuManager;
    GamePanel gamePanel;

    int cursorPosition = 0;
    int maxCursorPosition = 0;

    public ItemSelectState(PauseMenuManager pauseMenuManager, GamePanel gamePanel){
        this.pauseMenuManager = pauseMenuManager;
        this.gamePanel = gamePanel;

    }

    @Override
    public void draw(Graphics2D g2) {
        // Substates
        // Choose item first boolean check
        // if true allow player to be picked
        // If it is too much hassle break up into item select and target select states

        int width = gamePanel.screenWidth - (gamePanel.tileSize);
        int height = gamePanel.screenHeight - (gamePanel.tileSize);
        int x = gamePanel.screenWidth - width - gamePanel.tileSize/2;
        int y = gamePanel.screenHeight - height  - gamePanel.tileSize/2;
        UtilityTool.drawSubWindow(x, y, width, height, g2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));

        int statsX = gamePanel.tileSize;
        int statsY = gamePanel.tileSize*2;

        int statsWidth = (gamePanel.screenWidth/3);
        int statsHeight = (gamePanel.screenHeight/5);

        for(int i = 0; i < gamePanel.playerTeam.size(); i++){
            Combatant currentPlayer = gamePanel.playerTeam.get(i);

            UtilityTool.drawSubWindow(statsX,statsY , statsWidth, statsHeight, g2);

            int nameX = statsX + gamePanel.tileSize/2;
            int nameY = statsY + gamePanel.tileSize;

            int hpX = nameX;
            int hpY = nameY + gamePanel.tileSize/2;

            int mpX = hpX;
            int mpY = hpY + gamePanel.tileSize/2;
            g2.drawString(currentPlayer.name, nameX, nameY);
            g2.drawString("HP: "+currentPlayer.health  +"/"+ currentPlayer.maxHealth, hpX, hpY);
            g2.drawString("MP: "+currentPlayer.magicPower  +"/"+ currentPlayer.maxMagicPower, mpX, mpY);

            statsY += statsHeight + gamePanel.tileSize/2;
        }

        statsY = gamePanel.tileSize*2;

        int actionsX = width - statsWidth;
        UtilityTool.drawSubWindow(actionsX, statsY , statsWidth, (int)(height*.8), g2);

        String text = "Items";
        int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();


        g2.drawString("Items", actionsX - textLength/2 + statsWidth/2, statsY + gamePanel.tileSize);

        if(gamePanel.itemManager.getItems().isEmpty()){
            g2.drawString("Empty", actionsX + (int)(gamePanel.tileSize*.8), statsY + gamePanel.tileSize + gamePanel.tileSize);

        }else{

            g2.drawString("> ", actionsX + (gamePanel.tileSize/2), statsY + gamePanel.tileSize + gamePanel.tileSize);

            for (Map.Entry<String, Integer> entry : gamePanel.itemManager.getItems().entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();

                g2.drawString(key + " " + "x" + value, actionsX + (int)(gamePanel.tileSize), statsY + gamePanel.tileSize + gamePanel.tileSize);
                maxCursorPosition++;
                statsY += gamePanel.tileSize;
            }


        }

    }

    @Override
    public void handleInputs(KeyEvent e) {

        // if maxCursorPosition > 1 let the cursor move else do not

    }
}
