package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tiles;
    public int mapTileNum[][];

    public TileManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        this.tiles = new Tile[50];
        this.mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTileImage();
        loadMap("/maps/worldV2.txt");

    }

    public void getTileImage(){


            setup(0, "/tiles/grass.png", false);
            setup(1, "/tiles/wall.png", false);
            setup(2, "/tiles/water.png", false);
            setup(3, "/tiles/earth.png", false);
            setup(4, "/tiles/tree.png", false);
            setup(5, "/tiles/sand.png", false); setup(2, "/tiles/water.png", false);
            setup(6, "/tiles/earth.png", false);
            setup(7, "/tiles/tree.png", false);
            setup(8, "/tiles/sand.png", false);
            setup(9, "/tiles/sand.png", false);

//            setup(10, "/tiles/000.png", false);
            setup(10, "/tiles/grass00.png", false);
            setup(11, "/tiles/grass01.png", false);
            setup(12, "/tiles/water00.png", true);
            setup(13, "/tiles/water01.png", true);
            setup(14, "/tiles/water02.png", true);
            setup(15, "/tiles/water03.png", true);
            setup(16, "/tiles/water04.png", true);
            setup(17, "/tiles/water05.png", true);
            setup(18, "/tiles/water06.png", true);
            setup(19, "/tiles/water07.png", true);
            setup(20, "/tiles/water08.png", true);
            setup(21, "/tiles/water09.png", true);
            setup(22, "/tiles/water10.png", true);
            setup(23, "/tiles/water11.png", true);
            setup(24, "/tiles/water12.png", true);
            setup(25, "/tiles/water13.png", true);
            setup(26, "/tiles/road00.png", false);
            setup(27, "/tiles/road01.png", false);
            setup(28, "/tiles/road02.png", false);
            setup(29, "/tiles/road03.png", false);
            setup(30, "/tiles/road04.png", false);
            setup(31, "/tiles/road05.png", false);
            setup(32, "/tiles/road06.png", false);
            setup(33, "/tiles/road07.png", false);
            setup(34, "/tiles/road08.png", false);
            setup(35, "/tiles/road09.png", false);
            setup(36, "/tiles/road10.png", false);
            setup(37, "/tiles/road11.png", false);
            setup(38, "/tiles/road12.png", false);
            setup(39, "/tiles/earth.png", false);
            setup(40, "/tiles/wall.png", true);
            setup(41, "/tiles/tree.png", true);
            setup(42, "/tiles/034.png", false);
            setup(43, "/tiles/035.png", false);
            setup(44, "/tiles/036.png", false);
            setup(47, "/tiles/037.png", false);

    }

    public void setup(int index, String imagePath, boolean collision){

        try{
            // Do scaling here so it does not have to be done later
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            tiles[index].image = UtilityTool.scaleImage(tiles[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tiles[index].collision = collision;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String mapFilePath){
        try{
            InputStream inputStream = getClass().getResourceAsStream(mapFilePath);
            assert inputStream != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;

            while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
               String line = br.readLine();

               while(col < gamePanel.maxWorldCol){
                   String[] numbers = line.split(" ");

                   int num  = Integer.parseInt(numbers[col]);

                   mapTileNum[col][row] = num;
                   col++;
               }
               if(col == gamePanel.maxWorldCol){
                   col = 0;
                   row++;
               }
            }
            br.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        // Pretty sure this can be done better
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            // only render the tiles that are seen
            if (screenX + gamePanel.tileSize > 0 &&
                    screenY + gamePanel.tileSize > 0 &&
                    screenX < gamePanel.screenWidth &&
                    screenY < gamePanel.screenHeight) {
                g2.drawImage(tiles[tileNum].image, screenX, screenY, null);
            }
            worldCol++;

            if(worldCol == gamePanel.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
//    public void draw(Graphics2D g2){
//        // Pretty sure this can be done better
//        int col = 0;
//        int worldRow = 0;
//        int x = 0;
//        int y = 0;
//
//        while(col < gamePanel.maxScreenCol && worldRow < gamePanel.maxScreenRow){
//
//            int tileNum = mapTileNum[col][worldRow];
//
//            g2.drawImage(tiles[tileNum].image, x ,  y, gamePanel.tileSize, gamePanel.tileSize, null);
//            col++;
//            x += gamePanel.tileSize;
//
//            if(col == gamePanel.maxScreenCol){
//                col = 0;
//                x = 0;
//                worldRow++;
//                y += gamePanel.tileSize;
//            }
//        }
//    }
}
