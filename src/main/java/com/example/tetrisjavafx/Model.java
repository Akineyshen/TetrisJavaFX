package com.example.tetrisjavafx;

import java.util.Random;
import java.util.Timer;

public class Model {
    private final int width;
    private final int height;
    private final char[][] board;
    public boolean gameOver = false;
    private char[][] currentPiece;
    private int pieceX, pieceY;
    private int score = 0;
    private char[][] nextPiece;
    private long startTime;
    private long endTime = 0;
    private final Random random;

    public Model(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new char[height][width];
        this.random = new Random();

        resetBoard();
        nextPiece = generateRandomPiece();
        spawnPiece();

    }

    public void resetBoard() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                board[y][x] = ' ';
            }
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
    }


    private void addScore(int points) {
        score += points;
    }

    public char[][] getNextPiece() {
        return nextPiece;
    }


    public void spawnPiece() {
        currentPiece = nextPiece; // Следующая фигура становится текущей
        nextPiece = generateRandomPiece(); // Генерируем новую следующую фигуру
        pieceX = width / 2 - currentPiece[0].length / 2;
        pieceY = 0;

        // Если фигура не может появиться, игра заканчивается
        if (!canMove(pieceX, pieceY, currentPiece)) {
            gameOver = true;
            stopTimer();
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public char[][] getCurrentPiece() {
        return currentPiece;
    }

    public int getPieceX() {
        return pieceX;
    }

    public int getPieceY() {
        return pieceY;
    }

    public void movePieceDown() {
        if (canMove(pieceX, pieceY + 1, currentPiece)) {
            pieceY++;
        } else {
            placePiece(); // Устанавливаем фигуру на поле
            clearLines(); // Очищаем заполненные линии
            spawnPiece(); // Создаём новую фигуру
        }
    }

    public void movePieceLeft() {
        if (canMove(pieceX - 1, pieceY, currentPiece)) {
            pieceX--;
        }
    }

    public void movePieceRight() {
        if (canMove(pieceX + 1, pieceY, currentPiece)) {
            pieceX++;
        }
    }


    private char[][] generateRandomPiece() {
        switch (random.nextInt(7)) { // Всего 7 фигур
            case 0: // O-образная фигура (Куб)
                return new char[][]{
                        {'X', 'X'},
                        {'X', 'X'}
                };
            case 1: // I-образная фигура
                return new char[][]{
                        {'X', 'X', 'X', 'X'}
                };
            case 2: // T-образная фигура
                return new char[][]{
                        {' ', 'X', ' '},
                        {'X', 'X', 'X'}
                };
            case 3: // J-образная фигура
                return new char[][]{
                        {'X', ' ', ' '},
                        {'X', 'X', 'X'}
                };
            case 4: // L-образная фигура
                return new char[][]{
                        {' ', ' ', 'X'},
                        {'X', 'X', 'X'}
                };
            case 5: // S-образная фигура
                return new char[][]{
                        {' ', 'X', 'X'},
                        {'X', 'X', ' '}
                };
            case 6: // Z-образная фигура
                return new char[][]{
                        {'X', 'X', ' '},
                        {' ', 'X', 'X'}
                };
            default:
                throw new IllegalStateException("Unexpected value");
        }
    }



    // Проверяет, может ли фигура переместиться в заданные координаты
    private boolean canMove(int newX, int newY, char[][] piece) {
        for (int y = 0; y < piece.length; y++) {
            for (int x = 0; x < piece[y].length; x++) {
                if (piece[y][x] != ' ') {
                    int boardX = newX + x;
                    int boardY = newY + y;

                    // Проверяем выход за границы поля
                    if (boardX < 0 || boardX >= width || boardY >= height) {
                        return false;
                    }
                    // Проверяем наложение на уже занятые клетки
                    if (boardY >= 0 && board[boardY][boardX] != ' ') {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    // Устанавливает текущую фигуру на игровом поле
    private void placePiece() {
        for (int y = 0; y < currentPiece.length; y++) {
            for (int x = 0; x < currentPiece[y].length; x++) {
                if (currentPiece[y][x] != ' ') {
                    board[pieceY + y][pieceX + x] = currentPiece[y][x];
                }
            }
        }
    }

    // Очищает заполненные линии
    private void clearLines() {
        for (int y = 0; y < height; y++) {
            boolean fullLine = true;
            for (int x = 0; x < width; x++) {
                if (board[y][x] == ' ') {
                    fullLine = false;
                    break;
                }
            }
            if (fullLine) {
                removeLine(y);
                addScore(100); // Увеличиваем счёт за каждую очищенную линию
            }
        }
    }


    // Сдвигает все строки выше вниз
    private void removeLine(int line) {
        for (int y = line; y > 0; y--) {
            System.arraycopy(board[y - 1], 0, board[y], 0, width);
        }
        // Очищаем верхнюю строку
        for (int x = 0; x < width; x++) {
            board[0][x] = ' ';
        }
    }

    public void rotatePiece() {
        char[][] rotatedPiece = new char[currentPiece[0].length][currentPiece.length];
        for (int y = 0; y < currentPiece.length; y++) {
            for (int x = 0; x < currentPiece[0].length; x++) {
                rotatedPiece[x][currentPiece.length - 1 - y] = currentPiece[y][x];
            }
        }
        if (canMove(pieceX, pieceY, rotatedPiece)) {
            currentPiece = rotatedPiece;
        }
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

    public String getTime() {

        long elapsedTime = gameOver ? endTime - startTime : System.currentTimeMillis() - startTime;

        long totalSeconds = elapsedTime / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;

        return String.format("%02d/%02d", minutes, seconds);
    }

}
