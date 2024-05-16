package main;

import Pause.PauseMenuManager;
import object.Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {

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
    int messageCounter = 0;
    int messageDuration = 80;
    public boolean gameFinished = false;

    PauseMenuManager pauseMenuManager;



    public enum TitleCommands{
        LoadGame,
        NewGame,
        Quit
    }

    public TitleCommands titleCommand = TitleCommands.LoadGame;

    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.pauseMenuManager = new PauseMenuManager(gamePanel, g2);
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
            case titleState:
                drawTitleScreen();
                break;
            case playState:
                // Do player stuff later
                break;
            case pauseState:
//                drawPauseScreen();
                pauseMenuManager.draw(g2);
                break;
            case dialogueState:
                drawDialogueScreen();
                break;
        }


    // Old Game one things
//        if(gameFinished){
//            String text;
//            int textLength;
//            int x;
//            int y;
//
//            text = "You found the treasure!";
//            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//            x = gamePanel.screenWidth/2 - textLength/2;
//            y = gamePanel.screenWidth/2 - (gamePanel.tileSize);
//            g2.drawString(text, x, y);
//
//            text = "Your time is: "+decimalFormat.format(playTime) + "!";
//            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//            x = gamePanel.screenWidth/2 - textLength/2;
//            y = gamePanel.screenWidth/2 + (gamePanel.tileSize*3);
//            g2.drawString(text, x, y);
//
//            g2.setFont(arial_80B);
//            g2.setColor(Color.yellow);
//
//            text = "Congratulations!";
//            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//
//            x = gamePanel.screenWidth/2 - textLength/2;
//            y = gamePanel.screenWidth/2 - (gamePanel.tileSize * 2);
//            g2.drawString(text, x, y);
//
//            // Stops the game
//            gamePanel.gameThread = null;
//
//
//        }else{
//
//            g2.drawImage(keyImage, gamePanel.tileSize/2, gamePanel.tileSize/2, gamePanel.tileSize, gamePanel.tileSize, null);
//            // y is the baseline of the text
//            g2.drawString(" "+ gamePanel.player.numberOfKeys, 74, 65);
//
//            // Time
//            playTime += (double) 1/60;
//            g2.drawString("Time: "+ decimalFormat.format(playTime), gamePanel.tileSize*11, 63);
//
//            // Message
//            if(messageOn){
//                g2.setFont(g2.getFont().deriveFont(30F));
//                g2.drawString(this.message, gamePanel.tileSize/2, gamePanel.tileSize * 5);
//                messageCounter ++;
//
//                if(messageCounter > messageDuration){
//                    messageCounter = 0;
//                    messageOn = false;
//                    message = "";
//
//                }
//            }
//        }
}


    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gamePanel.screenHeight/2;
        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen(){
        // window
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize/2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize*4);
        int height = gamePanel.tileSize*4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));

        // If you want antialiasing on text turned on
//        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        int textX = gamePanel.tileSize * 2 + gamePanel.tileSize;
        int textY = gamePanel.tileSize/2 + gamePanel.tileSize;

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 40;

        }
    }

    public void drawTitleScreen(){
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        // Title name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Blue Boy Adventure";
        int x = getXforCenteredText(text);
        int y = gamePanel.tileSize * 3;

        // Shadow
        g2.setColor(Color.darkGray);
        g2.drawString(text, x+5, y+5);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // Blue Boy Image
        // x = gamePanel.screenWidth/2 - gamePanel.tileSize;
        // y += gamePanel.tileSize * 2;

        // g2.drawImage(gamePanel.player.down1, x, y, gamePanel.tileSize*2, gamePanel.tileSize*2, null);

        // Menu
        text = "LOAD GAME";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));
        x = getXforCenteredText(text);
        y += gamePanel.tileSize * 4;
        g2.drawString(text, x, y);
        if(titleCommand == TitleCommands.LoadGame){
            g2.drawString(">", x- gamePanel.tileSize/2, y);
        }

        text = "NEW GAME";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));
        x = getXforCenteredText(text);
        y += gamePanel.tileSize;
        g2.drawString(text, x, y);
        if(titleCommand == TitleCommands.NewGame){
            g2.drawString(">", x- gamePanel.tileSize/2, y);
        }

        text = "QUIT";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));
        x = getXforCenteredText(text);
        y += gamePanel.tileSize;
        g2.drawString(text, x, y);
        if(titleCommand == TitleCommands.Quit){
            g2.drawString(">", x- gamePanel.tileSize/2, y);
        }

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
