package Pause;

import StateManager.StateManager;
import StateManager.State;
import main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PauseMenuManager implements StateManager {

    GamePanel gamePanel;
    Graphics2D g2;

    private ArrayList<State> menuStates = new ArrayList<State>();

    public PauseMenuManager(GamePanel gamePanel, Graphics2D g2){
        this.gamePanel = gamePanel;
        this.g2 = g2;

        this.pushState(new PauseMenuState(this, gamePanel));
    }


    @Override
    public void draw(Graphics2D g2) {
        // May not be needed, but I do not want to mess with it now
        this.g2 = g2;

        drawScene();


    }

    public void drawScene(){

        this.menuStates.get(menuStates.size()-1).draw(g2);

    }

    public void handleInputs(KeyEvent e){
        this.menuStates.get(menuStates.size()-1).handleInputs(e);

    }

    public void popState(){
        int firstIndex = menuStates.size()-1;
        if(firstIndex > 0){

            menuStates.remove(firstIndex);
        }
    }

    public State peek(){
        return menuStates.get(menuStates.size()-1);
    }

    public void popAllExceptFirst(){
        while(menuStates.size() > 1){
            this.popState();
        }

    }

    public void pushState(State battleState){
        menuStates.add(battleState);
    }

    public void printStack(){
        for(int i = 0; i < this.menuStates.size(); i++){
            System.out.println(menuStates.get((menuStates.size() - 1) - i));
        }
    }
}
