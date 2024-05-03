package object;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Door extends SuperObject{
    GamePanel gamePanel;

    public Door(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        name = "door";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
            UtilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);

        }catch (IOException e){
            e.printStackTrace();
        }
        collision = true;
    }

}
