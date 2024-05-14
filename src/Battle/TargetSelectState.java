package Battle;

import entity.combatants.Combatant;
import entity.skills.Skill;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class TargetSelectState implements BattleState{
    GamePanel gamePanel;
    BattleManager battleManager;

    Skill[] skills;
    String[] actions;
    int currentSkill;

    int currentTarget = 0;

    public TargetSelectState(Skill[] skills, int currentSkill, BattleManager battleManager, GamePanel gamePanel){
        this.skills = skills;
        this.currentSkill = currentSkill;
        this.battleManager = battleManager;
        this.gamePanel = gamePanel;
    }
    public TargetSelectState(String[] actions, int currentSkill, BattleManager battleManager, GamePanel gamePanel){
        this.actions = actions;
        this.currentSkill = currentSkill;
        this.battleManager = battleManager;
        this.gamePanel = gamePanel;
    }

    @Override
    public void draw(Graphics2D g2) {
        // window
        BattleUI.drawMainWindow(gamePanel, g2);

        BattleUI.drawSelectionMenu(gamePanel, g2);

        BattleUI.drawTurnDisplay(battleManager.turnOrderManager.currentTeam, battleManager.turnOrderManager.normalTurns, battleManager.turnOrderManager.advantageTurns, gamePanel, g2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));

        TurnOrderManager turnOrderManager = battleManager.turnOrderManager;

        // Draw player Team
        BattleUI.drawPlayerTeam(turnOrderManager.playerTeam, turnOrderManager.currentTeam, turnOrderManager.currentIndex, gamePanel, g2);

        if(turnOrderManager.enemyTeam.get(currentTarget).health <= 0){

            int checkDeadCounter = 0;

            do {
                currentTarget++;
                if(currentTarget > turnOrderManager.enemyTeam.size()-1){
                    currentTarget = 0;
                }

                checkDeadCounter++;
            } while ((turnOrderManager.enemyTeam.get(currentTarget).health <= 0) && checkDeadCounter <= turnOrderManager.enemyTeam.size());

        }

        // Draw Target Selection
        BattleUI.drawTargetSelect(turnOrderManager.enemyTeam, turnOrderManager.currentTeam, currentTarget, gamePanel, g2);
        if(turnOrderManager.currentTeam == TurnOrderManager.team.enemy){
            BattleUI.drawEnemyTeam(turnOrderManager.enemyTeam, turnOrderManager.currentTeam, turnOrderManager.currentIndex, gamePanel, battleManager.g2);

        }

        // Draw Skills
        if(this.actions == null){

            BattleUI.drawSelectionSkills(skills, currentSkill, gamePanel, g2);
        } else if (this.skills == null) {
            BattleUI.drawSelectionSkills(actions, currentSkill, gamePanel, g2);


        }
    }

    @Override
    public void handleInputs(KeyEvent e) {
        TurnOrderManager turnOrderManager = battleManager.turnOrderManager;
        int code = e.getKeyCode();

        int checkDeadCounter = 0;

        switch (code){

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
//                currentTarget++;
//                if(currentTarget > turnOrderManager.enemyTeam.size()-1){
//                    currentTarget = 0;
//                }

                do {
                    currentTarget++;
                    if(currentTarget > turnOrderManager.enemyTeam.size()-1){
                        currentTarget = 0;
                    }

                    checkDeadCounter++;
                } while ((turnOrderManager.enemyTeam.get(currentTarget).health <= 0) && checkDeadCounter <= turnOrderManager.enemyTeam.size());

                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                // Left here as a reminder
              //  currentTarget--;
              // if(currentTarget < 0){
               //     currentTarget = turnOrderManager.enemyTeam.size()-1;
                //}

                do {
                    currentTarget--;
                    if(currentTarget < 0){
                        currentTarget = turnOrderManager.enemyTeam.size()-1;
                    }

                    checkDeadCounter++;
                } while ((turnOrderManager.enemyTeam.get(currentTarget).health <= 0) && checkDeadCounter <= turnOrderManager.enemyTeam.size());

                break;
            case KeyEvent.VK_ENTER:
                // Damage time?
                Combatant currentPlayer = turnOrderManager.playerTeam.get(turnOrderManager.currentIndex);
                Combatant targetedEnemy =  turnOrderManager.enemyTeam.get(currentTarget);
                int damage = currentPlayer.attackTarget(currentPlayer.skills[currentSkill], turnOrderManager.enemyTeam.get(currentTarget));
                turnOrderManager.enemyTeam.get(currentTarget).health -= damage;

                if(turnOrderManager.getAdvantageTurn(currentPlayer.skills[currentSkill], turnOrderManager.enemyTeam.get(currentTarget))){
                    turnOrderManager.handleAddAdvantageTurn();
                }else if(turnOrderManager.giveTurnPenalty(currentPlayer.skills[currentSkill], turnOrderManager.enemyTeam.get(currentTarget))){
                    turnOrderManager.handleTurnPenalty(1);
                }

                TurnOrderManager.team curTeam = turnOrderManager.currentTeam;
                turnOrderManager.handleEndTurn();

                if(turnOrderManager.checkIfTeamDied(turnOrderManager.enemyTeam)){
                    return;
                }

                battleManager.pushState(new BattleDialogueState(
                        String.format( curTeam + " %s attacks %s for %o damage", currentPlayer.name, targetedEnemy.name, damage),
                        battleManager,
                        gamePanel,
                        this,
                        () -> {   // anon lambda expression
                            battleManager.popAllExceptFirst();
                            if(turnOrderManager.currentTeam == TurnOrderManager.team.enemy){
                                System.out.println("added Enemy turn");

                                BattleUI.drawEnemyTeam(turnOrderManager.enemyTeam, turnOrderManager.currentTeam, turnOrderManager.currentIndex, gamePanel, battleManager.g2);

                                new EnemyTurnState(battleManager, gamePanel);
//
                            }
                        }
                ));


                break;
            case KeyEvent.VK_BACK_SPACE:

                battleManager.popState();
                break;

        }
    }
}
