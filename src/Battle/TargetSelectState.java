package Battle;

import entity.combatants.Combatant;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TargetSelectState implements BattleState{
    GamePanel gamePanel;
    BattleManager battleManager;

    String[] skills;
    int currentSkill;

    int currentTarget = 0;

    public TargetSelectState(String[] skills, int currentSkill, BattleManager battleManager, GamePanel gamePanel){
        this.skills = skills;
        this.currentSkill = currentSkill;
        this.battleManager = battleManager;
        this.gamePanel = gamePanel;
    }

    @Override
    public void draw(Graphics2D g2) {
        // window
        BattleUI.drawMainWindow(gamePanel, g2);

        BattleUI.drawSelectionMenu(gamePanel, g2);
        BattleUI.drawSelectionSkills(skills, currentSkill, gamePanel, g2);

        BattleUI.drawTurnDisplay(gamePanel, g2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));

        TurnOrderManager turnOrderManager = battleManager.turnOrderManager;

        // Draw player Team
        BattleUI.drawPlayerTeam(turnOrderManager.playerTeam, turnOrderManager.currentTeam, turnOrderManager.currentIndex, gamePanel, g2);

        // Draw Target Selection
        BattleUI.drawTargetSelect(turnOrderManager.enemyTeam, turnOrderManager.currentTeam, currentTarget, gamePanel, g2);

        // Draw Skills
        BattleUI.drawSelectionSkills(skills, currentSkill, gamePanel, g2);
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
                // Damage time?

                battleManager.popState();
                break;

        }
    }
}
