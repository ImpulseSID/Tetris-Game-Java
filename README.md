#Tetris Game in Java

This repository contains a simple implementation of the classic Tetris game in Java using javax.swing. The game is built as a JPanel and allows players to move and rotate Tetromino pieces as they fall onto a 10x20 grid.


Features

    7 Tetromino Shapes: Includes all the classic Tetris shapes (I, O, T, S, Z, L, J).
    Piece Movement: Move left, right, down, and rotate pieces using keyboard controls.
    Line Clearing: Complete horizontal lines are cleared, and blocks above shift down.
    Game Over Detection: The game ends when no space is available for a new piece.
    Colorful Graphics: Each piece has a unique color.
    Keyboard Controls:
        Arrow keys for movement.
        Rotate with the up arrow key.


Prerequisites

    Java Development Kit (JDK) 8 or higher
    Any IDE or text editor for Java programming


Code Structure
TetrisGame Class

The main class that handles the game logic, rendering, and keyboard inputs. It extends JPanel and implements ActionListener and KeyListener.

Key responsibilities:

    Manage the game board and current Tetromino piece.
    Handle piece movement, collision detection, and line clearing.
    Render the board and pieces using the paintComponent method.
    Respond to user input via KeyListener.

Tetromino Class

Represents a Tetris piece. Each piece has:

    A shape (2D array of integers).
    A color.
    Methods to move (left, right, down) and rotate the piece.

Key methods:

    randomPiece(): Generates a random Tetromino.
    rotate(): Rotates the piece clockwise.
    rotateBack(): Reverses the rotation.



Customization

    Adjust Board Dimensions: Modify BOARD_WIDTH and BOARD_HEIGHT in the TetrisGame class.
    Change Drop Speed: Modify the delay in the Timer object (500 milliseconds by default).
    Add New Shapes: Extend the SHAPES array in the Tetromino class with additional shapes.
