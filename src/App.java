
import javax.swing.*;;

public class App {
    public static void main(String[] args) throws Exception {
        int gameWidth = 600;
        int gameHeight = 600;
        JFrame frame = new JFrame("snake");
        frame.setSize(gameWidth, gameHeight); // Set the size of the JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit application on close
        frame.setResizable(false); // Disable resizing of the JFrame
        frame.setLocationRelativeTo(null); // Center the JFrame on the screen
        frame.setVisible(true); // Make the JFrame visible

        SnakeGame game = new SnakeGame(gameWidth, gameHeight);
        frame.add(game);
        frame.pack();
        game.requestFocus();
    }
}
