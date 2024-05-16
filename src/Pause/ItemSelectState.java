package Pause;

import StateManager.State;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ItemSelectState implements State {
    @Override
    public void draw(Graphics2D g2) {
        // Substates
        // Choose item first boolean check
        // if true allow player to be picked
        // If it is too much hassle break up into item select and target select states

    }

    @Override
    public void handleInputs(KeyEvent e) {

    }
}
