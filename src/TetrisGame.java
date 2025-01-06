import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class TetrisGame extends JPanel implements ActionListener, KeyListener {

    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 20;
    private final int BLOCK_SIZE = 30;
    private Timer timer;
    private Tetromino currentPiece;
    private Color[][] board;

    public TetrisGame() {
        setPreferredSize(new Dimension(BOARD_WIDTH * BLOCK_SIZE, BOARD_HEIGHT * BLOCK_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        board = new Color[BOARD_HEIGHT][BOARD_WIDTH];
        timer = new Timer(500, this);
        timer.start();

        spawnPiece();
    }

    private void spawnPiece() {
        currentPiece = Tetromino.randomPiece();

        // Check if the piece can be placed at its starting position
        if (!canMove(currentPiece.getX(), currentPiece.getY(), currentPiece.getShape())) {
            // Game over condition
            timer.stop();  // Stop the game timer
            JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);  // Exit the game
        }
    }

    private boolean canMove(int newX, int newY, int[][] shape) {
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] != 0) {
                    int newRow = row + newY;
                    int newCol = col + newX;

                    if (newCol < 0 || newCol >= BOARD_WIDTH || newRow < 0 || newRow >= BOARD_HEIGHT || board[newRow][newCol] != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void placePiece() {
        int[][] shape = currentPiece.getShape();
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] != 0) {
                    board[currentPiece.getY() + row][currentPiece.getX() + col] = currentPiece.getColor();
                }
            }
        }
        clearLines();
        spawnPiece();
    }

    private void clearLines() {
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            boolean fullLine = true;
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (board[row][col] == null) {
                    fullLine = false;
                    break;
                }
            }
            if (fullLine) {
                for (int i = row; i > 0; i--) {
                    board[i] = board[i - 1];
                }
                board[0] = new Color[BOARD_WIDTH];
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (canMove(currentPiece.getX(), currentPiece.getY() + 1, currentPiece.getShape())) {
            currentPiece.moveDown();
        } else {
            placePiece();
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (board[row][col] != null) {
                    g.setColor(board[row][col]);
                    g.fillRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }

        int[][] shape = currentPiece.getShape();
        g.setColor(currentPiece.getColor());
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] != 0) {
                    g.fillRect((currentPiece.getX() + col) * BLOCK_SIZE, (currentPiece.getY() + row) * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            if (canMove(currentPiece.getX() - 1, currentPiece.getY(), currentPiece.getShape())) {
                currentPiece.moveLeft();
            }
        } else if (key == KeyEvent.VK_RIGHT) {
            if (canMove(currentPiece.getX() + 1, currentPiece.getY(), currentPiece.getShape())) {
                currentPiece.moveRight();
            }
        } else if (key == KeyEvent.VK_DOWN) {
            if (canMove(currentPiece.getX(), currentPiece.getY() + 1, currentPiece.getShape())) {
                currentPiece.moveDown();
            }
        } else if (key == KeyEvent.VK_UP) {
            currentPiece.rotate();
            if (!canMove(currentPiece.getX(), currentPiece.getY(), currentPiece.getShape())) {
                currentPiece.rotateBack();
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris Game");
        TetrisGame game = new TetrisGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class Tetromino {
    private int[][] shape;
    private Color color;
    private int x, y;

    private static final int[][][] SHAPES = {
            {{1, 1, 1, 1}}, // I
            {{1, 1}, {1, 1}}, // O
            {{0, 1, 0}, {1, 1, 1}}, // T
            {{1, 1, 0}, {0, 1, 1}}, // S
            {{0, 1, 1}, {1, 1, 0}}, // Z
            {{1, 1, 1}, {1, 0, 0}}, // L
            {{1, 1, 1}, {0, 0, 1}}  // J
    };

    private static final Color[] COLORS = {
            Color.CYAN, Color.YELLOW, Color.MAGENTA, Color.GREEN, Color.RED, Color.ORANGE, Color.BLUE
    };

    public Tetromino(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
        this.x = 3; // Start in the middle of the board
        this.y = 0; // Start at the top of the board
    }

    public int[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveDown() {
        y++;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void rotate() {
        int[][] rotatedShape = new int[shape[0].length][shape.length];
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                rotatedShape[col][shape.length - 1 - row] = shape[row][col];
            }
        }
        shape = rotatedShape;
    }

    public void rotateBack() {
        rotate();
        rotate();
        rotate();
    }

    public static Tetromino randomPiece() {
        Random rand = new Random();
        int index = rand.nextInt(SHAPES.length);
        return new Tetromino(SHAPES[index], COLORS[index]);
    }
}