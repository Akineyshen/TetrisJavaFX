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
            return; // Игра окончена, дальнейшие обновления не требуются
        }
        model.movePieceDown();
    }

    public void render() {
        if (model.isGameOver()) {
            view.renderGameOver();
        } else {
            view.renderBoard(model.getBoardColors(), model.getCurrentPiece(), model.getPieceX(), model.getPieceY(), model.getCurrentPieceColor());
            view.clearSidebar(); // Очищаем боковую панель
            view.renderNextPiece(model.getNextPiece(), model.getNextPieceColor());
            view.renderScore(model.getScore());
            view.renderTimer(model.getTime());
        }
    }


    public void resetGame() {
        model.resetBoard(); // Очистить игровое поле
        model.spawnPiece(); // Создать новую фигуру
        model.resetScore();
        model.startTimer();
        model.gameOver = false; // Сбросить флаг окончания игры
        render(); // Обновить отображение
    }

    public void handleKeyPress(KeyEvent event) {
        if (model.isGameOver()) {
            // Если игра окончена, проверяем только нажатие SPACE для рестарта
            if (event.getCode() == KeyCode.SPACE) {
                resetGame();
            }
            return;
        }

        // Обработка ввода во время игры
        switch (event.getCode()) {
            case LEFT -> moveLeft();
            case RIGHT -> moveRight();
            case DOWN -> moveDown();
            case UP -> rotate();
        }
    }


}
