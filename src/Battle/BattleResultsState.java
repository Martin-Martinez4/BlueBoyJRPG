package Battle;

import entity.combatants.Combatant;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class BattleResultsState implements  BattleState{

    GamePanel gamePanel;
    BattleManager battleManager;

    int exp;
    int money;
    String[] items;

    public BattleResultsState(BattleManager battleManager, GamePanel gamePanel, int exp, int money, String[] items){
        this.battleManager = battleManager;
        this.gamePanel = gamePanel;
        this.exp = exp;
        this.money = money;
        this.items = items;
    }
    @Override
    public void draw(Graphics2D g2) {
        // Shows how much gold and items the party got
        // Show how many xp points each party members got
        BattleUI.drawMainWindow(gamePanel, g2);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 45F));

        String text = "You won, here is what you got.  ";
        int textHeight = (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        int textXGap =  (int)(gamePanel.tileSize * .5);

        ArrayList<Combatant> enemyTeam = battleManager.turnOrderManager.enemyTeam;

        int totalEXP = 0;
        int totalMoney = 0;
        for (int i = 0; i < enemyTeam.size(); i++){
            totalEXP += enemyTeam.get(i).giveXP();
            totalMoney += enemyTeam.get(i).giveMoney();
        }


        UtilityTool.drawSubWindow(gamePanel.tileSize, gamePanel.tileSize, gamePanel.screenWidth - gamePanel.tileSize*2, gamePanel.tileSize, g2);
        g2.drawString("You won, here is what you got.  ", gamePanel.tileSize + textXGap, gamePanel.tileSize + gamePanel.tileSize/2 + textHeight/2);


        int windowX = gamePanel.tileSize*2;
        int windowY = (int)(gamePanel.tileSize*2.5);


        int windowHeight = (int)(gamePanel.screenHeight * .10);
        int windowWidth = gamePanel.screenWidth/4;

        // EXP Summary
        text = "EXP:";
        textHeight = (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();

        UtilityTool.drawSubWindow(windowX, windowY, windowWidth, windowHeight, g2);
        g2.drawString(text, textXGap + windowX, windowY + windowHeight/2 + textHeight/2);

        g2.drawString(totalEXP+"", textXGap + windowX + windowWidth/2, windowY + windowHeight/2 + textHeight/2);

        // Money Summary
        text = "Money:";
        windowY += (int)(gamePanel.tileSize*.5);

        UtilityTool.drawSubWindow(windowX, windowHeight + windowY, windowWidth, windowHeight, g2);

        g2.drawString(text, textXGap + windowX, windowY + (int)(windowHeight*1.5) + textHeight/2);
        g2.drawString(totalMoney+"", textXGap + windowX + windowWidth/2, windowY + (int)(windowHeight*1.5) + textHeight/2);

        // Items Summary
        text = "Items:";
        windowY += (int)(gamePanel.tileSize*.5);
        windowHeight = (int)((gamePanel.screenHeight * .20) + windowY);

        int textY = windowHeight/2 + textHeight/2 + windowY;

        UtilityTool.drawSubWindow(windowX, windowHeight, windowWidth, (int)(gamePanel.screenHeight * .4), g2);
        g2.drawString(text, textXGap + windowX, textY);

        g2.drawString(items[0], textXGap + windowX,textY + gamePanel.tileSize);
        g2.drawString("x2", textXGap + windowX + windowWidth/2,textY + gamePanel.tileSize);


        // Change to loop later
        // Player EXP and Next
        ArrayList<Combatant> playerTeam = battleManager.turnOrderManager.playerTeam;


        for (int i = 0; i < playerTeam.size(); i++){
            int screenY = (int)(gamePanel.tileSize*2.5 + (gamePanel.screenHeight * .2 *i) + gamePanel.tileSize*.5 * i);
            UtilityTool.drawSubWindow(gamePanel.screenWidth/2, (int)(gamePanel.tileSize*2.5 + (gamePanel.screenHeight * .2 *i) + gamePanel.tileSize*.5 * i), gamePanel.screenWidth/3, (int)(gamePanel.screenHeight * .2), g2);
            g2.drawString(battleManager.turnOrderManager.playerTeam.get(i).name, gamePanel.screenWidth/2 + textXGap, screenY + gamePanel.tileSize);

            g2.drawString("EXP: ", gamePanel.screenWidth/2 + textXGap, (int)(screenY + gamePanel.tileSize * 1.5));
            g2.drawString(totalEXP/playerTeam.size()+"", gamePanel.screenWidth/2 + textXGap + windowX, (int)(screenY + gamePanel.tileSize * 1.5));

            g2.drawString("Next: ", gamePanel.screenWidth/2 + textXGap, screenY + gamePanel.tileSize*2);
            g2.drawString(playerTeam.get(i).totalXPForNextLevel - playerTeam.get(i).xp +"", gamePanel.screenWidth/2 + textXGap + windowX, screenY + gamePanel.tileSize*2);
        }

        // do an animation where the exp is allocated a bit at a time.  Can also keep track of how many level ups occur

    }

    @Override
    public void handleInputs(KeyEvent e) {

    }
}
