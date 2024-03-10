package test;

import javax.swing.*;

public class Game {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PanelManager.gameFrame.setVisible(true);
            }
        });
    }
}
