import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*; // Swing GUI compoents

// Jpanel is a swing container hold 
// custom snake game panel or component that will hold other parts of the game
public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    Tile snakeHead;
    int width;
    int height;
    int tileSize = 25;
    Tile food;
    Random random;
    Timer gameLoop;
    int velocityX;
    int velocityY;
    ArrayList<Tile> snakeBody;
    boolean gameOver = false;

    SnakeGame(int w, int h) {
        width = w;
        height = h;
        // Set the preferred size of the SnakeGame component to the specified width and
        // height
        setPreferredSize(new Dimension(this.width, this.height));
        // Set the background color of the SnakeGame component to black
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        random = new Random();
        snakeHead = new Tile(4, 5);
        snakeBody = new ArrayList<Tile>();
        food = new Tile(55, 55);
        foodRandom();
        velocityX = 0;
        velocityY = 0;
        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakepart = snakeBody.get(i);
            g.fillRect(snakepart.x * tileSize, snakepart.y * tileSize, tileSize, tileSize);
        }
        g.setColor(Color.red);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over ---'-'---" + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        } else {
            g.setColor(Color.green);
            g.drawString("SCORE " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }

    }

    public void foodRandom() {
        food.x = random.nextInt(width / tileSize);
        food.y = random.nextInt(height / tileSize);
    }

    public Boolean collision(Tile a, Tile b) {
        return a.x == b.x && a.y == b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        move();
        if (gameOver) {
            gameLoop.stop();
        }
        if (snakeHead.x * tileSize > width) {
            snakeHead.x = 0;
        }
        if (snakeHead.x < 0) {
            snakeHead.x = width / tileSize;
        }
        if (snakeHead.y * tileSize > height) {
            snakeHead.y = 0;
        }
        if (snakeHead.y < 0) {
            snakeHead.y = height / tileSize;
        }
    }

    public void move() {
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            foodRandom();
        }
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakepart = snakeBody.get(i);
            if (i == 0) {
                snakepart.x = snakeHead.x;
                snakepart.y = snakeHead.y;
            } else {
                Tile prevSnakepart = snakeBody.get(i - 1);
                snakepart.x = prevSnakepart.x;
                snakepart.y = prevSnakepart.y;
            }

        }
        // game over
        for (int i = 1; i < snakeBody.size(); i++) {
            Tile snakepart = snakeBody.get(i);
            if (collision(snakepart, snakeHead)) {
                gameOver = true;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP && velocityY == 0) {
            velocityX = 0;
            velocityY = -1;
        } else if (code == KeyEvent.VK_DOWN && velocityY == 0) {
            velocityX = 0;
            velocityY = 1;
        } else if (code == KeyEvent.VK_LEFT && velocityX == 0) {
            velocityX = -1;
            velocityY = 0;
        } else if (code == KeyEvent.VK_RIGHT && velocityX == 0) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
