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


    public void drawBattleScene(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        // window
        int width = gamePanel.screenWidth - (gamePanel.tileSize);
        int height = gamePanel.screenHeight - (gamePanel.tileSize);
        int x = gamePanel.screenWidth - width - gamePanel.tileSize/2;
        int y = gamePanel.screenHeight - height  - gamePanel.tileSize/2;
        UtilityTool.drawSubWindow(x, y, width, height, g2);

        String text = "Fight";
        int innnerWindowX = x + (width - ((int)(gamePanel.screenWidth * .85)))/2;
//        int innerWindowY = y - y/2 + (height - (int)(gamePanel.screenHeight * .25));
        int innerWindowY = (int)(gamePanel.screenHeight * .45) + (y/16);
        // parent margin + (parentWidth - (childWidth/2)) to center the window
        UtilityTool.drawSubWindow( innnerWindowX, innerWindowY, (int)(gamePanel.screenWidth * .17), (int)(gamePanel.screenHeight * .45), g2);
        x = UtilityTool.getXforCenteredText(text, gamePanel, g2);
        y = gamePanel.screenHeight/2;
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
        int length;
        int stringX;


        // iterate over enums using for loop
        int i = 0;
        for (options option : options.values()) {
            // Here in case the text needs to be centered
            // length = (int)g2.getFontMetrics().getStringBounds(option, g2).getWidth();
            // Center the text
            // stringX = (int)(gamePanel.screenWidth * .15) - length/2;
            if(currentOption == option){
                stringX = (int)(gamePanel.screenWidth * .15) - innnerWindowX/2 + gamePanel.tileSize/3;

            }else{

                stringX = (int)(gamePanel.screenWidth * .15) - innnerWindowX/2;
            }

            if(currentOption == option ){
                g2.drawString(">", (int)(gamePanel.screenWidth * .15) - innnerWindowX/2, innerWindowY + gamePanel.tileSize * (i+1));
            }

            g2.drawString(String.valueOf(option), stringX, innerWindowY + gamePanel.tileSize * (i+1));
            i++;
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
                if(currentOption == options.Skills){
                    battleManager.pushState(new SkillSelectState(battleManager, gamePanel));
                }
                // Check the option and act accordingly
                // Setup the state stack later
                // Attack and skills have subStates



        }
    }
}
