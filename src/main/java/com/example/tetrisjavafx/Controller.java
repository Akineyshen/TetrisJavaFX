package com.example.tetrisjavafx;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller {
    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void moveLeft() {
        model.movePieceLeft();
    }
    public void moveRight() {
        model.movePieceRight();
    }
    public void moveDown() {
        model.movePieceDown();
    }
    public void rotate() {
        model.rotatePiece();
    }

    public void update() {
        if (model.isGameOver()) {
            return;
        }
        model.movePieceDown();
    }
    public void render() {
        if (model.isGameOver()) {
            view.renderGameOver();
        } else {
            view.renderBoard(model.getBoard(), model.getCurrentPiece(), model.getPieceX(), model.getPieceY(), model.getCurrentPieceColor());
            view.clearSidebar();
            view.renderNextPiece(model.getNextPiece(), model.getNextPieceColor());
            view.renderScore(model.getScore());
            view.renderLevel(model.getLevel());
            view.renderTimer(model.getTime());
        }
    }


    public void resetGame() {
        model.resetBoard();
        model.spawnPiece();
        model.resetScore();
        model.startTimer();
        model.gameOver = false;
        render();
    }
    public void handleKeyPress(KeyEvent event) {
        if (model.isGameOver()) {
            if (event.getCode() == KeyCode.SPACE) {
                resetGame();
            }
            return;
        }

        switch (event.getCode()) {
            case LEFT -> moveLeft();
            case RIGHT -> moveRight();
            case DOWN -> moveDown();
            case UP -> rotate();
        }
    }
}
