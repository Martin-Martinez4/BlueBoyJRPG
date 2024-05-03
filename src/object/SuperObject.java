package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public void draw(Graphics2D g2, GamePanel gamePanel){
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        // only render the objects that are seen
        if (screenX + gamePanel.tileSize > 0 &&
                screenY + gamePanel.tileSize > 0 &&
                screenX < gamePanel.screenWidth &&
                screenY < gamePanel.screenHeight) {
            g2.drawImage(image, screenX, screenY, gamePanel.tileSize,
                    gamePanel.tileSize, null);
        }
    }

}
