package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class UtilityTool {

    public static BufferedImage scaleImage(BufferedImage originalImage, int width, int height){
        BufferedImage scaledImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(originalImage, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }

    // Move to utils
    public static void drawSubWindow(int x, int y, int width, int height, Graphics2D g2){
        Color windowColor = new Color(0,0,0, 225);
        g2.setColor(windowColor);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        Color borderColor = new Color(255,255,255);
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x,y, width, height, 20,20);

    }

    public static int getXforCenteredText(String text, GamePanel gamePanel, Graphics2D g2){
        int x;
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        // Center the text
        x = gamePanel.screenWidth/2 - length/2;
        return x;
    }
}
