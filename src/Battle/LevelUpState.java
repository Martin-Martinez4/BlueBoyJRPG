package Battle;

import StateManager.State;
import entity.combatants.Combatant;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.event.KeyEvent;

// Should probably be renamed
public class LevelUpState implements State {

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

    BattleManager battleManager;
    GamePanel gamePanel;
    Combatant levelUpper;
    int statPointsToAllocate;
    int statPointsLeftToAllocate;

    int statMax = 100;

    // Make it into an enum later
    int cursorIndex = 0;
    int maxCursorIndex = 4;

    // implement later
    private enum Stats {
        STR,
        DEF,
        MAG,
        MAGD,
        LUCK
    }

    Stats[] statsArray = Stats.values();


    LevelUpState(BattleManager battleManager, GamePanel gamePanel, Combatant levelUpper, int statPointsToAllocate){

        this.battleManager = battleManager;
        this.gamePanel = gamePanel;

        this.strength = levelUpper.strength;
        this.defense = levelUpper.defense;
        this.magic = levelUpper.magic;
        this.magicDefense = levelUpper.magicDefense;
        this.luck = levelUpper.luck;

        this.levelUpper = levelUpper;
        this.statPointsToAllocate = statPointsToAllocate;
        this.statPointsLeftToAllocate = statPointsToAllocate;


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
        g2.drawString("Stat points left to allocate: "+ statPointsLeftToAllocate, windowX, windowHeight + windowY - gamePanel.tileSize/4);

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
        int code = e.getKeyCode();

        System.out.println(cursorIndex);

        switch(code){
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                cursorIndex++;
                if(cursorIndex > maxCursorIndex){
                    cursorIndex = 0;
                }
                break;

            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                cursorIndex--;
                if(cursorIndex < 0){
                    cursorIndex = maxCursorIndex;
                }
                break;

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if(statPointsLeftToAllocate > 0){

                    switch(statsArray[cursorIndex]){
                        case STR -> this.strengthAdded++;
                        case DEF -> this.defenseAdded++;
                        case MAG -> this.magicAdded++;
                        case MAGD -> this.magicDefenseAdded++;
                        case LUCK -> this.luckAdded++;
                    }
                    this.statPointsLeftToAllocate--;
                }
                break;

            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:

                if(statPointsLeftToAllocate < statPointsToAllocate){

                    switch(statsArray[cursorIndex]){
                        case STR:
                            if(this.strengthAdded > 0){

                                this.strengthAdded--;
                                this.statPointsLeftToAllocate++;
                            }
                            break;
                        case DEF:
                            if(this.defenseAdded > 0){

                                this.defenseAdded--;
                                this.statPointsLeftToAllocate++;
                            }
                            break;
                        case MAG:
                            if(this.magicAdded > 0){

                                this.magicAdded--;
                                this.statPointsLeftToAllocate++;
                            }
                            break;
                        case MAGD:
                            if(this.magicDefenseAdded > 0){

                                this.magicDefenseAdded--;
                                this.statPointsLeftToAllocate++;
                            }
                            break;
                        case LUCK :
                            if(this.luckAdded > 0){

                                this.luckAdded--;
                                this.statPointsLeftToAllocate++;
                            }
                            break;
                    }
                }
                break;


            case KeyEvent.VK_ENTER:
                if(statPointsLeftToAllocate > 0){
                    battleManager.pushState(new BattleDialogueState("Use all points before continuing.", battleManager, gamePanel, this, true, ()->{
                        battleManager.popState();
                    }));

                }else{
                    levelUpper.addStatPoints(this.strengthAdded, this.defenseAdded, this.magicAdded, this.magicDefenseAdded, this.luckAdded);
                    battleManager.popState();
                }
        }

    }
}
