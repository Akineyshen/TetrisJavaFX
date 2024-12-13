package com.example.tetrisjavafx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Tetris extends Application {
    private static final int CELL_SIZE = 30;
    private static final int WIDTH = 10; // ширина поля в клетках
    private static final int HEIGHT = 20; // высота поля в клетках

    private Model model;
    private View view;
    private Controller controller;

    @Override
    public void start(Stage primaryStage) {
        // Настройка Canvas
        Canvas canvas = new Canvas((WIDTH + 8) * CELL_SIZE, (HEIGHT + 2) * CELL_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Создание модели, представления и контроллера
        model = new Model(WIDTH, HEIGHT);
        view = new View(gc, CELL_SIZE, WIDTH, HEIGHT);
        controller = new Controller(model, view);
        controller.resetGame();

        // Настройка сцены
        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Tetris");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Обработка ввода
        scene.setOnKeyPressed(controller::handleKeyPress);

        // Запуск игрового цикла
        startGameLoop();
    }

    private void startGameLoop() {
        new AnimationTimer() {
            private long lastUpdate = 0;
            private long dropInterval = 500_000_000;

            @Override
            public void handle(long now) {
                int score = model.getScore();
                dropInterval = 500_000_000 - ((score / 500) * 50_000_000);
                dropInterval = Math.max(dropInterval, 100_000_000);

                if (now - lastUpdate >= dropInterval) { // Обновление каждые 500 мс
                    controller.update();
                    lastUpdate = now;
                }
                controller.render();
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
