# Tetris JavaFX

## Features
- **Keyboard Controls**: Move and rotate Tetris blocks using arrow keys.
- **Game Over Handling**: Detects when the game ends and allows restarting.
- **Score Tracking**: Keeps track of the player's score and cleared lines.
- **Level Progression**: Increases difficulty as the player clears more lines.
- **Dynamic Piece Generation**: Randomly generates Tetris pieces for gameplay.
- **Line Clearing**: Automatically removes full lines and updates the score.
- **Timer**: Tracks the elapsed time during gameplay.
- **MVC Architecture**: Clean separation of concerns with Model-View-Controller design.
- **JavaFX Rendering**: Uses JavaFX for graphical rendering of the game.

## Unit Testing
- **ModelTest**: Tests for game logic, including piece movement, line clearing, scoring, and game state.

<img src="https://i.imgur.com/UuyMYTN.png" alt="Tests">

## Requirements
- **Java**: JDK 21 or newer.
- **Maven**: For dependency management and building the project.
- **JavaFX**: For graphical rendering.

## Installation
1. Clone the repositury:
    ```bash
    git clone https://github.com/Akineyshen/TetrisJavaFX.git
    ```
2. Navigate to the project directory:
    ```bash
    cd TetrisJavaFX
    ```
3. Build the project using Maven:
    ```bash
    mvn clean install
    ```
4. Navigate to the java folder:
    ```bash
    Open the java folder.
    ``` 
5. Run the application:
    ```bash
    Right-click on the file Tetris.
    Select "Run Tetris.main()".
    ```
   
## Project Structure
```bash
    TetrisJavaFX/
    ├── src/ # Source code
    │   ├── main/
    │   │   ├── java/
    │   │   │   ├── Controller/ # Controller logic
    │   │   │   ├── Model/ # Game logic and data
    │   │   │   ├── View/ # Rendering logic
    │   │   │   └── Tetris # Main entry point
    │   │   └── resources/ # Game resources
    │   └── test/ # Unit tests
    ├── .gitignore # Ignored files
    ├── pom.xml # Maven configuration
    └── README.md # Project description
```

## Screenshots

<img src="https://i.imgur.com/E0GX4yl.png" alt="Tetris">

<img src="https://i.imgur.com/LzyMvKG.png" alt="GameOver">