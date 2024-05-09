package Battle;

import entity.combatants.Combatant;
import entity.skills.Skill;
import main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class EnemyTurnState implements BattleState {
    GamePanel gamePanel;
    BattleManager battleManager;

    String[] skills = {"test"};
    int currentSkill;

    int currentTarget = 0;

    public EnemyTurnState(BattleManager battleManager, GamePanel gamePanel){
        this.battleManager = battleManager;
        this.gamePanel = gamePanel;

        System.out.println("Created");

        this.enemyAttackPlayer1();
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

    public void enemyAttackPlayer1(){
        TurnOrderManager turnOrderManager = battleManager.turnOrderManager;

        Combatant currentEnemy = turnOrderManager.enemyTeam.get(turnOrderManager.currentIndex);
        Combatant targetedPlayer =  turnOrderManager.playerTeam.get(currentTarget);
        int damage = currentEnemy.attackTarget(new Skill(Skill.type.magic, Skill.element.fire, 10), targetedPlayer);
        targetedPlayer.health -= damage;
        turnOrderManager.handleEndTurn();

        System.out.println("Should happen");
        this.battleManager.pushState(new BattleDialogueState(
                String.format(turnOrderManager.currentTeam + " %s attacks %s for %o damage", currentEnemy.name, targetedPlayer.name, damage),
                battleManager,
                gamePanel,
                this,
                () -> {
                    battleManager.popAllExceptFirst();
                    if(turnOrderManager.currentTeam == TurnOrderManager.team.enemy){
                        EnemyTurnState enemyTurnState = new EnemyTurnState(battleManager, gamePanel);
                    }

                }
        ));


    }

    @Override
    public void handleInputs(KeyEvent e) {

    }
}
