package com.example.tetrisjavafx;

import javafx.scene.paint.Color;
import java.util.Random;

public class Model {
    private final int width;
    private final int height;
    private final Color[][] board;
    private Piece currentPiece;
    private int pieceX, pieceY;
    private Piece nextPiece;
    public boolean gameOver = false;
    private int score = 0;
    private int level = 1;
    private long startTime;
    private long endTime = 0;
    private final Random random;

    // Array of shapes with colors
    private static final Piece[] PIECES = {
            new Piece(new char[][]{{'X', 'X'}, {'X', 'X'}}, Color.GOLDENROD),  // O
            new Piece(new char[][]{{'X', 'X', 'X', 'X'}}, Color.DARKCYAN),      // I
            new Piece(new char[][]{{' ', 'X', ' '}, {'X', 'X', 'X'}}, Color.DARKVIOLET),  // T
            new Piece(new char[][]{{'X', ' ', ' '}, {'X', 'X', 'X'}}, Color.DARKBLUE),    // J
            new Piece(new char[][]{{' ', ' ', 'X'}, {'X', 'X', 'X'}}, Color.DARKORANGE),  // L
            new Piece(new char[][]{{' ', 'X', 'X'}, {'X', 'X', ' '}}, Color.DARKGREEN),   // S
            new Piece(new char[][]{{'X', 'X', ' '}, {' ', 'X', 'X'}}, Color.DARKRED)      // Z
    };

    public Model(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new Color[height][width];
        this.random = new Random();

        resetBoard();
        nextPiece = generateRandomPiece();
        spawnPiece();
    }

    public static class Piece {
        public char[][] shape;
        public final Color color;

        public Piece(char[][] shape, Color color) {
            this.shape = shape;
            this.color = color;
        }

        public static Piece clonePiece(Piece original) {
            char[][] newShape = new char[original.shape.length][];
            for (int i = 0; i < original.shape.length; i++) {
                newShape[i] = original.shape[i].clone();
            }
            return new Piece(newShape, original.color);
        }
    }
    public static Piece clonePiece(Piece original) {
        char[][] newShape = new char[original.shape.length][];
        for (int i = 0; i < original.shape.length; i++) {
            newShape[i] = original.shape[i].clone();
        }
        return new Piece(newShape, original.color);
    }

    // Generate a random piece
    private Piece generateRandomPiece() {
        int pieceType = random.nextInt(7);
        return PIECES[pieceType];
    }

    public char[][] getNextPiece() {
        return nextPiece.shape;
    }
    public char[][] getCurrentPiece() {
        return currentPiece.shape;
    }

    public Color getCurrentPieceColor() {
        return currentPiece.color;
    }
    public Color getNextPieceColor() {
        return nextPiece.color;
    }

    public int getPieceX() {
        return pieceX;
    }
    public int getPieceY() {
        return pieceY;
    }

    public void spawnPiece() {
        currentPiece = Piece.clonePiece(nextPiece);
        pieceX = width / 2 - 1;
        pieceY = 0;
        nextPiece = generateRandomPiece();

        if (!canMove(pieceX, pieceY, currentPiece.shape)) {
            gameOver = true;
            stopTimer();
        }
    }
    private void placePiece() {
        for (int y = 0; y < currentPiece.shape.length; y++) {
            for (int x = 0; x < currentPiece.shape[y].length; x++) {
                if (currentPiece.shape[y][x] != ' ') {
                    board[pieceY + y][pieceX + x] = currentPiece.color;
                }
            }
        }
    }

    public void movePieceDown() {
        if (canMove(pieceX, pieceY + 1, currentPiece.shape)) {
            pieceY++;
        } else {
            placePiece();
            clearLines();
            spawnPiece();
        }
    }
    public void movePieceLeft() {
        if (canMove(pieceX - 1, pieceY, currentPiece.shape)) {
            pieceX--;
        }
    }
    public void movePieceRight() {
        if (canMove(pieceX + 1, pieceY, currentPiece.shape)) {
            pieceX++;
        }
    }
    public void rotatePiece() {
        char[][] rotatedPiece = new char[currentPiece.shape[0].length][currentPiece.shape.length];
        for (int y = 0; y < currentPiece.shape.length; y++) {
            for (int x = 0; x < currentPiece.shape[0].length; x++) {
                rotatedPiece[x][currentPiece.shape.length - 1 - y] = currentPiece.shape[y][x];
            }
        }
        if (canMove(pieceX, pieceY, rotatedPiece)) {
            currentPiece.shape = rotatedPiece;
        }
    }

    public boolean canMove(int newX, int newY, char[][] piece) {
        for (int y = 0; y < piece.length; y++) {
            for (int x = 0; x < piece[y].length; x++) {
                if (piece[y][x] != ' ') {
                    int boardX = newX + x;
                    int boardY = newY + y;

                    if (boardX < 0 || boardX >= width || boardY >= height) {
                        return false;
                    }
                    if (boardY >= 0 && board[boardY][boardX] != Color.BLACK) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    private void clearLines() {
        for (int y = 0; y < height; y++) {
            boolean fullLine = true;
            for (int x = 0; x < width; x++) {
                if (board[y][x] == Color.BLACK) {
                    fullLine = false;
                    break;
                }
            }
            if (fullLine) {
                removeLine(y);
                addScore(100);
            }
        }
    }

    private void removeLine(int line) {
        for (int y = line; y > 0; y--) {
            System.arraycopy(board[y - 1], 0, board[y], 0, width);
        }
        for (int x = 0; x < width; x++) {
            board[0][x] = Color.BLACK;
        }
    }

    public Color[][] getBoard() {
        return board;
    }

    public void resetBoard() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                board[y][x] = Color.BLACK;
            }
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }
    public void addScore(int points) {
        score += points;
        updateLevel();
    }
    public void resetScore() {
        score = 0;
        level = 1;
    }

    public int getLevel() {
        return level;
    }
    private void updateLevel() {
        level = (score / 500) + 1;
    }

    public String getTime() {
        long elapsedTime = gameOver ? endTime - startTime : System.currentTimeMillis() - startTime;

        long totalSeconds = elapsedTime / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;

        return String.format("%02d/%02d", minutes, seconds);
    }
    public void startTimer() {
        startTime = System.currentTimeMillis();
        endTime = 0;
    }
    public void stopTimer() {
        if (endTime == 0) {
            endTime = System.currentTimeMillis();
        }
    }
}
