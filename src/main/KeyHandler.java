package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    boolean debug_drawTime = false;
    GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Play state
        if(gamePanel.gameState == GamePanel.gameStates.playState){
            switch (code){
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    upPressed = true;
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    downPressed = true;
                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    leftPressed = true;
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    rightPressed = true;
                    break;
                case KeyEvent.VK_T:
                    debug_drawTime = !debug_drawTime;
                    break;
                case KeyEvent.VK_ENTER:
                    enterPressed = true;
                    break;
                case KeyEvent.VK_P:
                    gamePanel.gameState = GamePanel.gameStates.pauseState;
                    break;
            }
        }

        // Pause State
        else if(gamePanel.gameState == GamePanel.gameStates.pauseState){
            if(code == KeyEvent.VK_P){
                gamePanel.gameState = GamePanel.gameStates.playState;

            }
        }
        // Battle State
        else if(gamePanel.gameState == GamePanel.gameStates.battleState){
           this.gamePanel.battleManager.handleInputs(e);
        }
        // Dialogue State
        else if(gamePanel.gameState == GamePanel.gameStates.dialogueState){
            if(code ==  KeyEvent.VK_ENTER){
                gamePanel.gameState = GamePanel.gameStates.playState;
            }
        }
        // Title State
        else if(gamePanel.gameState == GamePanel.gameStates.titleState){
            UI.TitleCommands[] commands = UI.TitleCommands.values();
            switch (code){
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:

                    if(gamePanel.ui.titleCommand.ordinal() > 0){
                        gamePanel.ui.titleCommand = commands[gamePanel.ui.titleCommand.ordinal()-1];
                    }else{
                        gamePanel.ui.titleCommand = commands[commands.length-1];
                    }
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    if(gamePanel.ui.titleCommand.ordinal() < commands.length-1){
                        gamePanel.ui.titleCommand = commands[gamePanel.ui.titleCommand.ordinal()+1];
                    }else{
                        gamePanel.ui.titleCommand = commands[0];
                    }
                    break;
                case KeyEvent.VK_T:
                    debug_drawTime = !debug_drawTime;
                    break;
                case KeyEvent.VK_ENTER:
                    switch (gamePanel.ui.titleCommand){
                        case LoadGame:
                            // Do other stuff later
                            gamePanel.gameState = GamePanel.gameStates.playState;
                            gamePanel.playMusic(0);
                            break;
                        case NewGame:
                            gamePanel.gameState = GamePanel.gameStates.playState;
                            gamePanel.playMusic(0);
                            break;
                        case Quit:
                            System.exit(0);
                            break;
                    }
                    break;
            }
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code){
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                upPressed = false;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;

        }
    }
}
