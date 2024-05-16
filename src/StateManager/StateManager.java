package StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface StateManager {

        
        public void draw(Graphics2D g2);
        public void drawScene();
        public void handleInputs(KeyEvent e);

        public void popState();

        public State peek();

        public void popAllExceptFirst();

        public void pushState(State state);

        public void printStack();


    }

