package com.example.tetrisjavafx;

import javafx.scene.paint.Color;

import java.util.Random;

public class Model {
    private final int width;
    private final int height;
    private final Color[][] boardColors; // Массив цветов для ячеек
    public boolean gameOver = false;
    private Piece currentPiece; // Текущая фигура
    private int pieceX, pieceY;
    private int score = 0;
    private Piece nextPiece; // Следующая фигура
    private long startTime;
    private long endTime = 0;
    private final Random random;

    // Массив фигур с цветами
    private static final Piece[] PIECES = {
            new Piece(new char[][]{{'X', 'X'}, {'X', 'X'}}, Color.YELLOW),  // O (Квадрат)
            new Piece(new char[][]{{'X', 'X', 'X', 'X'}}, Color.DARKCYAN),      // I (Линия)
            new Piece(new char[][]{{' ', 'X', ' '}, {'X', 'X', 'X'}}, Color.PURPLE),  // T
            new Piece(new char[][]{{'X', ' ', ' '}, {'X', 'X', 'X'}}, Color.DARKBLUE),    // J
            new Piece(new char[][]{{' ', ' ', 'X'}, {'X', 'X', 'X'}}, Color.DARKORANGE),  // L
            new Piece(new char[][]{{' ', 'X', 'X'}, {'X', 'X', ' '}}, Color.DARKGREEN),   // S
            new Piece(new char[][]{{'X', 'X', ' '}, {' ', 'X', 'X'}}, Color.DARKRED)      // Z
    };

    // Объект фигуры (включает форму и цвет)
    public static class Piece {
        public char[][] shape;
        public final Color color;

        public Piece(char[][] shape, Color color) {
            this.shape = shape;
            this.color = color;
        }

        // Метод для клонирования фигуры
        public static Piece clonePiece(Piece original) {
            char[][] newShape = new char[original.shape.length][];
            for (int i = 0; i < original.shape.length; i++) {
                newShape[i] = original.shape[i].clone();
            }
            return new Piece(newShape, original.color);
        }
    }

    public Model(int width, int height) {
        this.width = width;
        this.height = height;
        this.boardColors = new Color[height][width]; // Массив для хранения цветов ячеек
        this.random = new Random();

        resetBoard();  // Инициализация поля
        nextPiece = generateRandomPiece();  // Генерация следующей фигуры
        spawnPiece(); // Спавн первой фигуры
    }

    // Сброс игрового поля
    public void resetBoard() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boardColors[y][x] = Color.BLACK; // Изначально все клетки черные
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
        return nextPiece.shape;
    }

    // Генерация случайной фигуры с фиксированным цветом
    private Piece generateRandomPiece() {
        int pieceType = random.nextInt(7);  // Случайный выбор фигуры (0-6)
        return PIECES[pieceType];
    }

    public void spawnPiece() {
        currentPiece = Piece.clonePiece(nextPiece); // Следующая фигура становится текущей
        pieceX = width / 2 - 1;
        pieceY = 0;
        nextPiece = generateRandomPiece(); // Генерация новой следующей фигуры

        // Если фигура не может появиться, игра заканчивается
        if (!canMove(pieceX, pieceY, currentPiece.shape)) {
            gameOver = true;
            stopTimer();
        }
    }

    public Color[][] getBoardColors() {
        return boardColors; // Возвращаем массив с цветами для отображения в View
    }

    public char[][] getCurrentPiece() {
        return currentPiece.shape;
    }

    public int getPieceX() {
        return pieceX;
    }

    public int getPieceY() {
        return pieceY;
    }

    // Перемещение фигуры вниз
    public void movePieceDown() {
        if (canMove(pieceX, pieceY + 1, currentPiece.shape)) {
            pieceY++;
        } else {
            placePiece(); // Устанавливаем фигуру на поле
            clearLines(); // Очищаем заполненные линии
            spawnPiece(); // Спавн новой фигуры
        }
    }

    // Перемещение фигуры влево
    public void movePieceLeft() {
        if (canMove(pieceX - 1, pieceY, currentPiece.shape)) {
            pieceX--;
        }
    }

    // Перемещение фигуры вправо
    public void movePieceRight() {
        if (canMove(pieceX + 1, pieceY, currentPiece.shape)) {
            pieceX++;
        }
    }

    // Проверка, можно ли переместить фигуру в заданные координаты
    private boolean canMove(int newX, int newY, char[][] piece) {
        for (int y = 0; y < piece.length; y++) {
            for (int x = 0; x < piece[y].length; x++) {
                if (piece[y][x] != ' ') {
                    int boardX = newX + x;
                    int boardY = newY + y;

                    // Проверка выхода за границы поля
                    if (boardX < 0 || boardX >= width || boardY >= height) {
                        return false;
                    }
                    // Проверка на наложение на другие фигуры
                    if (boardY >= 0 && boardColors[boardY][boardX] != Color.BLACK) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Установка текущей фигуры на игровом поле
    private void placePiece() {
        for (int y = 0; y < currentPiece.shape.length; y++) {
            for (int x = 0; x < currentPiece.shape[y].length; x++) {
                if (currentPiece.shape[y][x] != ' ') {
                    boardColors[pieceY + y][pieceX + x] = currentPiece.color; // Сохраняем цвет фигуры
                }
            }
        }
    }

    // Очищает заполненные линии
    private void clearLines() {
        for (int y = 0; y < height; y++) {
            boolean fullLine = true;
            for (int x = 0; x < width; x++) {
                if (boardColors[y][x] == Color.BLACK) {
                    fullLine = false;
                    break;
                }
            }
            if (fullLine) {
                removeLine(y);
                addScore(100); // Добавляем очки за очищенную линию
            }
        }
    }

    // Удаляет строку и сдвигает все строки выше вниз
    private void removeLine(int line) {
        for (int y = line; y > 0; y--) {
            System.arraycopy(boardColors[y - 1], 0, boardColors[y], 0, width);
        }
        // Очищаем верхнюю строку
        for (int x = 0; x < width; x++) {
            boardColors[0][x] = Color.BLACK;
        }
    }

    // Поворот фигуры
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

    // Запуск таймера
    public void startTimer() {
        startTime = System.currentTimeMillis();
        endTime = 0;
    }

    // Остановка таймера
    public void stopTimer() {
        if (endTime == 0) {
            endTime = System.currentTimeMillis();
        }
    }

    // Получение времени игры
    public String getTime() {
        long elapsedTime = gameOver ? endTime - startTime : System.currentTimeMillis() - startTime;

        long totalSeconds = elapsedTime / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;

        return String.format("%02d/%02d", minutes, seconds);
    }

    // Получение цвета текущей фигуры
    public Color getCurrentPieceColor() {
        return currentPiece.color;
    }

    // Получение цвета следующей фигуры
    public Color getNextPieceColor() {
        return nextPiece.color;
    }

    public static Piece clonePiece(Piece original) {
        char[][] newShape = new char[original.shape.length][];
        for (int i = 0; i < original.shape.length; i++) {
            newShape[i] = original.shape[i].clone();
        }
        return new Piece(newShape, original.color);
    }
}
