package test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyHandler implements KeyListener, MouseListener {

    private boolean isMovingUp = false;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    private boolean isMovingDown = false;
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                if (!GameManager.isGamePaused) {
                    isMovingLeft = true;
                }
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                if (!GameManager.isGamePaused) {
                    isMovingDown = true;
                }
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                if (!GameManager.isGamePaused) {
                    isMovingRight = true;
                }
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                if (!GameManager.isGamePaused) {
                    isMovingUp = true;
                }
                break;
            case KeyEvent.VK_SPACE:
                if (!GameManager.isGamePaused) {
                    if (GameManager.energy == 100) {
//                        GameManager.specialAttack();
                    }
                }
                break;
            case KeyEvent.VK_P:
            case KeyEvent.VK_ESCAPE:
                if (!GameManager.isGamePaused) {
                    GameManager.isGamePaused = true;
                    PanelManager.gamePausedLabel.setVisible(true);
                } else {
                    GameManager.isGamePaused = false;
                    PanelManager.gamePausedLabel.setVisible(false);
                }
                break;
            case KeyEvent.VK_H:
                PanelManager.healthLabel.setForeground(Color.white);
                GameManager.health = 100;
                PanelManager.healthLabel.setText("Health: " + GameManager.health);
                break;
            case KeyEvent.VK_E:
                GameManager.spawnEnemies();
                break;
        }

        if (isMovingRight && isMovingUp) {
            GameManager.playerX += 14;
            GameManager.playerY -= 14;
        } else if (isMovingLeft && isMovingUp) {
            GameManager.playerX -= 14;
            GameManager.playerY -= 14;
        } else if (isMovingRight && isMovingDown) {
            GameManager.playerX += 14;
            GameManager.playerY += 14;
        } else if (isMovingLeft && isMovingDown) {
            GameManager.playerX -= 14;
            GameManager.playerY += 14;
        } else {
            if (isMovingRight) {
                if (GameManager.playerX + 20 < GameFrame.GAME_WIDTH) {
                    GameManager.playerX += 20;
                }
            } else if (isMovingLeft) {
                if (GameManager.playerX - 20 > 0) {
                    GameManager.playerX -= 20;
                }
            } else if (isMovingUp) {
                if (GameManager.playerY - 20 > 0) {
                    GameManager.playerY -= 20;
                }
            } else if (isMovingDown) {
                if (GameManager.playerY + 20 < GameFrame.GAME_HEIGHT) {
                    GameManager.playerY += 20;
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                isMovingLeft = false;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                isMovingRight = false;
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                isMovingUp = false;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                isMovingDown = false;
                break;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!GameManager.isGamePaused) {
            if (GameManager.energy == 100) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    GameManager.mouseX = e.getX();
                    GameManager.mouseY = e.getY();
                    GameManager.shootBullets();
                }
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
