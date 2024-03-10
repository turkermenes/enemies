package test;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends JFrame  {

    static final int GAME_WIDTH = 1280;
    static final int GAME_HEIGHT = 720;
    static final int SIZE = 45;
    static final int DELAY = 16;
    Timer time = new Timer();
    public GameFrame() {
        super("EnemiesES");
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setFocusable(true);
        setLayout(null);
        setResizable(false);
        add(PanelManager.menuPanel());
    }

    public void initGame() {
        GameManager.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!GameManager.isGamePaused) {
                    GameManager.enemyMove();
                    GameManager.enemyAttack();
                    GameManager.checkCollisions();
                    repaint();
                }
            }
        }, 0, DELAY);

        time.schedule(new TimerTask() {
            @Override
            public void run() {
                GameManager.seconds++;
                if (GameManager.seconds == 60) {
                    GameManager.minutes++;
                    GameManager.seconds = 0;
                }
                if (GameManager.minutes == 60) {
                    GameManager.hours++;
                    GameManager.minutes = 0;
                }
            }
        }, 0, 1000);
    }

    public void cancelTimers() {
        GameManager.timer.cancel();
        time.cancel();
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (GameManager.gameStatus == GameStatuses.IN_GAME) {
            g.setColor(Color.BLUE);
            g.fillRect(GameManager.playerX, GameManager.playerY, SIZE, SIZE);
            if (GameManager.isShooted) {
                g.setColor(Color.GREEN);
                g.fillOval(GameManager.bulletX, GameManager.bulletY, 15, 15);
            }
            for (int i = 0; i < GameManager.coordsEnemies.size(); i++) {
                g.setColor(Color.red);
                g.fillRect((int) GameManager.coordsEnemies.get(i).getX(), (int) GameManager.coordsEnemies.get(i).getY(), SIZE, SIZE);
                g.setColor(Color.white);
                g.setFont(new Font("Dialog", Font.BOLD, 30));
                g.drawString(String.valueOf(GameManager.healthsOfEnemies.get(i)), (int) GameManager.coordsEnemies.get(i).getX() + SIZE / 4, (int) GameManager.coordsEnemies.get(i).getY() + g.getFont().getSize());
            }
        }
    }

    public void clearFrame() {
        getContentPane().removeAll();
        repaint();
        revalidate();

    }

}
