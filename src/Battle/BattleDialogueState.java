package Battle;

import entity.combatants.Combatant;
import entity.skills.Skill;
import main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;
import java.util.function.Function;

import static main.UtilityTool.getXforCenteredText;

public class BattleDialogueState implements  BattleState{

    BattleState previousBattleState;
    BattleManager battleManager;
    GamePanel gamePanel;
    String text;
    BattleState getPreviousBattleState;
    Runnable nextStep;

    // The runnable is here because the same behavior may not be needed for all battle dialogue
    BattleDialogueState(String text, BattleManager battleManager, GamePanel gamePanel, BattleState previousBattleState, Runnable nextStep){
        this.text = text;
        this.battleManager = battleManager;
        this.gamePanel = gamePanel;
        this.previousBattleState = previousBattleState;
        this.nextStep = nextStep;
    }
    @Override
    public void draw(Graphics2D g2) {

        this.previousBattleState.draw(g2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));

        // Darw test in middle
        int stringX = getXforCenteredText(text, gamePanel, g2);

        g2.drawString(text, stringX, gamePanel.screenHeight/2);

    }

    @Override
    public void handleInputs(KeyEvent e) {
        TurnOrderManager turnOrderManager = battleManager.turnOrderManager;
        int code = e.getKeyCode();

        switch (code){


            case KeyEvent.VK_ENTER:
                nextStep.run();
                break;
            case KeyEvent.VK_BACK_SPACE:
                battleManager.popAllExceptFirst();
                break;

        }
    }
}
