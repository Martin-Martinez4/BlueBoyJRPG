package Pause;

import Battle.BattleDialogueState;
import StateManager.State;
import entity.combatants.Combatant;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ItemSelectState implements State {

    PauseMenuManager pauseMenuManager;
    GamePanel gamePanel;

    int itemCursorPosition = 0;
    int maxItemCursorPosition = 0;
    String currentItemName;

    int playerCursorPosition = 0;

    boolean inTargetSelect = false;

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


        if(inTargetSelect){
            for (int i = 0; i < gamePanel.playerTeam.size(); i++) {
                Combatant currentPlayer = gamePanel.playerTeam.get(i);

                if(playerCursorPosition == i){

                    UtilityTool.drawSubWindow(statsX, statsY, statsWidth,  statsHeight, new Color(0, 0, 0), new Color(50, 50, 150), g2);
                }
                else{
                    UtilityTool.drawSubWindow(statsX, statsY, statsWidth, statsHeight, g2);

                }

                int nameX = statsX + gamePanel.tileSize / 2;
                int nameY = statsY + gamePanel.tileSize;

                int hpX = nameX;
                int hpY = nameY + gamePanel.tileSize / 2;

                int mpX = hpX;
                int mpY = hpY + gamePanel.tileSize / 2;
                g2.drawString(currentPlayer.name, nameX, nameY);
                g2.drawString("HP: " + currentPlayer.health + "/" + currentPlayer.maxHealth, hpX, hpY);
                g2.drawString("MP: " + currentPlayer.magicPower + "/" + currentPlayer.maxMagicPower, mpX, mpY);

                statsY += statsHeight + gamePanel.tileSize / 2;
            }
        }else{
            for (int i = 0; i < gamePanel.playerTeam.size(); i++) {
                Combatant currentPlayer = gamePanel.playerTeam.get(i);

                UtilityTool.drawSubWindow(statsX, statsY, statsWidth, statsHeight, g2);

                int nameX = statsX + gamePanel.tileSize / 2;
                int nameY = statsY + gamePanel.tileSize;

                int hpX = nameX;
                int hpY = nameY + gamePanel.tileSize / 2;

                int mpX = hpX;
                int mpY = hpY + gamePanel.tileSize / 2;
                g2.drawString(currentPlayer.name, nameX, nameY);
                g2.drawString("HP: " + currentPlayer.health + "/" + currentPlayer.maxHealth, hpX, hpY);
                g2.drawString("MP: " + currentPlayer.magicPower + "/" + currentPlayer.maxMagicPower, mpX, mpY);

                statsY += statsHeight + gamePanel.tileSize / 2;
            }
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

            HashMap<String, Integer> itemsInInventory = gamePanel.itemManager.getItems();
            maxItemCursorPosition = itemsInInventory.size() -1;
            if(itemCursorPosition > maxItemCursorPosition ){
                itemCursorPosition = maxItemCursorPosition;
            } else if (itemCursorPosition < 0) {
                itemCursorPosition = 0;
            }

            int currentItem = 0;
            for (String entry : itemsInInventory.keySet()) {

                g2.drawString(entry + " " + "x" + itemsInInventory.get(entry), actionsX + (int)(gamePanel.tileSize), statsY + gamePanel.tileSize + gamePanel.tileSize);

                if(currentItem == this.itemCursorPosition){

                    g2.drawString("> ", actionsX + (gamePanel.tileSize/2), statsY + gamePanel.tileSize + gamePanel.tileSize);
                    currentItemName = entry;
                }
                statsY += gamePanel.tileSize;

                currentItem++;
            }



        }

    }

    @Override
    public void handleInputs(KeyEvent e) {
        int code = e.getKeyCode();

        if(inTargetSelect){

            switch (code){
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    playerCursorPosition--;
                    if(playerCursorPosition < 0){
                        playerCursorPosition = gamePanel.playerTeam.size()-1;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    playerCursorPosition++;
                    if(playerCursorPosition >  gamePanel.playerTeam.size()-1){
                        playerCursorPosition = 0;
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    if(gamePanel.itemManager.useItem(currentItemName, gamePanel.playerTeam.get(playerCursorPosition))){
                        // check if there is still items
                        if(gamePanel.itemManager.getItemAmount(currentItemName) <= 0){
                            inTargetSelect = false;
                        }


                    }
                    else{
                        pauseMenuManager.pushState(new BattleDialogueState("Nothing Happened!", gamePanel, this, true, () -> {
                            pauseMenuManager.popState();
                            if(gamePanel.itemManager.getItemAmount(currentItemName) == 0){
                                inTargetSelect = false;
                            }
                        }));
                    }
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    inTargetSelect = false;
                    break;
            }

        }else{

            switch (code){
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    itemCursorPosition--;
                    if(itemCursorPosition < 0){
                        itemCursorPosition = maxItemCursorPosition;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    itemCursorPosition++;
                    if(itemCursorPosition > maxItemCursorPosition){
                        itemCursorPosition = 0;
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    if(gamePanel.itemManager.getItemAmount(currentItemName) > 0){
                        inTargetSelect = true;
                    }else{
                        if(gamePanel.itemManager.getItems().isEmpty()){
                            pauseMenuManager.pushState(new BattleDialogueState("You have no items", gamePanel, this, true,()->{
                                pauseMenuManager.popState();
                            }));
                        }
                    }
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    pauseMenuManager.popState();
                    break;

            }
        }
        // if maxCursorPosition > 1 let the cursor move else do not


    }
}
