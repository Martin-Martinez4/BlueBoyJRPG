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

        g2.drawString(exp+"", textXGap + windowX + windowWidth/2, windowY + windowHeight/2 + textHeight/2);

        // Money Summary
        text = "Money:";
        windowY += (int)(gamePanel.tileSize*.5);

        UtilityTool.drawSubWindow(windowX, windowHeight + windowY, windowWidth, windowHeight, g2);

        g2.drawString(text, textXGap + windowX, windowY + (int)(windowHeight*1.5) + textHeight/2);
        g2.drawString(money+"", textXGap + windowX + windowWidth/2, windowY + (int)(windowHeight*1.5) + textHeight/2);

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
        UtilityTool.drawSubWindow(gamePanel.screenWidth/2, (int)(gamePanel.tileSize*2.5), gamePanel.screenWidth/3, (int)(gamePanel.screenHeight * .2), g2);
        UtilityTool.drawSubWindow(gamePanel.screenWidth/2, (int)(gamePanel.tileSize*3)+ (int)(gamePanel.screenHeight * .2), gamePanel.screenWidth/3, (int)(gamePanel.screenHeight * .2), g2);
        UtilityTool.drawSubWindow(gamePanel.screenWidth/2, (int)(gamePanel.tileSize*3.5)+ (int)(gamePanel.screenHeight * .2)*2, gamePanel.screenWidth/3, (int)(gamePanel.screenHeight * .2), g2);

        ArrayList<Combatant> playerTean = battleManager.turnOrderManager.playerTeam;



    }

    @Override
    public void handleInputs(KeyEvent e) {

    }
}
