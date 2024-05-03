package main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class Battle {

    // Do not create new instances inside the game loop or else a new instance will be created 60 times per second
    GamePanel gamePanel;
    Graphics2D g2;
    Font arial_40, arial_80B;
    // From Font Folder
    Font jersey15;
    Font vT323;
    Font iBMPlexMono;
    //    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    public String currentDialogue = "";


    public Battle(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
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

    public void showMessage(String text) {
        this.message = text;
        this.messageOn = true;
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
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize/2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize*4);
        int height = gamePanel.screenHeight - (gamePanel.tileSize*4);
        drawSubWindow(x, y, width, height);
        String text = "Fight";
        x = getXforCenteredText(text);
        y = gamePanel.screenHeight/2;
        g2.drawString(text, x, y);
    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color windowColor = new Color(0,0,0, 225);
        g2.setColor(windowColor);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        Color borderColor = new Color(255,255,255);
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x,y, width, height, 35,35);

    }

    public int getXforCenteredText(String text){
        int x;
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        // Center the text
        x = gamePanel.screenWidth/2 - length/2;
        return x;
    }
}
