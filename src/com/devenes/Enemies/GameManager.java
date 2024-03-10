package com.devenes.Enemies;

import java.awt.*;
import java.util.*;

public class GameManager {
    public static int playerX = 600;
    public static int playerY = 300;
    public static int gameStage = 1;
    static int roundCounter = 0;
    static final int ROUND_FOR_EACH_STAGE = 3;
    static final int ENEMY_HEALTH = 3;
    static final int ENEMY_WALKING_DISTANCE = 3;
    static final int ENEMY_ATTACK_DAMAGE = 2;
    static final int PLAYER_WALKING_DISTANCE = 8;
    static final int CROSS_WALKING_DISTANCE = (int) Math.sqrt(((double) (PLAYER_WALKING_DISTANCE * PLAYER_WALKING_DISTANCE) / 2));
    static int mouseX, mouseY, bulletX, bulletY, enemyX, enemyY, score = 0;
    static int bulletDamage = 1;
    static int specialAttackDamage = 3;
    static boolean canSpecialAttack = true;
    static Point leftBullet = new Point();
    static Point rightBullet = new Point();
    static Point downBullet = new Point();
    static Point upBullet = new Point();
    static boolean isShooted = false;
    static boolean specialAttacked = false;
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
            case 1 -> 4;
            case 2 -> 8;
            case 3 -> 12;
            case 4 -> 15;
            default -> 0;
        };
    }


    public static void statusChanger() {
        switch (gameStatus) {
            case PRE_GAME:
            case IN_GAME:
                break;
            case END_GAME:
                PanelManager.gameFrame.clearFrame();
                PanelManager.gameFrame.cancelTimers();
                PanelManager.gameFrame.add(PanelManager.endPanel());
                break;
        }

    }

    public static void restartGame() {
        resetGame();
        GameFrame.stopTimers = false;
        GameManager.spawnEnemies();
    }

    public static void resetGame() {
        playerX = 600;
        playerY = 300;
        gameStage = 1;
        roundCounter = 0;
        score = 0;
        isShooted = false;
        canSpecialAttack = true;
        specialAttacked = false;
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
        if (roundCounter < ROUND_FOR_EACH_STAGE) {
            roundCounter++;
        } else {
            gameStage++;
            roundCounter = 0;
        }
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
        int tempX;
        int tempY;
        for (int i = 0; i < coordsEnemies.size(); i++) {
            if ((int) coordsEnemies.get(i).getY() > playerY) {
                if (Math.abs(coordsEnemies.get(i).getY() - playerY) < ENEMY_WALKING_DISTANCE) {
                    tempY = (int) coordsEnemies.get(i).getY() - ((int) coordsEnemies.get(i).getY() - playerY);
                } else {
                    tempY = (int) coordsEnemies.get(i).getY() - ENEMY_WALKING_DISTANCE;
                }
            } else {
                if (Math.abs(coordsEnemies.get(i).getY() - playerY) < ENEMY_WALKING_DISTANCE) {
                    tempY = (int) coordsEnemies.get(i).getY() + (playerY - (int) coordsEnemies.get(i).getY());
                } else {
                    tempY = (int) coordsEnemies.get(i).getY() + ENEMY_WALKING_DISTANCE;
                }
            }
            if (coordsEnemies.get(i).getX() > playerX) {
                if (Math.abs(coordsEnemies.get(i).getX() - playerX) < ENEMY_WALKING_DISTANCE) {
                    tempX = (int) coordsEnemies.get(i).getX() - ((int) coordsEnemies.get(i).getX() - playerX);
                } else {
                    tempX = (int) coordsEnemies.get(i).getX() - ENEMY_WALKING_DISTANCE;
                }
            } else {
                if (Math.abs(coordsEnemies.get(i).getX() - playerX) < ENEMY_WALKING_DISTANCE) {
                    tempX = (int) coordsEnemies.get(i).getX() + (playerX - (int) coordsEnemies.get(i).getX());
                } else {
                    tempX = (int) coordsEnemies.get(i).getX() + ENEMY_WALKING_DISTANCE;
                }
            }
            coordsEnemies.get(i).setLocation(tempX, tempY);
        }
    }

    public static void enemyAttack() {
        for (int i = 0; i < coordsEnemies.size(); i++) {
            if (Math.abs((int) coordsEnemies.get(i).getX() - playerX) < GameFrame.SIZE && Math.abs((int) coordsEnemies.get(i).getY() - playerY) < GameFrame.SIZE) {
                if (health > 0) {
                    health -= ENEMY_ATTACK_DAMAGE;
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
                healthsOfEnemies.set(i, healthsOfEnemies.get(i) - bulletDamage);
                bulletX = -GameFrame.SIZE;
                bulletY = -GameFrame.SIZE;

            }
            if (Math.abs(leftBullet.getX() - (int) coordsEnemies.get(i).getX()) < GameFrame.SIZE && Math.abs(leftBullet.getY() - (int) coordsEnemies.get(i).getY()) < GameFrame.SIZE) {
                healthsOfEnemies.set(i, healthsOfEnemies.get(i) - specialAttackDamage);
                leftBullet.setLocation(-GameFrame.SIZE, -GameFrame.SIZE);

            }
            if (Math.abs(rightBullet.getX() - (int) coordsEnemies.get(i).getX()) < GameFrame.SIZE && Math.abs(rightBullet.getY() - (int) coordsEnemies.get(i).getY()) < GameFrame.SIZE) {
                healthsOfEnemies.set(i, healthsOfEnemies.get(i) - specialAttackDamage);
                rightBullet.setLocation(-GameFrame.SIZE, -GameFrame.SIZE);

            }
            if (Math.abs(upBullet.getX() - (int) coordsEnemies.get(i).getX()) < GameFrame.SIZE && Math.abs(upBullet.getY() - (int) coordsEnemies.get(i).getY()) < GameFrame.SIZE) {
                healthsOfEnemies.set(i, healthsOfEnemies.get(i) - specialAttackDamage);
                upBullet.setLocation(-GameFrame.SIZE, -GameFrame.SIZE);

            }
            if (Math.abs(downBullet.getX() - (int) coordsEnemies.get(i).getX()) < GameFrame.SIZE && Math.abs(downBullet.getY() - (int) coordsEnemies.get(i).getY()) < GameFrame.SIZE) {
                healthsOfEnemies.set(i, healthsOfEnemies.get(i) - specialAttackDamage);
                downBullet.setLocation(-GameFrame.SIZE, -GameFrame.SIZE);
            }

            if (healthsOfEnemies.get(i) <= 0) {
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
    public static void movePlayer() {
        if (KeyHandler.isMovingRight && KeyHandler.isMovingUp) {
            if (playerX + CROSS_WALKING_DISTANCE < GameFrame.GAME_WIDTH && playerY - CROSS_WALKING_DISTANCE > 0) {
                playerX += CROSS_WALKING_DISTANCE;
                playerY -= CROSS_WALKING_DISTANCE;
            }
        } else if (KeyHandler.isMovingLeft && KeyHandler.isMovingUp) {
            if (playerX - CROSS_WALKING_DISTANCE > 0 && playerY - CROSS_WALKING_DISTANCE > 0) {
                playerX -= CROSS_WALKING_DISTANCE;
                playerY -= CROSS_WALKING_DISTANCE;
            }
        } else if (KeyHandler.isMovingRight && KeyHandler.isMovingDown) {
            if (playerX + CROSS_WALKING_DISTANCE < GameFrame.GAME_WIDTH && playerY + CROSS_WALKING_DISTANCE < GameFrame.GAME_HEIGHT) {
                playerX += CROSS_WALKING_DISTANCE;
                playerY += CROSS_WALKING_DISTANCE;
            }
        } else if (KeyHandler.isMovingLeft && KeyHandler.isMovingDown) {
            if (playerX - CROSS_WALKING_DISTANCE > 0 && playerY + CROSS_WALKING_DISTANCE < GameFrame.GAME_HEIGHT) {
                playerX -= CROSS_WALKING_DISTANCE;
                playerY += CROSS_WALKING_DISTANCE;
            }
        } else if (KeyHandler.isMovingRight) {
            if (playerX + PLAYER_WALKING_DISTANCE < GameFrame.GAME_WIDTH) {
                playerX += PLAYER_WALKING_DISTANCE;
            }
        } else if (KeyHandler.isMovingLeft) {
            if (playerX - PLAYER_WALKING_DISTANCE > 0) {
                playerX -= PLAYER_WALKING_DISTANCE;
            }
        } else if (KeyHandler.isMovingUp) {
            if (playerY - PLAYER_WALKING_DISTANCE > 0) {
                playerY -= PLAYER_WALKING_DISTANCE;
            }
        } else if (KeyHandler.isMovingDown) {
            if (playerY + PLAYER_WALKING_DISTANCE < GameFrame.GAME_HEIGHT) {
                playerY += PLAYER_WALKING_DISTANCE;
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
                if (!GameFrame.stopTimers) {
                    if (!isGamePaused) {
                        if (energy + 1 <= 100) {
                            energy++;
                        }
                        PanelManager.energyLabel.setText("%" + energy);
                        if (energy == 100) {
                            PanelManager.energyLabel.setForeground(Color.green);
                            renewEnergy.cancel();
                        }
                    }
                }
            }
        }, 0, GameFrame.DELAY);

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!GameFrame.stopTimers) {
                    if (!isGamePaused) {
                        bulletX += (mouseX - tempX) / 20;
                        bulletY += (mouseY - tempY) / 20;

                        if (bulletX > GameFrame.GAME_WIDTH || bulletX < 0 || bulletY > GameFrame.GAME_HEIGHT || bulletY < 0) {
                            isShooted = false;
                            t.cancel();
                        }
                    }
                }
            }
        }, 0, GameFrame.DELAY);
    }

    public static void specialAttack() {
        if (canSpecialAttack) {
            canSpecialAttack = false;
            specialAttacked = true;
            leftBullet = new Point(playerX, playerY);
            rightBullet = new Point(playerX, playerY);
            upBullet = new Point(playerX, playerY);
            downBullet = new Point(playerX, playerY);

            Timer doSpecialAttack = new Timer();
            doSpecialAttack.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (leftBullet.getX() > 0) {
                        leftBullet.setLocation(leftBullet.getX() - 15, leftBullet.getY());
                    }
                    if (rightBullet.getX() < GameFrame.GAME_WIDTH) {
                        rightBullet.setLocation(rightBullet.getX() + 15, rightBullet.getY());
                    }
                    if (upBullet.getY() > 0) {
                        upBullet.setLocation(upBullet.getX(), upBullet.getY() - 15);
                    }
                    if (downBullet.getY() < GameFrame.GAME_HEIGHT) {
                        downBullet.setLocation(downBullet.getX(), downBullet.getY() + 15);
                    }
                    if (leftBullet.getX() < -GameFrame.SIZE && rightBullet.getX() >= GameFrame.GAME_WIDTH && upBullet.getY() <= 0 && downBullet.getY() >= GameFrame.GAME_HEIGHT) {
                        specialAttacked = false;
                        doSpecialAttack.cancel();
                    }
                }
            }, 0, 17);

            Timer renewEnergy = new Timer();
            renewEnergy.schedule(new TimerTask() {
                @Override
                public void run() {
                    canSpecialAttack = true;
                }
            }, 10000);
        }

    }

    public static void rightClickAttack() {
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
                if (!GameFrame.stopTimers) {
                    if (!isGamePaused) {
                        if (energy + 1 <= 100) {
                            energy++;
                        }
                        PanelManager.energyLabel.setText("%" + energy);
                        if (energy == 100) {
                            PanelManager.energyLabel.setForeground(Color.green);
                            renewEnergy.cancel();
                        }
                    }
                }
            }
        }, 0, GameFrame.DELAY);

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!GameFrame.stopTimers) {
                    if (!isGamePaused) {
                        bulletX += (mouseX - tempX) / 20;
                        bulletY += (mouseY - tempY) / 20;

                        if (bulletX > GameFrame.GAME_WIDTH || bulletX < 0 || bulletY > GameFrame.GAME_HEIGHT || bulletY < 0) {
                            isShooted = false;
                            t.cancel();
                        }
                    }
                }
            }
        }, 0, GameFrame.DELAY);


    }
}
