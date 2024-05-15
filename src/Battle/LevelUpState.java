package Battle;

import entity.combatants.Combatant;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.event.KeyEvent;

// Should probably be renamed
public class LevelUpState implements  BattleState{

    int strength;
    int defense;
    int magic;
    int magicDefense;
    int luck;

    int strengthAdded = 0;
    int defenseAdded = 0;
    int magicAdded = 0;
    int magicDefenseAdded = 0;
    int luckAdded = 0;

    GamePanel gamePanel;
    Combatant levelUpper;
    int statPointsToAllocate;
    int statPointsLeft;

    int statMax = 100;

    int cursorIndex = 0;

    LevelUpState(GamePanel gamePanel, Combatant levelUpper, int statPointsToAllocate){

        this.gamePanel = gamePanel;

        this.strength = levelUpper.strength;
        this.defense = levelUpper.defense;
        this.magic = levelUpper.magic;
        this.magicDefense = levelUpper.magicDefense;
        this.luck = levelUpper.luck;

        this.levelUpper = levelUpper;
        this.statPointsToAllocate = statPointsToAllocate;
        this.statPointsLeft = statPointsToAllocate;


    }

    @Override
    public void draw(Graphics2D g2) {
        // Draw another page that forces a user to allocate the available points
        // Will happen once per character that leveled up
        // might have them stack up during the results page phase
        // Make points accumulate and allocate once

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));


        BattleUI.drawMainWindow(gamePanel, g2);

        int windowX = gamePanel.tileSize*2;
        int windowY = (int)(gamePanel.tileSize*2.5);


        int windowHeight = (int)(gamePanel.screenHeight * .10);
        int windowWidth = gamePanel.screenWidth/2;

        // LevelUpper information
        g2.drawString(levelUpper.name, windowX, (int)(gamePanel.tileSize*1.5));
        g2.drawString("LV: "+levelUpper.level, windowX, (int)(gamePanel.tileSize*2));
        g2.drawString("HP: "+levelUpper.maxHealth + " MP: "+ levelUpper.maxMagicPower, windowX, (int)(gamePanel.tileSize*2.5));

        // Allocation screen should take up most of the screen
        int gapY = gamePanel.tileSize;
        int gapBetweenStatAndBar = (gamePanel.tileSize * 4)/3;
        g2.drawString("Stat points left to allocate: "+ statPointsLeft, windowX, windowHeight + windowY - gamePanel.tileSize/4);

        windowHeight = (int)(gamePanel.screenHeight * 0.05);

        String cursor = "> ";

        // draw container
        UtilityTool.drawSubWindow(windowX + gapBetweenStatAndBar, windowHeight + windowY + gapY, windowWidth, windowHeight, g2);
        // Attempt at a bar
        UtilityTool.drawSubWindow(windowX + gapBetweenStatAndBar, windowHeight + windowY + gapY, (int)(windowWidth* ((double) levelUpper.strength/statMax)), windowHeight, new Color(255, 0,0), new Color(255, 0,0, 0), g2);
        // allocated points bar
        UtilityTool.drawSubWindow(windowX + gapBetweenStatAndBar +  (int)(windowWidth * ((double) this.strength/statMax)), windowHeight + windowY + gapY, (int)(windowWidth* ((double) strengthAdded/statMax)), windowHeight, new Color(0, 255,0), new Color(0, 255,0, 0), g2);

        String text = "STR: ";
        int textHeight = (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        g2.drawString(text, windowX, (windowHeight/2 + textHeight/2 + windowHeight + windowY + gapY));

        if(cursorIndex == 0){

            g2.drawString(cursor, windowX - gamePanel.tileSize/2, (windowHeight/2 + textHeight/2 + windowHeight + windowY + gapY));
        }

        gapY += gamePanel.tileSize;
        windowY += windowHeight;

        UtilityTool.drawSubWindow(windowX + gapBetweenStatAndBar, windowHeight + windowY + gapY, windowWidth, windowHeight, g2);
        UtilityTool.drawSubWindow(windowX + gapBetweenStatAndBar, windowHeight + windowY + gapY, (int)(windowWidth* ((double) levelUpper.defense/statMax)), windowHeight, new Color(255, 0,0), new Color(255, 0,0, 50), g2);
        // allocated points bar
        UtilityTool.drawSubWindow(windowX + gapBetweenStatAndBar +  (int)(windowWidth * ((double) this.defense/statMax)), windowHeight + windowY + gapY, (int)(windowWidth* ((double) defenseAdded/statMax)), windowHeight, new Color(0, 255,0), new Color(0, 255,0, 0), g2);

        g2.drawString("DEF: ", windowX, (windowHeight/2 + textHeight/2 + windowHeight + windowY + gapY));
        if(cursorIndex == 1){

            g2.drawString(cursor, windowX - gamePanel.tileSize/2, (windowHeight/2 + textHeight/2 + windowHeight + windowY + gapY));
        }

        gapY += gamePanel.tileSize;
        windowY += windowHeight;

        UtilityTool.drawSubWindow(windowX + gapBetweenStatAndBar, windowHeight + windowY + gapY, windowWidth, windowHeight, g2);
        UtilityTool.drawSubWindow(windowX + gapBetweenStatAndBar, windowHeight + windowY + gapY, (int)(windowWidth* ((double) levelUpper.magic/statMax)), windowHeight, new Color(255, 0,0), new Color(255, 0,0, 50), g2);
        // allocated points bar
        UtilityTool.drawSubWindow(windowX + gapBetweenStatAndBar +  (int)(windowWidth * ((double) levelUpper.magic/statMax)), windowHeight + windowY + gapY, (int)(windowWidth* ((double) magicAdded/statMax)), windowHeight, new Color(0, 255,0), new Color(0, 255,0, 0), g2);

        g2.drawString("MAG: ", windowX, (windowHeight/2 + textHeight/2 + windowHeight + windowY + gapY));
        if(cursorIndex == 2){

            g2.drawString(cursor, windowX - gamePanel.tileSize/2, (windowHeight/2 + textHeight/2 + windowHeight + windowY + gapY));
        }

        gapY += gamePanel.tileSize;
        windowY += windowHeight;

        UtilityTool.drawSubWindow(windowX + gapBetweenStatAndBar, windowHeight + windowY + gapY, windowWidth, windowHeight, g2);
        UtilityTool.drawSubWindow(windowX + gapBetweenStatAndBar, windowHeight + windowY + gapY, (int)(windowWidth* ((double) levelUpper.magicDefense/statMax)), windowHeight, new Color(255, 0,0), new Color(255, 0,0, 50), g2);
        // allocated points bar
        UtilityTool.drawSubWindow(windowX + gapBetweenStatAndBar +  (int)(windowWidth * ((double) levelUpper.magicDefense/statMax)), windowHeight + windowY + gapY, (int)(windowWidth* ((double) magicDefenseAdded/statMax)), windowHeight, new Color(0, 255,0), new Color(0, 255,0, 0), g2);

        g2.drawString("MDEF: ", windowX, (windowHeight/2 + textHeight/2 + windowHeight + windowY + gapY));
        if(cursorIndex == 3){

            g2.drawString(cursor, windowX - gamePanel.tileSize/2, (windowHeight/2 + textHeight/2 + windowHeight + windowY + gapY));
        }

        gapY += gamePanel.tileSize;
        windowY += windowHeight;

        UtilityTool.drawSubWindow(windowX + gapBetweenStatAndBar, windowHeight + windowY + gapY, windowWidth, windowHeight, g2);
        UtilityTool.drawSubWindow(windowX + gapBetweenStatAndBar, windowHeight + windowY + gapY, (int)(windowWidth* ((double) levelUpper.luck/statMax)), windowHeight, new Color(255, 0,0), new Color(255, 0,0, 50), g2);
        // allocated points bar
        UtilityTool.drawSubWindow(windowX + gapBetweenStatAndBar +  (int)(windowWidth * ((double) levelUpper.luck/statMax)), windowHeight + windowY + gapY, (int)(windowWidth* ((double) luckAdded/statMax)), windowHeight, new Color(0, 255,0), new Color(0, 255,0, 0), g2);

        g2.drawString("LUCK: ", windowX, (windowHeight/2 + textHeight/2 + windowHeight + windowY + gapY));
        if(cursorIndex == 4){

            g2.drawString(cursor, windowX - gamePanel.tileSize/2, (windowHeight/2 + textHeight/2 + windowHeight + windowY + gapY));
        }


    }

    @Override
    public void handleInputs(KeyEvent e) {

    }
}
