package StateManager;


import java.awt.*;
import java.awt.event.KeyEvent;

public interface State {
    public String name = "";
    public void draw(Graphics2D g2);
    public void handleInputs(KeyEvent e);
}
