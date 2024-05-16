package Battle;

import StateManager.State;
import main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ResetState implements State {
    // Just runs the constructor and ends battle when enter is pressed.
    // It may be bad form but the project is due tomorrow :)

    BattleManager battleManager;
    GamePanel gamePanel;

    int countDown = 25;

    public ResetState(BattleManager battleManager, GamePanel gamePanel){
        this.battleManager = battleManager;
        this.gamePanel = gamePanel;
    }

    @Override
    public void draw(Graphics2D g2) {

        if(this.countDown > 0){

            this.countDown--;
        }else {
            battleManager.turnOrderManager = new TurnOrderManager(gamePanel, battleManager);
            battleManager.popAllExceptFirst();
            battleManager.g2.dispose();
            gamePanel.gameState = GamePanel.gameStates.playState;
        }

    }

    @Override
    public void handleInputs(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_ENTER){

            battleManager.turnOrderManager = new TurnOrderManager(gamePanel, battleManager);
            battleManager.popAllExceptFirst();
            battleManager.g2.dispose();
            gamePanel.gameState = GamePanel.gameStates.playState;
        }
    }
}
