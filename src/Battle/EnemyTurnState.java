package Battle;

import entity.combatants.Combatant;
import entity.skills.Skill;
import main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class EnemyTurnState implements BattleState {
    GamePanel gamePanel;
    BattleManager battleManager;

    String[] skills = {"test"};
    int currentSkill;

    int currentTarget = 0;

    public EnemyTurnState(BattleManager battleManager, GamePanel gamePanel){
        this.battleManager = battleManager;
        this.gamePanel = gamePanel;

        this.draw(battleManager.g2);

        this.enemyAttackRandomPlayer();
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
        // Create a different method to make it clear which team member is being targeted
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
        BattleUI.drawEnemyTeam(turnOrderManager.enemyTeam, turnOrderManager.currentTeam, turnOrderManager.currentIndex, gamePanel, g2);

        // Draw Skills
        BattleUI.drawSelectionSkills(skills, currentSkill, gamePanel, g2);
    }

    public void enemyAttackRandomPlayer(){
        // Make enemy pick a random enemy
        TurnOrderManager turnOrderManager = battleManager.turnOrderManager;

        ArrayList<Integer> alivePlayerIndices = new ArrayList<Integer>();

        for(int i = 0; i <  turnOrderManager.playerTeam.size(); i++){
            if(turnOrderManager.playerTeam.get(i).health > 0){
                alivePlayerIndices.add(i);
            }
        }
        int randomAlivePlayIndex;
        if(alivePlayerIndices.isEmpty()){
            // Game over
            // But for now set index to 0
            battleManager.pushState(new GameOver(battleManager, gamePanel));
            return;
        }
        else {
            randomAlivePlayIndex = alivePlayerIndices.get((new Random().nextInt(alivePlayerIndices.size())));
        }


        Combatant currentEnemy = turnOrderManager.enemyTeam.get(turnOrderManager.currentIndex);
        Combatant targetedPlayer =  turnOrderManager.playerTeam.get(randomAlivePlayIndex);
        int damage = currentEnemy.attackTarget(currentEnemy.skills[0], targetedPlayer);
        targetedPlayer.health -= damage;

        if(alivePlayerIndices.isEmpty()){
            // Game over
            // But for now set index to 0
            battleManager.pushState(new GameOver(battleManager, gamePanel));
            return;
        }

        TurnOrderManager.team curTeam = turnOrderManager.currentTeam;
        turnOrderManager.handleEndTurn();

        this.battleManager.pushState(new BattleDialogueState(
                String.format(curTeam + " %s attacks %s for %o damage", currentEnemy.name, targetedPlayer.name, damage),
                battleManager,
                gamePanel,
                this,
                () -> {
                    battleManager.popAllExceptFirst();
                    if(turnOrderManager.currentTeam == TurnOrderManager.team.enemy){
                        new EnemyTurnState(battleManager, gamePanel);
                    }

                }
        ));


    }

    @Override
    public void handleInputs(KeyEvent e) {

    }
}
