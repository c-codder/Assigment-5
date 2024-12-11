import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TicTacToeApp extends JFrame {
    private final JButton[] buttons = new JButton[9];
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private JLabel statusLabel;
    private boolean gameOver;
    private final DatabaseManager dbManager;

    public TicTacToeApp() {
        dbManager = new DatabaseManager();
        initializeUI();
        displayMenu();
    }

    private void initializeUI() {
        setTitle("Tic Tac Toe");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            buttons[i] = createGameButton(i);
            gamePanel.add(buttons[i]);
        }

        statusLabel = new JLabel("Welcome to Tic Tac Toe!", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(statusLabel, BorderLayout.NORTH);

        JButton saveButton = new JButton("Save Game");
        saveButton.addActionListener(e -> saveGameState());
        add(saveButton, BorderLayout.SOUTH);

        add(gamePanel, BorderLayout.CENTER);
    }

    private JButton createGameButton(int index) {
        JButton button = new JButton("");
        button.setFont(new Font("Arial", Font.PLAIN, 60));
        button.setFocusPainted(false);
        button.addActionListener(e -> handleButtonClick(index));
        return button;
    }

    private void displayMenu() {
        String[] options = {"New Game", "Load Game", "Exit"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Choose an option:",
                "Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (choice) {
            case 0 -> startNewGame();
            case 1 -> loadGameFromDatabase();
            default -> System.exit(0);
        }
    }

    private void startNewGame() {
        inputPlayerNames();
        resetGame();
    }

    private void inputPlayerNames() {
        String player1Name = JOptionPane.showInputDialog(this, "Enter Player 1 name (X):");
        String player2Name = JOptionPane.showInputDialog(this, "Enter Player 2 name (O):");

        if (player1Name == null || player2Name == null || player1Name.isEmpty() || player2Name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both player names are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
            inputPlayerNames();
        } else {
            player1 = new Player(player1Name, 'X');
            player2 = new Player(player2Name, 'O');
            currentPlayer = player1;
            statusLabel.setText(currentPlayer.getName() + " (" + currentPlayer.getSymbol() + ")'s turn.");
        }
    }

    private void handleButtonClick(int index) {
        if (gameOver) return;

        if (board.makeMove(index, currentPlayer.getSymbol())) {
            buttons[index].setText(String.valueOf(currentPlayer.getSymbol()));
            checkGameState();
        }
    }

    private void checkGameState() {
        if (board.checkWinner(currentPlayer.getSymbol())) {
            statusLabel.setText(currentPlayer.getName() + " wins!");
            gameOver = true;
            askForReplay();
        } else if (board.isBoardFull()) {
            statusLabel.setText("It's a draw!");
            gameOver = true;
            askForReplay();
        } else {
            switchPlayer();
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        statusLabel.setText(currentPlayer.getName() + " (" + currentPlayer.getSymbol() + ")'s turn.");
    }

    private void resetGame() {
        board = new Board();
        for (JButton button : buttons) {
            button.setText("");
        }
        gameOver = false;
        currentPlayer = player1;
        statusLabel.setText(currentPlayer.getName() + " (" + currentPlayer.getSymbol() + ")'s turn.");
    }

    private void askForReplay() {
        int response = JOptionPane.showConfirmDialog(this, "Game Over. Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            displayMenu();
        }
    }

    private void saveGameState() {
        dbManager.saveGame(player1, player2, currentPlayer, board.getBoard());
        statusLabel.setText("Game saved!");
    }

    private void loadGameFromDatabase() {
        Game loadedGame = dbManager.loadLatestGame();
        if (loadedGame != null) {
            player1 = loadedGame.getPlayer1();
            player2 = loadedGame.getPlayer2();
            currentPlayer = loadedGame.getCurrentPlayer();
            board = new Board();
            System.arraycopy(loadedGame.getBoard(), 0, board.getBoard(), 0, 9);
            updateButtons();
            gameOver = false;
            statusLabel.setText(currentPlayer.getName() + " (" + currentPlayer.getSymbol() + ")'s turn.");
        } else {
            JOptionPane.showMessageDialog(this, "No saved game found.");
            displayMenu();
        }
    }

    private void updateButtons() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(String.valueOf(board.getBoard()[i]));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToe game = new TicTacToe();
            game.setVisible(true);
        });
    }
}
