package com.example.tetrisjavafx;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;


import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private Model model;

    @BeforeEach
    void setUp() {
        model = new Model(10, 20);
    }
    @Test
    void clonePiece() {
        char[][] shape = model.getNextPiece();
        Color color = model.getNextPieceColor();
        Model.Piece piece = new Model.Piece(shape, color);

        Model.Piece clonedPiece = Model.clonePiece(piece);

        assertNotSame(piece, clonedPiece, "Cloned piece should not be the same object.");

        assertArrayEquals(piece.shape, clonedPiece.shape, "Cloned piece should have the same shape.");
    }

    @Test
    void getNextPiece() {
        assertNotNull(model.getNextPiece(), "Next piece should not be null.");
    }
    @Test
    void getCurrentPiece() {
        model.spawnPiece();
        assertNotNull(model.getCurrentPiece(), "Current piece should not be null.");
    }

    @Test
    void getCurrentPieceColor() {
        model.spawnPiece();
        assertNotNull(model.getCurrentPieceColor(), "Current piece color should not be null.");
    }
    @Test
    void getNextPieceColor() {
        assertNotNull(model.getNextPieceColor(), "Next piece color should not be null.");
    }

    @Test
    void getPieceX() {
        model.spawnPiece();
        assertEquals(4, model.getPieceX(), "Piece should spawn at X position 4.");
    }
    @Test
    void getPieceY() {
        model.spawnPiece();
        assertEquals(0, model.getPieceY(), "Piece should spawn at Y position 0.");
    }

    @Test
    void spawnPiece() {
        model.spawnPiece();
        assertNotNull(model.getCurrentPiece(), "Current piece should not be null.");
        assertTrue(model.canMove(model.getPieceX(), model.getPieceY(), model.getCurrentPiece()),
                "Piece should be able to spawn at the top of the board.");
    }

    @Test
    void movePieceDown() {
        model.spawnPiece();
        int initialY = model.getPieceY();
        model.movePieceDown();
        assertEquals(initialY + 1, model.getPieceY(), "Piece should move down.");
    }
    @Test
    void movePieceLeft() {
        model.spawnPiece();
        int initialX = model.getPieceX();
        model.movePieceLeft();
        assertEquals(initialX - 1, model.getPieceX(), "Piece should move left.");
    }
    @Test
    void movePieceRight() {
        model.spawnPiece();
        int initialX = model.getPieceX();
        model.movePieceRight();
        assertEquals(initialX + 1, model.getPieceX(), "Piece should move right.");
    }
    @Test
    void rotatePiece() {
        model.spawnPiece();
        char[][] initialShape = model.getCurrentPiece();
        model.rotatePiece();
        assertNotEquals(initialShape, model.getCurrentPiece(), "Piece shape should change after rotation.");
    }

    @Test
    void getBoard() {
        Color[][] boardColors = model.getBoard();
        assertNotNull(boardColors, "Board colors should not be null.");
        assertEquals(20, boardColors.length, "Board should have 20 rows.");
        assertEquals(10, boardColors[0].length, "Board should have 10 columns.");
    }
    @Test
    void resetBoard() {
        model.resetBoard();
        Color[][] boardColors = model.getBoard();
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 10; x++) {
                assertEquals(Color.BLACK, boardColors[y][x], "Board should be black after reset.");
            }
        }
    }

    @Test
    void isGameOver() {
        model.gameOver = true;
        assertTrue(model.isGameOver(), "Game should be over.");
    }

    @Test
    void getScore() {
        model.addScore(100);
        assertEquals(100, model.getScore(), "Score should be 100.");
    }
    @Test
    void resetScore() {
        model.addScore(100);
        model.resetScore();
        assertEquals(0, model.getScore(), "Score should be reset to 0.");
        assertEquals(1, model.getLevel(), "Level should be reset to 1.");
    }

    @Test
    void getLevel() {
        model.addScore(500);
        assertEquals(2, model.getLevel(), "Level should be 2 after scoring 500 points.");
    }


    @Test
    void getTime() {
        model.startTimer();
        String timeBefore = model.getTime();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String timeAfter = model.getTime();
        assertNotEquals(timeBefore, timeAfter, "Time should be updated after 1 second.");
    }
    @Test
    void startTimer() {
        model.startTimer();
        assertNotEquals(0, model.getTime(), "Timer should start.");
    }
    @Test
    void stopTimer() {
        model.startTimer();
        long startTime = System.currentTimeMillis();
        model.stopTimer();
        long elapsedTime = System.currentTimeMillis() - startTime;
        assertTrue(elapsedTime < 1000, "Timer should stop quickly.");
    }

}
