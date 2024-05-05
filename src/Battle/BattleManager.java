package Battle;

import main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BattleManager {

    // Do not create new instances inside the game loop or else a new instance will be created 60 times per second
    GamePanel gamePanel;
    Graphics2D g2;

    private ArrayList<BattleState> battleStates = new ArrayList<BattleState>();

    Font arial_40, arial_80B;
    // From Font Folder
    Font jersey15;
    Font vT323;
    Font iBMPlexMono;


    public BattleManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.arial_40 = new Font("Arial", Font.PLAIN, 40);
        this.arial_80B = new Font("Arial", Font.BOLD, 80);
//        this.keyImage = new Key(this.gamePanel).image;
        this.battleStates.add(new ActionSelectState(this, gamePanel));

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

        this.battleStates.get(battleStates.size()-1).draw(g2);

    }

    public void handleInputs(KeyEvent e){
        this.battleStates.get(battleStates.size()-1).handleInputs(e);

    }

    public void popState(){
        int firstIndex = battleStates.size()-1;
        if(firstIndex > 0){

            battleStates.remove(firstIndex);
        }
    }

    public void setPlayerTeam(){

    }

    public void setEnemyTeam(){

    }

    public void pushState(BattleState battleState){
        battleStates.add(battleState);
    }


}
