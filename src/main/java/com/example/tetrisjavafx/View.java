package com.example.tetrisjavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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

    public void renderBoard(Color[][] boardColors, char[][] piece, int pieceX, int pieceY, Color pieceColor) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, (width + 5) * cellSize, (height + 2) * cellSize);

        for (int y = 0; y < height + 2; y++) {
            for (int x = 0; x < width + 2; x++) {
                if (x == 0 || x == width + 1 || y == 0 || y == height + 1) {
                    drawCell(x, y, Color.DARKGRAY);
                }
            }
        }

        for (int y = 0; y < boardColors.length; y++) {
            for (int x = 0; x < boardColors[y].length; x++) {
                if (boardColors[y][x] != Color.BLACK) {
                    drawCell(x + 1, y + 1, boardColors[y][x]);
                }
            }
        }

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1);
        for (int i = 0; i <= width; i++) {
            gc.strokeLine(i * cellSize, 0, i * cellSize, (height + 1) * cellSize);
        }
        for (int i = 0; i <= height; i++) {
            gc.strokeLine(0, i * cellSize, (width + 1) * cellSize, i * cellSize);
        }

        for (int y = 0; y < piece.length; y++) {
            for (int x = 0; x < piece[y].length; x++) {
                if (piece[y][x] != ' ') {
                    drawCell(pieceX + x + 1, pieceY + y + 1, pieceColor);
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
        Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/font/Pixel Emulator.otf"), 25);
        gc.setFont(pixelFont);
        gc.setFill(Color.RED);
        gc.fillText("GAME OVER", (width + 2) * cellSize / 2, (height + 2) * cellSize / 2 - 20);
        gc.setFill(Color.WHITE);
        gc.fillText("Press SPACE to Restart", (width + 2) * cellSize / 2 - 115, (height + 2) * cellSize / 2 + 20);
    }

    public void renderNextPiece(char[][] nextPiece, Color nextPieceColor) {
        gc.setFill(Color.WHITE);
        gc.fillText("Next", (width + 3) * cellSize, 2 * cellSize);

        for (int y = 0; y < nextPiece.length; y++) {
            for (int x = 0; x < nextPiece[y].length; x++) {
                if (nextPiece[y][x] != ' ') {
                    drawCell(width + 3 + x, 3 + y, nextPieceColor);
                }
            }
        }
    }
    public void renderScore(int score) {
        gc.setFill(Color.WHITE);
        Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/font/Pixel Emulator.otf"), 20);
        gc.setFont(pixelFont);
        gc.fillText("Score", (width + 3) * cellSize, 6 * cellSize + 5);

        gc.fillText(String.valueOf(score), (width + 3) * cellSize, 7 * cellSize - 4);
    }
    public void renderLevel(int level) {
        gc.setFill(Color.WHITE);
        Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/font/Pixel Emulator.otf"), 20);
        gc.setFont(pixelFont);
        gc.fillText("Level", (width + 3) * cellSize, 8 * cellSize + 5);

        gc.fillText(String.valueOf(level), (width + 3) * cellSize, 9 * cellSize - 4);
    }
    public void renderTimer(String time) {
        gc.setFill(Color.WHITE);
        Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/font/Pixel Emulator.otf"), 20);
        gc.setFont(pixelFont);
        gc.fillText("Time", (width + 3) * cellSize, 10 * cellSize + 10);
        gc.fillText(time, (width + 3) * cellSize, 11 * cellSize + 10);
    }

    public void clearSidebar() {
        gc.setFill(Color.BLACK);
        gc.fillRect((width + 2) * cellSize, 0, 6 * cellSize, (height + 2) * cellSize);
    }
}
