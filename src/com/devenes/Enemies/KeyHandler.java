package com.devenes.Enemies;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyHandler implements KeyListener, MouseListener {

    static boolean isMovingUp = false;
    static boolean isMovingLeft = false;
    static boolean isMovingRight = false;
    static boolean isMovingDown = false;
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
                    GameManager.specialAttack();
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
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (GameManager.energy == 100) {
                    GameManager.mouseX = e.getX();
                    GameManager.mouseY = e.getY();
                    GameManager.shootBullets();
                }
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                GameManager.mouseX = e.getX();
                GameManager.mouseY = e.getY();
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
