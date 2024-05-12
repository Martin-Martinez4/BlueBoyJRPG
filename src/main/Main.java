package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // If this line is not here it makes the game laggy on Linux
        System.setProperty("sun.java2d.opengl", "true");
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");

        // Could serialize an object and pass in the state when building the game panel

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupObjects();
        gamePanel.startGameThread();
    }
}