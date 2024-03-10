package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelManager {

    static JLabel healthLabel, scoreLabel, energyLabel, gamePausedLabel;
    static GameFrame gameFrame = new GameFrame();
    public static JPanel menuPanel() {

        JLabel gameNameLabel = new JLabel("EnemiesES");
        gameNameLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 85));
        gameNameLabel.setForeground(Color.red);
        gameNameLabel.setSize(500, 150);
        gameNameLabel.setLocation(GameFrame.GAME_WIDTH / 2 - gameNameLabel.getWidth() / 2 + 20, 50);
        gameNameLabel.setVisible(true);

        JButton newGameButton = new JButton("New Game");
        newGameButton.setSize(200, 50);
        newGameButton.setLocation(GameFrame.GAME_WIDTH / 2 - newGameButton.getWidth() / 2, GameFrame.GAME_HEIGHT / 2 - 50);
        newGameButton.setForeground(Color.black);
        newGameButton.setBackground(Color.lightGray);
        newGameButton.setFocusable(false);
        newGameButton.setFont(new Font("Dialog", Font.BOLD, 25));
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameManager.gameStatus = GameStatuses.IN_GAME;
                gameFrame.clearFrame();
                gameFrame.add(gamePanel());
                gameFrame.initGame();
                gameFrame.repaint();
                gameFrame.addKeyListener(new KeyHandler());
                GameManager.spawnEnemies();
            }
        });
        newGameButton.setVisible(true);

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.setSize(200, 50);
        loadGameButton.setLocation(GameFrame.GAME_WIDTH / 2 - loadGameButton.getWidth() / 2, newGameButton.getY() + 75);
        loadGameButton.setForeground(Color.black);
        loadGameButton.setBackground(Color.lightGray);
        loadGameButton.setFont(new Font("Dialog", Font.BOLD, 25));
        loadGameButton.setEnabled(false);
        loadGameButton.setFocusable(false);
        loadGameButton.setVisible(true);

        JButton settingsButton = new JButton("Settings");
        settingsButton.setSize(200, 50);
        settingsButton.setLocation(GameFrame.GAME_WIDTH / 2 - settingsButton.getWidth() / 2, newGameButton.getY() + 150);
        settingsButton.setForeground(Color.black);
        settingsButton.setBackground(Color.lightGray);
        settingsButton.setFont(new Font("Dialog", Font.BOLD, 25));
        settingsButton.setEnabled(false);
        settingsButton.setFocusable(false);
        settingsButton.setVisible(true);

        JButton exitButton = new JButton("Exit");
        exitButton.setSize(200, 50);
        exitButton.setLocation(GameFrame.GAME_WIDTH / 2 - exitButton.getWidth() / 2, newGameButton.getY() + 225);
        exitButton.setForeground(Color.black);
        exitButton.setBackground(Color.lightGray);
        exitButton.setFont(new Font("Dialog", Font.BOLD, 25));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exitButton.setFocusable(false);
        exitButton.setVisible(true);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, GameFrame.GAME_WIDTH,  GameFrame.GAME_HEIGHT);
        panel.setBackground(Color.black);
        panel.setFocusable(true);
        panel.setLayout(null);
        panel.add(gameNameLabel);
        panel.add(newGameButton);
        panel.add(loadGameButton);
        panel.add(settingsButton);
        panel.add(exitButton);
        panel.setVisible(true);
        return panel;
    }
    public static JPanel gamePanel() {

        healthLabel = new JLabel("Health: " + GameManager.health);
        healthLabel.setFont(new Font("Dialog", Font.PLAIN, 45));
        healthLabel.setForeground(Color.white);
        healthLabel.setBounds(GameFrame.SIZE, 0, 250, 100);
        healthLabel.setVisible(true);

        scoreLabel = new JLabel("Score: " + GameManager.score);
        scoreLabel.setFont(new Font("Dialog", Font.PLAIN, 45));
        scoreLabel.setForeground(Color.white);
        scoreLabel.setBounds(GameFrame.SIZE * 8, 0, 250, 100);
        scoreLabel.setVisible(true);

        energyLabel = new JLabel("%" + GameManager.energy);
        energyLabel.setFont(new Font("Dialog", Font.PLAIN, 45));
        energyLabel.setForeground(Color.white);
        energyLabel.setBounds(GameFrame.GAME_WIDTH - GameFrame.SIZE * 5, 0, 300, 100);
        energyLabel.setVisible(true);

        gamePausedLabel = new JLabel("Game Paused");
        gamePausedLabel.setFont(new Font("Dialog", Font.ITALIC, 80));
        gamePausedLabel.setSize(gamePausedLabel.getPreferredSize());
        gamePausedLabel.setLocation(GameFrame.GAME_WIDTH / 2 - gamePausedLabel.getWidth() / 2, GameFrame.GAME_HEIGHT / 2 - gamePausedLabel.getHeight() / 2);
        gamePausedLabel.setForeground(Color.white);
        gamePausedLabel.setVisible(false);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, GameFrame.GAME_WIDTH,  GameFrame.GAME_HEIGHT);
        panel.setBackground(Color.black);
        panel.addKeyListener(new KeyHandler());
        panel.addMouseListener(new KeyHandler());
        panel.setFocusable(true);
        panel.setLayout(null);
        panel.add(healthLabel);
        panel.add(scoreLabel);
        panel.add(energyLabel);
        panel.add(gamePausedLabel);
        panel.setVisible(true);
        return panel;
    }

    public static JPanel endPanel() {
        JLabel gameOverLabel = new JLabel("Game Over!");
        gameOverLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 85));
        gameOverLabel.setForeground(Color.red);
        gameOverLabel.setSize(500, 150);
        gameOverLabel.setLocation(GameFrame.GAME_WIDTH / 2 - gameOverLabel.getWidth() / 2 + 20, 50);
        gameOverLabel.setVisible(true);

        String hourString = GameManager.hours < 10 ? "0" + GameManager.hours : String.valueOf(GameManager.hours);
        String minuteString = GameManager.minutes < 10 ? "0" + GameManager.minutes : String.valueOf(GameManager.minutes);
        String secondString = GameManager.seconds < 10 ? "0" + GameManager.seconds : String.valueOf(GameManager.seconds);
        String timeFormat = "Time: " + hourString + ":" + minuteString + ":" + secondString;

        JLabel timeLabel = new JLabel(timeFormat);
        timeLabel.setFont(new Font("Bookman Old Style", Font.ITALIC, 50));
        timeLabel.setForeground(Color.white);
        timeLabel.setSize(500, 150);
        timeLabel.setLocation(GameFrame.GAME_WIDTH / 2 - (timeLabel.getWidth() / 2 - 70), 150);
        timeLabel.setVisible(true);

        JLabel scoreLabel = new JLabel("Your Score: " + GameManager.score);
        scoreLabel.setFont(new Font("Bookman Old Style", Font.ITALIC, 50));
        scoreLabel.setForeground(Color.white);
        scoreLabel.setSize(500, 150);
        scoreLabel.setLocation(GameFrame.GAME_WIDTH / 2 - (scoreLabel.getWidth() / 2 - 70), 225);
        scoreLabel.setVisible(true);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setSize(200, 50);
        playAgainButton.setLocation(GameFrame.GAME_WIDTH / 2 - playAgainButton.getWidth() / 2, 400);
        playAgainButton.setForeground(Color.black);
        playAgainButton.setBackground(Color.lightGray);
        playAgainButton.setFont(new Font("Dialog", Font.BOLD, 25));
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.clearFrame();
                GameManager.resetGame();
                gameFrame.add(gamePanel());
            }
        });
        playAgainButton.setFocusable(false);
        playAgainButton.setVisible(true);

        JButton backToMenuButton = new JButton("Back to Menu");
        backToMenuButton.setSize(200, 50);
        backToMenuButton.setLocation(GameFrame.GAME_WIDTH / 2 - backToMenuButton.getWidth() / 2, 475);
        backToMenuButton.setForeground(Color.black);
        backToMenuButton.setBackground(Color.lightGray);
        backToMenuButton.setFont(new Font("Dialog", Font.BOLD, 25));
        backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.clearFrame();
                GameManager.resetGame();
                gameFrame.add(menuPanel());

            }
        });
        backToMenuButton.setFocusable(false);
        backToMenuButton.setVisible(true);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT);
        panel.setBackground(Color.black);
        panel.add(gameOverLabel);
        panel.add(timeLabel);
        panel.add(scoreLabel);
        panel.add(playAgainButton);
        panel.add(backToMenuButton);
        panel.setVisible(true);
        return panel;
    }
}
