package Battle;

import main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;

import static main.UtilityTool.getXforCenteredText;

public class GameOver implements BattleState{

    GamePanel gamePanel;
    BattleManager battleManager;

    // Create an enum of options
    enum GameOverOptions{
        NewGame("New Game"),
        Quit("Quit");

        private String name;

        GameOverOptions(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
    // Create an array to hold the options
    int currentIndex = 0;

    GameOverOptions[] gameOverOptionsArray = GameOverOptions.values();

    public GameOver(BattleManager battleManager, GamePanel gamePanel){
        this.battleManager = battleManager;
        this.gamePanel = gamePanel;
    }

    @Override
    public void draw(Graphics2D g2) {
        // Should just say Game Over
        // Boots to the title screen

        BattleUI.drawMainWindow(gamePanel, g2);

        String text = "Game Over";

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 45F));

        // Draw text in middle
        int stringX = getXforCenteredText(text, gamePanel, g2);
        g2.drawString(text, stringX, gamePanel.screenHeight/2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));

        GameOverOptions currentOption = gameOverOptionsArray[currentIndex];

        for (int i = 0; i < gameOverOptionsArray.length; i++) {
            GameOverOptions option = gameOverOptionsArray[i];

            text = option.getName();
            stringX = getXforCenteredText(text, gamePanel, g2);

            g2.drawString(text, stringX, gamePanel.screenHeight/2 + (gamePanel.tileSize * (i+1)));
            if(currentIndex == i){

                g2.drawString(">", stringX - gamePanel.tileSize/4, gamePanel.screenHeight/2 + (gamePanel.tileSize * (i+1)));

            }

        }


    }

    @Override
    public void handleInputs(KeyEvent e) {
        TurnOrderManager turnOrderManager = battleManager.turnOrderManager;
        int code = e.getKeyCode();

        switch (code){
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:

                currentIndex--;
                if(currentIndex < 0){
                    currentIndex = gameOverOptionsArray.length-1;
                }
                break;

            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                currentIndex++;
                if(currentIndex > gameOverOptionsArray.length-1){
                    currentIndex = 0;
                }
                break;
        }

    }
}
