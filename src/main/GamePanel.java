package main;

import Battle.BattleManager;
import Battle.TurnOrderManager;
import entity.Entity;
import entity.Player;
import entity.combatants.Combatant;
import entity.combatants.JackFrost;
import entity.combatants.Slime;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{
    // Screen settings
    // 16x16 tiles
    final int originalTileSize = 16;
    // Scale the sprites so that they look bigger on modern screens
    // 16 * 3 scale
    public final int scale = 4;

    final public int tileSize = originalTileSize * scale;

    // Determine the max tiles allowed on the screen
    final public int maxScreenCol = 18;
    final public int maxScreenRow = 12;
    final public int screenWidth = tileSize * maxScreenCol;
    final public int screenHeight = tileSize * maxScreenRow;

    // World Settings
    public final int  maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int fps = 60;
    double nanoSecsPerSec = 1_000_000_000;

    TileManager tileManager = new TileManager(this);

    public KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();

    // Makes the game keep running
    Thread gameThread;

    public CollisionChecker collisionChecker = new CollisionChecker(this);

    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public BattleManager battleManager;
    public Player player = new Player(this, keyHandler);
    // 10 objects will be displayed at the same time
    public SuperObject[] objects = new SuperObject[10];
    public Entity[] npc = new Entity[10];

    // Game state
    public enum gameStates{
        titleState,
        playState,
        pauseState,
        dialogueState,
        battleState
    }

    public gameStates gameState;

    public ArrayList<Combatant> playerTeam;

    public GamePanel(){



        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        // if true all the drawing from this component will be done in an offscreen painting buffer.
        // May increase rendering performance.
        this.setDoubleBuffered(true);
        keyHandler = new KeyHandler(this);
        music = new Sound();
        soundEffect = new Sound();

        this.reset();

        // Add event listener to the window
        this.setFocusable(true);

    }

    public void reset(){
        this.removeKeyListener(keyHandler);
        tileManager = new TileManager(this);

        collisionChecker = new CollisionChecker(this);

        assetSetter = new AssetSetter(this);
        ui = new UI(this);
        player = new Player(this, keyHandler);
        // 10 objects will be displayed at the same time
        objects = new SuperObject[10];
        npc = new Entity[10];


        playerTeam = null;

        this.playerTeam = new ArrayList<Combatant>();
        playerTeam.add(new JackFrost());
        playerTeam.add(new Slime());
        playerTeam.add(new Slime());

        this.battleManager = new BattleManager(this, this.playerTeam);

        this.gameState = gameStates.playState;

        this.addKeyListener(keyHandler);


    }

    // Change name because it does a lot of things now
    public void setupObjects(){
        gameState = gameStates.titleState;
        assetSetter.setObject();
        assetSetter.setNPC();
        //playMusic(0);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        // 0.01667 seconds
        double drawInterval = nanoSecsPerSec/fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        // Game loop
        while(gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                // Update: update information
                update();
                // draw: draw the screen with the updated information
                // repaint will run the paintComponent method
                repaint();

                delta--;
                // For FPS counter
                drawCount++;

            }
            // For FPS counter
//            if(timer >= nanoSecsPerSec){
//                System.out.println("FPS: "+ drawCount);
//                drawCount = 0;
//                timer = 0;
//            }

        }
    }

    public void update(){

        switch(gameState){
            case playState:
                player.update();
                for(int i = 0; i < npc.length; i++){
                    if(npc[i] != null){
                        npc[i].update();
                    }
                }
                break;
            default:
                break;
        }

    }

    // a built-in method has to be named this
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Debug Stuff
        long drawStart = 0;
        drawStart = System.nanoTime();

        // Title Screen
        if(gameState == gameStates.titleState){
            ui.draw(g2);
        } else if (gameState == gameStates.battleState) {

            battleManager.draw(g2);

        } else{
            // Have to draw the tiles first so the player is not hidden under the tile
            tileManager.draw(g2);

            for(int i = 0; i < objects.length; i++){
                if(objects[i] != null){
                    objects[i].draw(g2, this);
                }
            }

            // NPC
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    npc[i].draw(g2);
                }
            }

            player.draw(g2);

            // UI: top layer
            ui.draw(g2);
        }

        if(keyHandler.debug_drawTime){
            long drawEnd = System.nanoTime();
            long drawTime = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: "+ drawTime/nanoSecsPerSec + "s", 10, 400);
            System.out.println("Draw Time: "+ drawTime/nanoSecsPerSec +"s");

        }

        g2.dispose();
    }

    public void playMusic(int index){
        music.setFile(index);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSoundEffect(int index){
        soundEffect.setFile(index);
        soundEffect.play();
    }

    public void quitGame(){
        // Made into a method in case there is need to call clean up or tear down functions before quiting
        System.exit(0);
    }
}
