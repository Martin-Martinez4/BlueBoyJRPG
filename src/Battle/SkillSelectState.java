package Battle;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SkillSelectState implements BattleState{
    GamePanel gamePanel;
    BattleManager battleManager;

    // This will be dynamic and will be an Array/List of skill objects
    String[] skills = {"Fire", "Water", "Force", "Thunder", "Light", "Light", "Light"};
    int currentSkill = 0;

    SkillSelectState(BattleManager battleManager, GamePanel gamePanel){
        this.battleManager = battleManager;
        this.gamePanel = gamePanel;
    }

    @Override
    public void draw(Graphics2D g2) {

        BattleUI.drawMainWindow(gamePanel, g2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));
        BattleUI.drawSelectionMenu(gamePanel, g2);
        BattleUI.drawSelectionSkills(skills, currentSkill, gamePanel, g2);


        BattleUI.drawTurnDisplay(gamePanel, g2);

        TurnOrderManager turnOrderManager = battleManager.turnOrderManager;

        BattleUI.drawPlayerTeam(
                turnOrderManager.playerTeam,
                turnOrderManager.currentTeam,
                turnOrderManager.currentIndex,
                gamePanel,
                g2
        );
        BattleUI.drawEnemyTeam(
                turnOrderManager.enemyTeam,
                turnOrderManager.currentTeam,
                turnOrderManager.currentIndex,
                gamePanel,
                g2
        );



    }

    @Override
    public void handleInputs(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code){
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:

                if(currentSkill > 0){
                    currentSkill = currentSkill -1;
                }else{
                    currentSkill = skills.length-1;
                }
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                if(currentSkill < skills.length-1){
                    currentSkill = currentSkill +1;
                }else{
                    currentSkill = 0;
                }
                break;
            case KeyEvent.VK_ENTER, KeyEvent.VK_RIGHT:
                battleManager.pushState(new TargetSelectState(skills, currentSkill, battleManager, gamePanel));
                break;
            case KeyEvent.VK_BACK_SPACE:
            case KeyEvent.VK_LEFT:
                battleManager.popState();
                break;

        }

    }
}
