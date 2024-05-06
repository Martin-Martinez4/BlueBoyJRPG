package Battle;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

public class ActionSelectState implements BattleState{
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
        Guard,
        Pass,
        Escape
    }

    options currentOption = options.Attack;
    options[] optionsArray = options.values();


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
        // window
        int width = gamePanel.screenWidth - (gamePanel.tileSize);
        int height = gamePanel.screenHeight - (gamePanel.tileSize);
        int x = gamePanel.screenWidth - width - gamePanel.tileSize/2;
        int y = gamePanel.screenHeight - height  - gamePanel.tileSize/2;
        UtilityTool.drawSubWindow(x, y, width, height, g2);

//        String text = "Fight";
        int innnerWindowX = x + (width - ((int)(gamePanel.screenWidth * .85)))/2;
        int innerWindowY = (int)(gamePanel.screenHeight * .45);
        UtilityTool.drawSubWindow( innnerWindowX, innerWindowY, (int)(gamePanel.screenWidth * .12), (int)(gamePanel.screenHeight * .45), g2);
        UtilityTool.drawSubWindow( (int)(gamePanel.screenWidth * .85) - gamePanel.tileSize/2, innerWindowY, (int)(gamePanel.screenWidth * .12), (int)(gamePanel.screenHeight * .45), g2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));
        int stringX;


        // iterate over enums using for loop
        int i = 0;
        for (options option : options.values()) {

            if(currentOption == option){
                stringX = (int)(gamePanel.screenWidth * .12) - innnerWindowX/2 + gamePanel.tileSize/3;

            }else{

                stringX = (int)(gamePanel.screenWidth * .12) - innnerWindowX/2;
            }

            if(currentOption == option){
                g2.drawString(">", (int)(gamePanel.screenWidth * .12) - innnerWindowX/2, innerWindowY + gamePanel.tileSize * (i+1));
            }

            g2.drawString(String.valueOf(option), stringX, innerWindowY + gamePanel.tileSize * (i+1));
            i++;
        }

        int textX = (int)(gamePanel.screenWidth * .12) + innnerWindowX + gamePanel.tileSize/2;

        String name;
        int health;
        int magicPower;
        int gapBetweenText = (int)(gamePanel.tileSize *.60);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));

        for(i = 0; i < this.battleManager.turnOrderManager.getPlayerTeam().size(); i++){
            if(this.battleManager.turnOrderManager.getPlayerTeam().get(i) != null){

                name = this.battleManager.turnOrderManager.getPlayerTeam().get(i).name;
                health = this.battleManager.turnOrderManager.getPlayerTeam().get(i).health;
                magicPower = this.battleManager.turnOrderManager.getPlayerTeam().get(i).magicPower;

                if(battleManager.turnOrderManager.currentTeam == TurnOrderManager.team.player &&  battleManager.turnOrderManager.currentIndex == i){

                    UtilityTool.drawSubWindow(textX, (int)(gamePanel.screenHeight * .70), (gamePanel.tileSize*3), (gamePanel.tileSize*2), new Color(0, 0, 0),new Color(50, 50, 150), g2);
                }
                else{

                    UtilityTool.drawSubWindow(textX, (int)(gamePanel.screenHeight * .70), (gamePanel.tileSize*3), (gamePanel.tileSize*2), g2);
                }
                g2.drawString(name, (int)(textX+gamePanel.tileSize*.15), (int)(gamePanel.screenHeight * .70) + gapBetweenText);

                g2.drawString("HP: "+ health, (int)(textX+gamePanel.tileSize*.15), (int)(gamePanel.screenHeight * .70) + gapBetweenText*2);

                g2.drawString("MP: "+ magicPower, (int)(textX+gamePanel.tileSize*.15), (int)(gamePanel.screenHeight * .70) + gapBetweenText*3);

                textX += (int)(gamePanel.tileSize * 3.5);

            }
        }

        textX = (int)(gamePanel.screenWidth * .12) + innnerWindowX + gamePanel.tileSize/2;

        for(i = 0; i < this.battleManager.turnOrderManager.getEnemyTeam().size(); i++){
            if(this.battleManager.turnOrderManager.getEnemyTeam().get(i) != null){
                name = this.battleManager.turnOrderManager.getEnemyTeam().get(i).name;
                health = this.battleManager.turnOrderManager.getEnemyTeam().get(i).health;
                magicPower = this.battleManager.turnOrderManager.getEnemyTeam().get(i).magicPower;

                if(battleManager.turnOrderManager.currentTeam == TurnOrderManager.team.enemy &&  battleManager.turnOrderManager.currentIndex == i){

                    UtilityTool.drawSubWindow(textX, (int)(gamePanel.screenHeight * .10), (gamePanel.tileSize*3), (gamePanel.tileSize*2), new Color(50, 10, 10),new Color(75, 60, 60), g2);
                }
                else{

                    UtilityTool.drawSubWindow(textX, (int)(gamePanel.screenHeight * .10), (gamePanel.tileSize*3), (gamePanel.tileSize*2), g2);
                }


                g2.drawString(name, (int)(textX+gamePanel.tileSize*.15),  (int)(gamePanel.screenHeight * .10)+ gapBetweenText);

                g2.drawString("HP: "+ health, (int)(textX+gamePanel.tileSize*.15),  (int)(gamePanel.screenHeight * .10) + gapBetweenText*2);

                g2.drawString("MP: "+ magicPower, (int)(textX+gamePanel.tileSize*.15),  (int)(gamePanel.screenHeight * .10) + gapBetweenText*3);

                textX += (int)(gamePanel.tileSize * 3.5);
            }
        }


    }

    public void handleInputs(KeyEvent e){
        int code = e.getKeyCode();

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
                    case options.Attack:
                        battleManager.pushState(new TargetSelectState(battleManager, gamePanel));
                        break;
                    case options.Skills:
                        battleManager.pushState(new SkillSelectState(battleManager, gamePanel));
                        break;
                    case options.Pass:
                        battleManager.turnOrderManager.handleAddAdvantageTurn();
                        battleManager.turnOrderManager.handleEndTurn();
                        break;
                    case options.Escape:
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
