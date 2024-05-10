package Battle;

import java.awt.*;
import java.awt.event.KeyEvent;

public class LevelUpState implements  BattleState{

    @Override
    public void draw(Graphics2D g2) {
        // Draw another page that forces a user to allocate the available points
        // Will happen once per character that leveled up
        // might have them stack up during the results page phase
        // Make points accumulate and allocate once

    }

    @Override
    public void handleInputs(KeyEvent e) {

    }
}
