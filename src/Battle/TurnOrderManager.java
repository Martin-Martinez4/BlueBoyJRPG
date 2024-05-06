package Battle;

import entity.combatants.Combatant;
import entity.combatants.JackFrost;
import entity.combatants.Slime;

import java.util.ArrayList;
// Idea: battle managers gets all turn information from TurnOrderManager
public class TurnOrderManager {

    public enum team{
        player,
        enemy
    }

    team currentTeam = team.player;

    ArrayList<Combatant> playerTeam = new ArrayList<Combatant>();
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

    public TurnOrderManager(){
        this.enemyTeam.add(new Slime());
        this.enemyTeam.add(new Slime());
        this.enemyTeam.add(new Slime());

        this.playerTeam.add(new JackFrost());
        this.playerTeam.add(new JackFrost());
        this.playerTeam.add(new JackFrost());

        if(currentTeam == team.player){
            this.normalTurns = playerTeam.size();

        }
        else{
            this.normalTurns = enemyTeam.size();
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

        if(normalTurns > 0){
            normalTurns--;
        } else{
            advantageTurns--;
        }

        advantageTurnGivenOut = false;

        System.out.println(normalTurns);
        System.out.println(normalTurns <= 0 && advantageTurns <= 0);
        if(normalTurns <= 0 && advantageTurns <= 0){
            handleSwitchTeam();
        }

        currentIndex++;
        System.out.println("CurrentIndex: " + currentIndex);
        System.out.println(currentTeam.name() + " Normal Turns: " + normalTurns);
        if((currentTeam == team.player && currentIndex >= playerTeam.size()) || (currentTeam == team.enemy && currentIndex >= enemyTeam.size())){
            currentIndex = 0;
        }
    }
    void handleSwitchTeam(){
        if(currentTeam == team.player){
            currentTeam = team.enemy;
            advantageTurns = 0;
            normalTurns = enemyTeam.size();
        }else{
            currentTeam = team.player;
            advantageTurns = 0;
            normalTurns = playerTeam.size();
        }
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
