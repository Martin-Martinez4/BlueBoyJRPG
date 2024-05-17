package Battle;

import StateManager.State;
import entity.skills.Skill;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SkillSelectState implements State {
    GamePanel gamePanel;
    BattleManager battleManager;

    // This will be dynamic and will be an Array/List of skill objects
    Skill[] skills;
    int currentSkill = 0;

    SkillSelectState(BattleManager battleManager, GamePanel gamePanel, Skill[] skills){
        this.battleManager = battleManager;
        this.gamePanel = gamePanel;
        this.skills = skills;
    }

    @Override
    public void draw(Graphics2D g2) {

        BattleUI.drawMainWindow(gamePanel, g2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));
        BattleUI.drawSelectionMenu(gamePanel, g2);

        BattleUI.drawSelectionSkills(skills, currentSkill, gamePanel, g2);

        BattleUI.drawTurnDisplay(battleManager.turnOrderManager.currentTeam, battleManager.turnOrderManager.normalTurns, battleManager.turnOrderManager.advantageTurns, gamePanel, g2);

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
            case KeyEvent.VK_ENTER:
                if(battleManager.turnOrderManager.playerTeam.get(battleManager.turnOrderManager.currentIndex).magicPower >= skills[currentSkill].getMpCost()){

                    battleManager.pushState(new TargetSelectState(skills, currentSkill, skills[currentSkill], battleManager, gamePanel));
                }else{
                    battleManager.pushState(new BattleDialogueState("Not enough MP!", gamePanel, this, () -> { battleManager.popState();})) ;
                }
                break;
            case KeyEvent.VK_BACK_SPACE:
                battleManager.popState();
                break;

        }

    }
}
