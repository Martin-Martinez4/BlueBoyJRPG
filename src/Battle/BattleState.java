package Battle;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface BattleState {

    public void draw(Graphics2D g2);
    public void handleInputs(KeyEvent e);
}
