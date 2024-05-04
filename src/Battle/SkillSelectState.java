package Battle;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SkillSelectState implements BattleState{
    GamePanel gamePanel;
    BattleManager battleManager;

    // This will be dynamic and will be an Array/List of skill objects
    String[] skills = {"fire", "water", "force", "thunder"};

    SkillSelectState(BattleManager battleManager, GamePanel gamePanel){
        this.battleManager = battleManager;
        this.gamePanel = gamePanel;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        // window
        int width = gamePanel.screenWidth - (gamePanel.tileSize);
        int height = gamePanel.screenHeight - (gamePanel.tileSize);
        int x = gamePanel.screenWidth - width - gamePanel.tileSize/2;
        int y = gamePanel.screenHeight - height  - gamePanel.tileSize/2;
        UtilityTool.drawSubWindow(x, y, width, height, g2);

        int innnerWindowX = x + (width - ((int)(gamePanel.screenWidth * .85)))/2;
        int innerWindowY = (int)(gamePanel.screenHeight * .45) + (y/16);

        // parent margin + (parentWidth - (childWidth/2)) to center the window
        for(int i = 0; i < skills.length; i++){
            UtilityTool.drawSubWindow(innnerWindowX, innerWindowY + (i*gamePanel.tileSize+2), (int)(gamePanel.screenWidth * .20), (int)(gamePanel.screenHeight * .05), g2);
        }

//        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));


        // iterate over player skills later
    }

    @Override
    public void handleInputs(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code){
            case KeyEvent.VK_BACK_SPACE:
                battleManager.battleStates.remove(battleManager.battleStates.size()-1);

        }

    }
}