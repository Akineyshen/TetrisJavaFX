package com.example.tetrisjavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class View {
    private final GraphicsContext gc;
    private final int cellSize;
    private final int width;
    private final int height;

    public View(GraphicsContext gc, int cellSize, int width, int height) {
        this.gc = gc;
        this.cellSize = cellSize;
        this.width = width;
        this.height = height;
    }

    public void renderBoard(char[][] board, char[][] piece, int pieceX, int pieceY) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, (width + 5) * cellSize, (height + 2) * cellSize);

        // Рисуем серую рамку по периметру
        for (int y = 0; y < height + 2; y++) {
            for (int x = 0; x < width + 2; x++) {
                if (x == 0 || x == width + 1 || y == 0 || y == height + 1) {
                    drawCell(x, y, Color.DARKGRAY);
                }
            }
        }

        // Рисуем игровое поле
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x] != ' ') {
                    drawCell(x + 1, y + 1, Color.GRAY); // Смещение на 1 клетку из-за рамки
                }
            }
        }

        // Рисуем линии сетки
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1);
        for (int i = 0; i <= width; i++) {
            gc.strokeLine(i * cellSize, 0, i * cellSize, (height + 1) * cellSize); // Вертикальные линии
        }
        for (int i = 0; i <= height; i++) {
            gc.strokeLine(0, i * cellSize, (width + 1) * cellSize, i * cellSize); // Горизонтальные линии
        }

        // Рисуем текущую фигуру
        for (int y = 0; y < piece.length; y++) {
            for (int x = 0; x < piece[y].length; x++) {
                if (piece[y][x] != ' ') {
                    drawCell(pieceX + x + 1, pieceY + y + 1, Color.BLUE); // Смещение на 1 клетку
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
        Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/font/ARCADECLASSIC.TTF"), 40);
        gc.setFont(pixelFont);
        gc.setFill(Color.RED);
        gc.fillText("GAME OVER", (width + 2) * cellSize / 2 - 15, (height + 2) * cellSize / 2 - 20);
        gc.setFill(Color.WHITE);
        gc.fillText("Press SPACE to Restart", (width + 2) * cellSize / 2 - 125, (height + 2) * cellSize / 2 + 20);
    }

    public void renderNextPiece(char[][] nextPiece) {
        gc.setFill(Color.WHITE);
        gc.fillText("Next", (width + 3) * cellSize, 2 * cellSize); // Отображаем текст "Next:"

        for (int y = 0; y < nextPiece.length; y++) {
            for (int x = 0; x < nextPiece[y].length; x++) {
                if (nextPiece[y][x] != ' ') {
                    drawCell(width + 3 + x, 3 + y, Color.WHITE); // Рисуем фигуру справа от поля
                }
            }
        }
    }

    public void renderScore(int score) {
        gc.setFill(Color.WHITE);
        Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/font/ARCADECLASSIC.TTF"), 20);
        gc.setFont(pixelFont);
        gc.fillText("\nScore", (width + 3) * cellSize, 7 * cellSize); // Заголовок "Score"

        gc.fillText(String.valueOf(score), (width + 3) * cellSize, 8 * cellSize + 10); // Текущее значение очков
    }

    public void clearSidebar() {
        // Очистка области справа от игрового поля
        gc.setFill(Color.BLACK);
        gc.fillRect((width + 2) * cellSize, 0, 6 * cellSize, (height + 2) * cellSize);
    }


}
