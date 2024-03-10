package com.devenes.Enemies;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelManager {
    static JLabel healthLabel, scoreLabel, energyLabel, gamePausedLabel;
    static GameFrame gameFrame = new GameFrame();
    static boolean firstTime = true;
    public static JPanel menuPanel() {

        JLabel gameNameLabel = new JLabel("ENEMIES");
        gameNameLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 150));
        gameNameLabel.setForeground(Color.white);
        gameNameLabel.setSize(1000, 150);
        gameNameLabel.setLocation(GameFrame.GAME_WIDTH / 2 - gameNameLabel.getWidth() / 2 + 120, 100);
        gameNameLabel.setVisible(true);

        JButton startButton = new JButton("Start");
        startButton.setSize(200, 40);
        startButton.setLocation(GameFrame.GAME_WIDTH / 2 - startButton.getWidth() / 2, GameFrame.GAME_HEIGHT / 2 + 40);
        startButton.setForeground(Color.white);
        startButton.setBackground(Color.black);
        startButton.setFocusable(false);
        startButton.setFont(new Font("Californian FB", Font.PLAIN, 30));
        startButton.setBorder(null);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameManager.gameStatus = GameStatuses.IN_GAME;
                gameFrame.clearFrame();
                gameFrame.add(gamePanel());
                if (firstTime) {
                    firstTime = false;
                    gameFrame.initGame();
                    gameFrame.repaint();
                    gameFrame.addKeyListener(new KeyHandler());
                    GameManager.spawnEnemies();
                } else {
                    GameManager.restartGame();
                }
            }
        });
        startButton.setVisible(true);

        JButton continueButton = new JButton("Continue");
        continueButton.setSize(200, 40);
        continueButton.setLocation(GameFrame.GAME_WIDTH / 2 - continueButton.getWidth() / 2, startButton.getY() + 40);
        continueButton.setForeground(Color.white);
        continueButton.setBackground(Color.black);
        continueButton.setFont(new Font("Californian FB", Font.PLAIN, 30));
        continueButton.setBorder(null);
        continueButton.setEnabled(false);
        continueButton.setFocusable(false);
        continueButton.setVisible(true);

        JButton settingsButton = new JButton("Settings");
        settingsButton.setSize(200, 40);
        settingsButton.setLocation(GameFrame.GAME_WIDTH / 2 - settingsButton.getWidth() / 2, startButton.getY() + 80);
        settingsButton.setForeground(Color.white);
        settingsButton.setBackground(Color.black);
        settingsButton.setFont(new Font("Californian FB", Font.PLAIN, 30));
        settingsButton.setBorder(null);
        settingsButton.setEnabled(false);
        settingsButton.setFocusable(false);
        settingsButton.setVisible(true);

        JButton quitButton = new JButton("Quit");
        quitButton.setSize(200, 40);
        quitButton.setLocation(GameFrame.GAME_WIDTH / 2 - quitButton.getWidth() / 2, startButton.getY() + 120);
        quitButton.setForeground(Color.white);
        quitButton.setBackground(Color.black);
        quitButton.setFont(new Font("Californian FB", Font.PLAIN, 30));
        quitButton.setBorder(null);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        quitButton.setFocusable(false);
        quitButton.setVisible(true);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, GameFrame.GAME_WIDTH,  GameFrame.GAME_HEIGHT);
        panel.setBackground(Color.black);
        panel.setFocusable(true);
        panel.setLayout(null);
        panel.add(gameNameLabel);
        panel.add(startButton);
        panel.add(continueButton);
        panel.add(settingsButton);
        panel.add(quitButton);
        panel.setVisible(true);
        return panel;
    }
    public static JPanel gamePanel() {

        healthLabel = new JLabel("Health: " + GameManager.health);
        healthLabel.setFont(new Font("Californian FB", Font.PLAIN, 45));
        healthLabel.setForeground(Color.white);
        healthLabel.setBounds(GameFrame.SIZE, 0, 250, 100);
        healthLabel.setVisible(true);

        scoreLabel = new JLabel("Score: " + GameManager.score);
        scoreLabel.setFont(new Font("Californian FB", Font.PLAIN, 45));
        scoreLabel.setForeground(Color.white);
        scoreLabel.setBounds(GameFrame.SIZE, 50, 250, 100);
        scoreLabel.setVisible(true);

        energyLabel = new JLabel("%" + GameManager.energy);
        energyLabel.setFont(new Font("Californian FB", Font.PLAIN, 45));
        energyLabel.setForeground(Color.white);
        energyLabel.setBounds(GameFrame.GAME_WIDTH - GameFrame.SIZE * 3, 0, 300, 100);
        energyLabel.setVisible(true);

        gamePausedLabel = new JLabel("Game Paused");
        gamePausedLabel.setFont(new Font("Californian FB", Font.ITALIC, 80));
        gamePausedLabel.setSize(gamePausedLabel.getPreferredSize());
        gamePausedLabel.setLocation(GameFrame.GAME_WIDTH / 2 - gamePausedLabel.getWidth() / 2, GameFrame.GAME_HEIGHT / 2 - gamePausedLabel.getHeight() / 2);
        gamePausedLabel.setForeground(Color.white);
        gamePausedLabel.setOpaque(false);
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

    public static JPanel escPanel() {
        JLabel gamePaused = new JLabel("Game Paused");
        gamePaused.setFont(new Font("Californian FB", Font.ITALIC, 20));
        gamePaused.setSize(gamePaused.getPreferredSize());
        gamePaused.setForeground(Color.white);
        gamePaused.setVisible(true);

        JPanel panel = new JPanel();
        panel.setBounds(100, 200, GameFrame.GAME_WIDTH / 2,  GameFrame.GAME_HEIGHT / 2);
        panel.setBackground(Color.white);
        panel.setLayout(null);
        panel.add(gamePaused);
        panel.setVisible(false);
        return panel;
    }

    public static JPanel endPanel() {
        JLabel gameOverLabel = new JLabel("GAME OVER!");
        gameOverLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 120));
        gameOverLabel.setForeground(Color.decode("#FF6D6D"));
        gameOverLabel.setSize(1000, 150);
        gameOverLabel.setLocation(250, 80);
        gameOverLabel.setVisible(true);

        String hourString = GameManager.hours < 10 ? "0" + GameManager.hours : String.valueOf(GameManager.hours);
        String minuteString = GameManager.minutes < 10 ? "0" + GameManager.minutes : String.valueOf(GameManager.minutes);
        String secondString = GameManager.seconds < 10 ? "0" + GameManager.seconds : String.valueOf(GameManager.seconds);
        String timeFormat = "Time: " + hourString + ":" + minuteString + ":" + secondString;

        JLabel timeLabel = new JLabel(timeFormat);
        timeLabel.setFont(new Font("Bookman Old Style", Font.ITALIC, 40));
        timeLabel.setForeground(Color.white);
        timeLabel.setSize(500, 150);
        timeLabel.setLocation(GameFrame.GAME_WIDTH / 2 - (timeLabel.getWidth() / 2 - 70) + 35, 200);
        timeLabel.setVisible(true);

        JLabel scoreLabel = new JLabel("Your Score: " + GameManager.score);
        scoreLabel.setFont(new Font("Bookman Old Style", Font.ITALIC, 40));
        scoreLabel.setForeground(Color.white);
        scoreLabel.setSize(500, 150);
        scoreLabel.setLocation(GameFrame.GAME_WIDTH / 2 - (scoreLabel.getWidth() / 2 - 70) + 35, 250);
        scoreLabel.setVisible(true);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setSize(210, 40);
        playAgainButton.setLocation(GameFrame.GAME_WIDTH / 2 - playAgainButton.getWidth() / 2, 440);
        playAgainButton.setForeground(Color.white);
        playAgainButton.setBackground(Color.black);
        playAgainButton.setFont(new Font("Californian FB", Font.PLAIN, 30));
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.clearFrame();
                GameManager.gameStatus = GameStatuses.IN_GAME;
                GameManager.restartGame();
                gameFrame.add(gamePanel());
            }
        });
        playAgainButton.setFocusable(false);
        playAgainButton.setVisible(true);

        JButton backToMenuButton = new JButton("Back to Menu");
        backToMenuButton.setSize(210, 40);
        backToMenuButton.setLocation(GameFrame.GAME_WIDTH / 2 - backToMenuButton.getWidth() / 2, 500);
        backToMenuButton.setForeground(Color.white);
        backToMenuButton.setBackground(Color.black);
        backToMenuButton.setFont(new Font("Californian FB", Font.PLAIN, 30));
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
