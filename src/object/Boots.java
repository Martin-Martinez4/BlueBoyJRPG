package object;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Boots extends SuperObject{
    GamePanel gamePanel;

    public Boots(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        name = "boots";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
            UtilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
