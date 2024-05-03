package object;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Chest extends SuperObject{
    GamePanel gamePanel;

    public Chest(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        name = "chest";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
            UtilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
