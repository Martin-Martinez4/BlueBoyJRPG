package Battle;

import entity.combatants.Combatant;
import entity.skills.Skill;
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

        BattleUI.drawTurnDisplay(battleManager.turnOrderManager.currentTeam, battleManager.turnOrderManager.normalTurns, battleManager.turnOrderManager.advantageTurns, gamePanel, g2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));

        TurnOrderManager turnOrderManager = battleManager.turnOrderManager;

        // Draw player Team
        BattleUI.drawPlayerTeam(turnOrderManager.playerTeam, turnOrderManager.currentTeam, turnOrderManager.currentIndex, gamePanel, g2);

        if(turnOrderManager.enemyTeam.get(currentTarget).health <= 0){

            int checkDeadCounter = 0;

            do {
                currentTarget++;
                System.out.println("CurrentIndex: " + currentTarget);
                if(currentTarget > turnOrderManager.enemyTeam.size()-1){
                    currentTarget = 0;
                }

                checkDeadCounter++;
            } while ((turnOrderManager.enemyTeam.get(currentTarget).health <= 0) && checkDeadCounter <= turnOrderManager.enemyTeam.size());

        }

        // Draw Target Selection
        BattleUI.drawTargetSelect(turnOrderManager.enemyTeam, turnOrderManager.currentTeam, currentTarget, gamePanel, g2);

        // Draw Skills
        BattleUI.drawSelectionSkills(skills, currentSkill, gamePanel, g2);
    }

    @Override
    public void handleInputs(KeyEvent e) {
        TurnOrderManager turnOrderManager = battleManager.turnOrderManager;
        int code = e.getKeyCode();

        switch (code){

            case KeyEvent.VK_RIGHT:
//                currentTarget++;
//                if(currentTarget > turnOrderManager.enemyTeam.size()-1){
//                    currentTarget = 0;
//                }
                int checkDeadCounter = 0;

                do {
                    currentTarget++;
                    System.out.println("CurrentIndex: " + currentTarget);
                    if(currentTarget > turnOrderManager.enemyTeam.size()-1){
                        currentTarget = 0;
                    }

                    checkDeadCounter++;
                } while ((turnOrderManager.enemyTeam.get(currentTarget).health <= 0) && checkDeadCounter <= turnOrderManager.enemyTeam.size());

                break;
            case KeyEvent.VK_LEFT:
                // Left here as a reminder
              //  currentTarget--;
              // if(currentTarget < 0){
               //     currentTarget = turnOrderManager.enemyTeam.size()-1;
                //}

                checkDeadCounter = 0;

                do {
                    currentTarget--;
                    System.out.println("CurrentIndex: " + currentTarget);
                    if(currentTarget < 0){
                        currentTarget = turnOrderManager.enemyTeam.size()-1;
                    }

                    checkDeadCounter++;
                } while ((turnOrderManager.enemyTeam.get(currentTarget).health <= 0) && checkDeadCounter <= turnOrderManager.enemyTeam.size());

                break;
            case KeyEvent.VK_ENTER:
                // Damage time?
                Combatant currentPlayer = turnOrderManager.playerTeam.get(turnOrderManager.currentIndex);
                int damage = currentPlayer.attackTarget(new Skill(Skill.type.magic, Skill.element.fire, 10), turnOrderManager.enemyTeam.get(currentTarget));
                turnOrderManager.enemyTeam.get(currentTarget).health -= damage;
                turnOrderManager.handleEndTurn();

                battleManager.popState();
                battleManager.popState();
                break;
            case KeyEvent.VK_BACK_SPACE:

                battleManager.popState();
                break;

        }
    }
}
