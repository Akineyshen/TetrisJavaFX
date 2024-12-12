package com.example.tetrisjavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class View {
    private final GraphicsContext gc;
    private final int cellSize;
    private final int width;
    private final int height;
    private final Color[] pieceColors = {
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.ORANGE,
            Color.PURPLE,
            Color.CYAN,
    };

    public View(GraphicsContext gc, int cellSize, int width, int height) {
        this.gc = gc;
        this.cellSize = cellSize;
        this.width = width;
        this.height = height;
    }

    public void renderBoard(char[][] board, char[][] piece, int pieceX, int pieceY) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width * cellSize, height * cellSize);

        // Рисуем игровое поле
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x] != ' ') {
                    drawCell(x, y, Color.GRAY);
                }
            }
        }

        // Рисуем текущую фигуру
        for (int y = 0; y < piece.length; y++) {
            for (int x = 0; x < piece[y].length; x++) {
                if (piece[y][x] != ' ') {
                    drawCell(pieceX + x, pieceY + y, Color.BLUE);
                }
            }
        }
    }

    private void drawCell(int x, int y, Color color) {
        gc.setFill(color);
        gc.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x * cellSize, y * cellSize, cellSize, cellSize);
    }

    public void renderGameOver() {
        gc.setFill(Color.RED);
        gc.fillText("GAME OVER", width * cellSize / 2 - 50, height * cellSize / 2 - 20);
        gc.setFill(Color.WHITE);
        gc.fillText("Press SPACE to Restart", width * cellSize / 2 - 70, height * cellSize / 2 + 20);
    }

    public void renderNextPiece(char[][] nextPiece) {
        gc.setFill(Color.RED);
        gc.fillText("Next:", width * cellSize + 20, 30); // Отображаем текст "Next:"

        for (int y = 0; y < nextPiece.length; y++) {
            for (int x = 0; x < nextPiece[y].length; x++) {
                if (nextPiece[y][x] != ' ') {
                    drawCell((width + 1) + x, y + 2, Color.RED); // Рисуем фигуру справа от поля
                }
            }
        }
    }

    public void renderScore(int score) {
        gc.setFill(Color.RED);
        gc.fillText("Score:", width * cellSize + 20, 150); // Заголовок "Score"
        gc.fillText(String.valueOf(score), width * cellSize + 20, 180); // Текущее значение очков
    }

    public void clearSidebar() {
        // Очистка области справа от игрового поля
        gc.setFill(Color.WHITE);
        gc.fillRect(width * cellSize, 0, 6 * cellSize, height * cellSize);
    }


}
