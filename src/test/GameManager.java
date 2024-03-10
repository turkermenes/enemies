package test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameManager {

    public static int playerX = 600;
    public static int playerY = 300;
    public static int gameStage = 1;
    static final int ENEMY_HEALTH = 3;
    static int mouseX, mouseY, bulletX, bulletY, enemyX, enemyY, score = 0;
    static boolean isShooted = false;
    static boolean isGamePaused = false;
    static int energy = 100, health = 100;
    static Timer timer = new Timer();
    static Random random;
    static ArrayList<Point> coordsEnemies = new ArrayList<>();
    static ArrayList<Integer> healthsOfEnemies = new ArrayList<>();
    static GameStatuses gameStatus = GameStatuses.PRE_GAME;
    static int seconds = 0, minutes = 0, hours = 0;
    public static int stageMaxEnemy() {
        return switch (gameStage) {
            case 1 -> 5;
            case 2 -> 8;
            case 3 -> 12;
            case 4 -> 15;
            default -> 0;
        };
    }


    public static void statusChanger() {
        switch (gameStatus) {
            case IN_GAME:
                break;
            case END_GAME:
                PanelManager.gameFrame.clearFrame();
                PanelManager.gameFrame.add(PanelManager.endPanel());
                PanelManager.gameFrame.cancelTimers();
                break;
        }

    }

    public void startGame() {

    }

    public static void resetGame() {
        playerX = 600;
        playerY = 300;
        gameStage = 1;
        score = 0;
        isShooted = false;
        isGamePaused = false;
        energy = 100;
        health = 100;
        coordsEnemies.clear();
        healthsOfEnemies.clear();
        seconds = 0;
        minutes = 0;
        hours = 0;
    }

    public static void spawnEnemies() {
        coordsEnemies.clear();
        healthsOfEnemies.clear();
        random = new Random();
        int numberOfEnemy = random.nextInt(stageMaxEnemy() / 2, stageMaxEnemy() + 1);
        for (int i = 0; i < numberOfEnemy; i++) {
            int chooseArea = random.nextInt(1, 5);
            switch (chooseArea) {
                case 1:
                    enemyX = -GameFrame.SIZE;
                    enemyY = random.nextInt(GameFrame.GAME_HEIGHT);
                    break;
                case 2:
                    enemyX = random.nextInt(GameFrame.GAME_WIDTH);
                    enemyY = -GameFrame.SIZE;
                    break;
                case 3:
                    enemyX = random.nextInt(GameFrame.GAME_WIDTH);
                    enemyY = GameFrame.GAME_HEIGHT + GameFrame.SIZE;
                    break;
                case 4:
                    enemyX = GameFrame.GAME_WIDTH + GameFrame.SIZE;
                    enemyY = random.nextInt(GameFrame.GAME_HEIGHT);
                    break;
            }
            coordsEnemies.add(new Point(enemyX, enemyY));
            healthsOfEnemies.add(ENEMY_HEALTH);
        }
    }

    public static void enemyMove() {
        int tempX = 0;
        int tempY = 0;
        int distanceX;
        int distanceY;
        for (int i = 0; i < coordsEnemies.size(); i++) {
            distanceX = random.nextInt(2, 5);
            distanceY = random.nextInt(2, 5);
            if ((int) coordsEnemies.get(i).getY() > playerY) {
                if (!((int) coordsEnemies.get(i).getY() - distanceY < playerY)) {
                    tempY = (int) coordsEnemies.get(i).getY() - distanceY;
                }
            } else {
                if (!((int) coordsEnemies.get(i).getY() + distanceY > playerY)) {
                    tempY = (int) coordsEnemies.get(i).getY() + distanceY;
                }
            }
            if (coordsEnemies.get(i).getX() > playerX) {
                if (!((int) coordsEnemies.get(i).getX() - distanceX < playerX)) {
                    tempX = (int) coordsEnemies.get(i).getX() - distanceX;
                }
            } else {
                if (!(coordsEnemies.get(i).getX() + distanceX > playerX)) {
                    tempX = (int) coordsEnemies.get(i).getX() + distanceX;
                }
            }
            while (coordsEnemies.contains(new Point(tempX, tempY))) {
                tempX += 2 * GameFrame.SIZE;
                tempY += 2 * GameFrame.SIZE;
            }
            coordsEnemies.get(i).setLocation(tempX, tempY);
        }
    }

    public static void enemyAttack() {
        for (int i = 0; i < coordsEnemies.size(); i++) {
            if (Math.abs((int) coordsEnemies.get(i).getX() - playerX) < GameFrame.SIZE && Math.abs((int) coordsEnemies.get(i).getY() - playerY) < GameFrame.SIZE) {
                if (health > 0) {
                    health -= 2;
                    if (health <= 20) {
                        PanelManager.healthLabel.setForeground(Color.red);
                    } else {
                        PanelManager.healthLabel.setForeground(Color.white);
                    }
                    PanelManager.healthLabel.setText("Health: " + health);
                } else {
                    gameStatus = GameStatuses.END_GAME;
                    statusChanger();
                }
            }
        }
    }

    public static void checkCollisions() {
        for (int i = 0; i < coordsEnemies.size(); i++) {
            if (Math.abs(bulletX - (int) coordsEnemies.get(i).getX()) < GameFrame.SIZE && Math.abs(bulletY - (int) coordsEnemies.get(i).getY()) < GameFrame.SIZE) {
                healthsOfEnemies.set(i, healthsOfEnemies.get(i) - 1);
                bulletX = -GameFrame.SIZE;
                bulletY = -GameFrame.SIZE;

            }
            if (healthsOfEnemies.get(i) == 0) {
                score++;
                PanelManager.scoreLabel.setText("Score: " + score);
                healthsOfEnemies.remove(i);
                coordsEnemies.remove(i);

            }
            if (coordsEnemies.isEmpty()) {
                spawnEnemies();
            }
        }

    }

    public static void shootBullets() {
        isShooted = true;
        PanelManager.energyLabel.setForeground(Color.white);
        energy = 0;
        bulletX = playerX;
        bulletY = playerY;
        int tempX = playerX;
        int tempY = playerY;
        Timer renewEnergy = new Timer();
        renewEnergy.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isGamePaused) {
                    energy++;
                    PanelManager.energyLabel.setText("%" + energy);
                    if (energy == 100) {
                        PanelManager.energyLabel.setForeground(Color.green);
                        renewEnergy.cancel();
                    }
                }
            }
        }, 0, GameFrame.DELAY);

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isGamePaused) {
                    bulletX += (mouseX - tempX) / 20;
                    bulletY += (mouseY - tempY) / 20;

                    if (bulletX > GameFrame.GAME_WIDTH || bulletX < 0 || bulletY > GameFrame.GAME_HEIGHT || bulletY < 0) {
                        isShooted = false;
                        t.cancel();
                    }
                }
            }
        }, 0, GameFrame.DELAY);
    }

//    public static void specialAttack() {
//        int[] bulletsX = new int[4];
//        int[] bulletsY = new int[4];
//
//        for (int i = 0; i < 4; i++) {
//            bulletsX[i] = playerX;
//            bulletsY[i] = playerY;
//        }
//
//    }

}
