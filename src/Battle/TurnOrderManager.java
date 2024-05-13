package Battle;

import entity.combatants.Combatant;
import entity.combatants.JackFrost;
import entity.combatants.Slime;
import entity.skills.Skill;
import main.GamePanel;

import java.util.ArrayList;
// Idea: battle managers gets all turn information from TurnOrderManager
public class TurnOrderManager {

    public enum team{
        player,
        enemy
    }

    team currentTeam = team.player;

    ArrayList<Combatant> playerTeam;
    ArrayList<Combatant> enemyTeam = new ArrayList<Combatant>();

    // The Combatants cannot die on their attacking turn
    // Possible to implement later
    // Make a turn class later, in order to track
    // ArrayList<Integer> currentTurnStack = new ArrayList<Integer>();

    // was going to use a stack, but it can be a lot simpler
    int normalTurns;
    int advantageTurns = 0;

    int currentIndex = 0;

    // Only one advantageTurn can be given out per turn if there are still normal turns available
    boolean advantageTurnGivenOut = false;

    GamePanel gamePanel;
    BattleManager battleManager;

    public TurnOrderManager(GamePanel gamePanel, BattleManager battleManager){
        this.gamePanel = gamePanel;
        this.battleManager = battleManager;

        this.enemyTeam.add(new Slime());
        this.enemyTeam.add(new Slime());
        this.enemyTeam.add(new Slime());

        this.playerTeam = gamePanel.playerTeam;

        if(currentTeam == team.player){
            for (Combatant combatant : playerTeam) {

                if (combatant.health > 0) {

                    this.normalTurns++;
                }
            }

        }
        else{
            for (Combatant combatant : enemyTeam) {

                if (combatant.health > 0) {

                    this.normalTurns++;
                }
            }
        }
    }

    public ArrayList<Combatant> getPlayerTeam() {
        return playerTeam;
    }

    public ArrayList<Combatant> getEnemyTeam() {
        return enemyTeam;
    }

    public team getCurrentTeam() {
        return currentTeam;
    }

    public void setPlayerTeam(ArrayList<Combatant> playerTeam){
        this.playerTeam = playerTeam;

    }

    public void setEnemyTeam(ArrayList<Combatant> enemyTeam){
        this.enemyTeam = enemyTeam;

    }

    //
    void handleEndTurn(){
        if(checkIfTeamDied(playerTeam)){
            System.out.println("Game Over");
            // Push game over state
            battleManager.pushState(new GameOver(battleManager, gamePanel));
        }
        if(checkIfTeamDied(enemyTeam)){
            System.out.println("This Ran");
            battleManager.turnOrderManager = new TurnOrderManager(gamePanel, battleManager);
            battleManager.popAllExceptFirst();
            battleManager.g2.dispose();
            battleManager.printStack();
            gamePanel.gameState = GamePanel.gameStates.playState;
        }

        if(normalTurns > 0){
            normalTurns--;
        } else{
            advantageTurns--;
        }

        advantageTurnGivenOut = false;

        ArrayList<Combatant> cTeam;

        if(currentTeam == team.player){
            cTeam = playerTeam;
        }
        else{
            cTeam = enemyTeam;
        }


        // Check if the next would be dead and skip over
        int checkDeadCounter = 0;

        do {
            currentIndex++;
            if ((currentTeam == team.player && currentIndex >= playerTeam.size()) || (currentTeam == team.enemy && currentIndex >= enemyTeam.size())) {
                currentIndex = 0;
            }

            checkDeadCounter++;
        } while (((currentTeam == team.player && playerTeam.get(currentIndex).health <= 0) || (currentTeam == team.enemy && enemyTeam.get(currentIndex).health <= 0)) && checkDeadCounter <= cTeam.size());


        if(normalTurns <= 0 && advantageTurns <= 0){
            handleSwitchTeam();
        }

    }

    void handleSwitchTeam(){
        System.out.println("Current Team: " + currentTeam.name() + " Current Index: " +currentIndex);

        if(checkIfTeamDied(playerTeam)){
            battleManager.pushState(new GameOver(battleManager, gamePanel));
            return;
        }
        if(checkIfTeamDied(enemyTeam)){
            battleManager.turnOrderManager = new TurnOrderManager(gamePanel, battleManager);
            battleManager.popAllExceptFirst();
            battleManager.g2.dispose();
            gamePanel.gameState = GamePanel.gameStates.playState;
        }

        if(currentTeam == team.player){
            currentTeam = team.enemy;
            advantageTurns = 0;
            currentIndex = 0;

            for (Combatant combatant : enemyTeam) {

                if (combatant.health > 0) {

                    normalTurns += 1;
                }
            }

        }else{
            currentTeam = team.player;
            for (Combatant combatant : playerTeam) {

                if (combatant.health > 0) {

                    normalTurns += 1;
                }
            }

        }

        ArrayList<Combatant> cTeam;

        if(currentTeam == team.player){
           cTeam = playerTeam;
        }
        else{
            cTeam = enemyTeam;
        }

        int checkDeadCounter = 0;

        if((currentTeam == team.player && playerTeam.get(currentIndex).health <= 0) || (currentTeam == team.enemy && enemyTeam.get(currentIndex).health <= 0)){

            do {
                currentIndex++;
                if ((currentTeam == team.player && currentIndex >= playerTeam.size()) || (currentTeam == team.enemy && currentIndex >= enemyTeam.size())) {
                    currentIndex = 0;
                }

                checkDeadCounter++;
            } while (((currentTeam == team.player && playerTeam.get(currentIndex).health <= 0) || (currentTeam == team.enemy && enemyTeam.get(currentIndex).health <= 0)) && checkDeadCounter <= cTeam.size());
        }

        if(checkIfTeamDied(playerTeam)){
            battleManager.pushState(new GameOver(battleManager, gamePanel));
            System.out.println("Game Over");
        }

        System.out.println("Current Team: " + currentTeam.name() + " Current Index: " +currentIndex);



    }

    // may be better if the team was passed in
    boolean checkIfTeamDied( ArrayList<Combatant> cTeam){


        int deadCount = 0;
        for(int i = 0; i < cTeam.size(); i++){
            if(cTeam.get(i).health <= 0){
                deadCount++;
            }
        }

        return  deadCount >= cTeam.size();

    }


    public boolean getAdvantageTurn(Skill skill, Combatant target){
        Skill.element attackElement = skill.getElement();

        return switch (attackElement) {
            case Skill.element.fire -> target.resistances.getFire() < 1;
            case Skill.element.ice -> target.resistances.getIce() < 1;
            case Skill.element.force -> target.resistances.getForce() < 1;
            case Skill.element.lightning -> target.resistances.getLighting() < 1;
            case Skill.element.dark -> target.resistances.getDark() < 1;
            case Skill.element.light -> target.resistances.getLight() < 1;
            case Skill.element.physical -> target.resistances.getPhysical() < 1;
        };
    }
    public boolean giveTurnPenalty(Skill skill, Combatant target){
        Skill.element attackElement = skill.getElement();

        return switch (attackElement) {
            case Skill.element.fire -> target.resistances.getFire() > 1;
            case Skill.element.ice -> target.resistances.getIce() > 1;
            case Skill.element.force -> target.resistances.getForce() > 1;
            case Skill.element.lightning -> target.resistances.getLighting() > 1;
            case Skill.element.dark -> target.resistances.getDark() > 1;
            case Skill.element.light -> target.resistances.getLight() > 1;
            case Skill.element.physical -> target.resistances.getPhysical() > 1;
        };
    }

    void handleAddAdvantageTurn(){
        if(!advantageTurnGivenOut && normalTurns > 0){
            advantageTurns++;
            advantageTurnGivenOut = true;
        }
    }
    void handleTurnPenalty(int turnsToDeduct){
        // get rid of advantage turns first
        if(advantageTurns >= turnsToDeduct){
            advantageTurns -= turnsToDeduct;
        }else{

            turnsToDeduct -= advantageTurns;
            advantageTurns = 0;
            normalTurns -= turnsToDeduct;

            if(normalTurns <= 0){
                // Switch Team
                handleSwitchTeam();
            }
        }
    }

    int getCurrentTeamTurns(){
        return normalTurns + advantageTurns;
    }
}
