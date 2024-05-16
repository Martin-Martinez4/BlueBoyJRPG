package Battle;

import StateManager.State;
import entity.skills.physical.Attack;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ActionSelectState implements State {
    // Do not create new instances inside the game loop or else a new instance will be created 60 times per second
    GamePanel gamePanel;
    BattleManager battleManager;
    Graphics2D g2;
    Font arial_40, arial_80B;
    // From Font Folder
    Font jersey15;
    Font vT323;
    Font iBMPlexMono;

    // Add to the combatant class later
    enum options {
        Attack,
        Skills,
        Items,
        Pass,
        Escape
    }

    options currentOption = options.Attack;
    options[] optionsArray = options.values();

    String name = "action select";


    public ActionSelectState(BattleManager battleManager, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.battleManager = battleManager;
        this.arial_40 = new Font("Arial", Font.PLAIN, 40);
        this.arial_80B = new Font("Arial", Font.BOLD, 80);
//        this.keyImage = new Key(this.gamePanel).image;

        try{

            InputStream inputStream = getClass().getResourceAsStream("/fonts/Jersey15-Regular.ttf");
            jersey15 = Font.createFont(Font.TRUETYPE_FONT, inputStream);

            inputStream = getClass().getResourceAsStream("/fonts/VT323-Regular.ttf");
            vT323 = Font.createFont(Font.TRUETYPE_FONT, inputStream);

            inputStream = getClass().getResourceAsStream("/fonts/IBMPlexMono-Regular.ttf");
            iBMPlexMono = Font.createFont(Font.TRUETYPE_FONT, inputStream);

        }catch (FontFormatException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }



    public void draw(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        this.g2 = g2;

        // g2.setFont(arial_40);
        g2.setFont(vT323);
        g2.setColor(Color.WHITE);

        switch(gamePanel.gameState){

            case battleState:
                drawBattleScene();
                break;

        }

    }

    // Have to break this up into different functions
    public void drawBattleScene(){

        BattleUI.drawMainWindow(gamePanel, g2);

        BattleUI.drawSelectionMenu(gamePanel, g2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));
        BattleUI.drawSelectionOptions(currentOption, gamePanel, g2);

        BattleUI.drawTurnDisplay(battleManager.turnOrderManager.currentTeam, battleManager.turnOrderManager.normalTurns, battleManager.turnOrderManager.advantageTurns, gamePanel, g2);


        TurnOrderManager turnOrderManager = battleManager.turnOrderManager;

        BattleUI.drawPlayerTeam(
                turnOrderManager.playerTeam,
                turnOrderManager.currentTeam,
                turnOrderManager.currentIndex,
                gamePanel,
                g2
        );
        BattleUI.drawEnemyTeam(
                turnOrderManager.enemyTeam,
                turnOrderManager.currentTeam,
                turnOrderManager.currentIndex,
                gamePanel,
                g2
        );


    }

    public void handleInputs(KeyEvent e){
        int code = e.getKeyCode();

        TurnOrderManager turnOrderManager = battleManager.turnOrderManager;

        switch (code){
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:

                if(currentOption.ordinal() > 0){
                    currentOption = optionsArray[currentOption.ordinal()-1];
                }else{
                    currentOption = optionsArray[optionsArray.length-1];
                }
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                if(currentOption.ordinal() < optionsArray.length-1){
                    currentOption = optionsArray[currentOption.ordinal()+1];
                }else{
                    currentOption = optionsArray[0];
                }
                break;
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_RIGHT:
                switch (currentOption){
                    case Attack:
                        battleManager.pushState(new TargetSelectState(new String[] { "Attack", "Skills", "Items", "Pass", "Escape"}, currentOption.ordinal(), new Attack(), battleManager, gamePanel));
                        break;
                    case Skills:
                        battleManager.pushState(new SkillSelectState(battleManager, gamePanel, turnOrderManager.playerTeam.get(turnOrderManager.currentIndex).skills));
                        break;
                    case Pass:

                        turnOrderManager.handleAddAdvantageTurn();
                        turnOrderManager.handleEndTurn();
                        battleManager.pushState(new BattleDialogueState(
                                "Pass",
                                battleManager,
                                gamePanel,
                                this,
                                () -> {   // anon lambda expression
                                    battleManager.popAllExceptFirst();
                                    if(turnOrderManager.currentTeam == TurnOrderManager.team.enemy){
                                        System.out.println("added Enemy turn");

                                        new EnemyTurnState(battleManager, gamePanel);
//

                                    }
                                }
                        ));
                        break;
                    case Escape:
                            // For now all escape attempts work
                        gamePanel.gameState = GamePanel.gameStates.playState;
                        break;
                }

                // Check the option and act accordingly
                // Setup the state stack later
                // Attack and skills have subStates



        }
    }
}
