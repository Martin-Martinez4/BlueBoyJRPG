package Battle;

import StateManager.State;
import StateManager.StateManager;
import entity.combatants.Combatant;
import entity.skills.Skill;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;
import java.util.function.Function;

import static main.UtilityTool.getXforCenteredText;

public class BattleDialogueState implements State {

    State previousBattleState;
    StateManager stateManger;
    GamePanel gamePanel;
    String text;
    State getPreviousBattleState;
    boolean drawSubWindow;
    Runnable nextStep;

    // The runnable is here because the same behavior may not be needed for all battle dialogue
    public BattleDialogueState(String text, GamePanel gamePanel, State previousBattleState, Runnable nextStep){
        this.text = text;
        this.gamePanel = gamePanel;
        this.previousBattleState = previousBattleState;
        this.drawSubWindow = false;
        this.nextStep = nextStep;
    }
    public BattleDialogueState(String text, GamePanel gamePanel, State previousBattleState, boolean drawSubWindow, Runnable nextStep){
        this.text = text;
        this.gamePanel = gamePanel;
        this.previousBattleState = previousBattleState;
        this.drawSubWindow = drawSubWindow;
        this.nextStep = nextStep;
    }
    @Override
    public void draw(Graphics2D g2) {

        this.previousBattleState.draw(g2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));

        // Darw test in middle
        int stringX = getXforCenteredText(text, gamePanel, g2);

        if(drawSubWindow){

            UtilityTool.drawSubWindow(stringX-gamePanel.tileSize, gamePanel.tileSize*2+gamePanel.screenHeight/4, gamePanel.screenWidth/2, gamePanel.screenHeight/4, g2);
        }

        g2.drawString(text, stringX, gamePanel.screenHeight/2);

    }

    @Override
    public void handleInputs(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_ENTER) {
            nextStep.run();
        }
    }
}
